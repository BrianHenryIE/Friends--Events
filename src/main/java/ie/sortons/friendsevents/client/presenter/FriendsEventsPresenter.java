package ie.sortons.friendsevents.client.presenter;

import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.web.bindery.event.shared.binder.EventBinder;

import ie.brianhenry.gwtbingmaps.client.api.LocationRect;
import ie.sortons.friendsevents.client.RpcService;
import ie.sortons.friendsevents.client.views.FriendsEventsView;
import ie.sortons.gwtfbplus.client.overlay.graph.GraphEvent;
import ie.sortons.gwtfbplus.client.widgets.datepicker.FbDateBox;

/**
 * Presenter for the main events screen, which shows a list of Facebook events once they've been loaded from Facebook.
 */
public class FriendsEventsPresenter implements Presenter {

	public interface Display {

		FbDateBox getStartDateBox();

		FbDateBox getEndDateBox();

		HandlerRegistration addValueChangeHandler(ValueChangeHandler<LocationRect> handler);

		LocationRect getLocationRect();

		void setEventsForList(PriorityQueue<GraphEvent> eventsForList);

		void eventsListUpdated();

		void setEventsForMap(PriorityQueue<GraphEvent> eventsForMap);

		void mapListUpdated();

		void setPresenter(FriendsEventsPresenter presenter);

		void setUserLocation(String latitude, String longitude);

	}

	interface MyEventBinder extends EventBinder<FriendsEventsPresenter> {
	}

	private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

	private FriendsEventsView view = new FriendsEventsView();

	private Date to = new Date();
	private Date from = new Date();

	private LocationRect currentLocationRect;

	Comparator<GraphEvent> eventsByTime = new Comparator<GraphEvent>() {
		public int compare(GraphEvent e1, GraphEvent e2) {
			return e2.getStartTime().compareTo(e1.getStartTime()) * -1;
		}
	};

	TreeMap<String, GraphEvent> allFriendsEvents = new TreeMap<String, GraphEvent>();

	TreeMap<String, GraphEvent> mappableEvents = new TreeMap<String, GraphEvent>();

	TreeMap<String, GraphEvent> unMappableEvents = new TreeMap<String, GraphEvent>();

	// currentEventsForMap is the set of events that fall within the specified
	// dates
	PriorityQueue<GraphEvent> mappableEventsBetweenDates = new PriorityQueue<GraphEvent>(100, eventsByTime);

	// currentEventsForList is the set of events that are visible on the map
	PriorityQueue<GraphEvent> currentlyMappedEventsForList = new PriorityQueue<GraphEvent>(100, eventsByTime);

	EventBus eventBus;

	public FriendsEventsPresenter(EventBus eventBus, RpcService rpcService) {

		this.eventBus = eventBus;

		rpcService.getUserLocation(new AsyncCallback<JavaScriptObject>() {
			public void onSuccess(JavaScriptObject response) {

				// TODO This should return a FqlUser object directly

				GWT.log("getUserLocation onSuccess");

				// {"location":{"location":{"city":"Dublin","country":"Ireland","latitude":53.3478,"longitude":-6.2597},"id":"110769888951990"},"id":"37302520"});

				JSONObject jo = new JSONObject(response);

				GWT.log(response.toString());
				
				JSONObject location = jo.get("location").isObject().get("location").isObject();

				GWT.log(location.toString());

				String city= location.get("city").isString().toString();
				GWT.log("city: " + city);
				
				String latitude = location.get("latitude").isNumber().toString();
				GWT.log("latitude: " + latitude);
				
				String longitude = location.get("longitude").isNumber().toString();
				GWT.log("longitude: " + longitude);

				view.setUserLocation(latitude, longitude);
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO onFailure
			}
		});

		rpcService.getEvents(asc);
	}

	AsyncCallback<TreeMap<String, GraphEvent>> asc = new AsyncCallback<TreeMap<String, GraphEvent>>() {

		@Override
		public void onSuccess(TreeMap<String, GraphEvent> result) {

			// First go through the entire list and split between mappable
			// and unmappable events

			
			GWT.log("result: " + result.size());

			for (GraphEvent nextEvent : result.values()) {
				
				allFriendsEvents.put(nextEvent.getId(), nextEvent);

				if (nextEvent.getPlace() != null && nextEvent.getPlace().getLocation() != null && nextEvent.getPlace().getLocation().getLatitude() != null
						&& nextEvent.getPlace().getLocation().getLongitude() != null)
					mappableEvents.put(nextEvent.getId(), nextEvent);
				else
					unMappableEvents.put(nextEvent.getId(), nextEvent);
			}

			GWT.log("allFriendsEvents " + allFriendsEvents.size());
			GWT.log("mappableEvents " + mappableEvents.size());
			GWT.log("unMappableEvents " + unMappableEvents.size());

			calculateEventsToShow();

		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void go(HasWidgets container) {
		view.setPresenter(this);

		container.clear();
		container.add(view);

		view.getStartDateBox().setValue(from);
		CalendarUtil.addMonthsToDate(to, 1);
		view.getEndDateBox().setValue(to);

		currentLocationRect = view.getLocationRect();

		view.setEventsForList(currentlyMappedEventsForList);
		view.setEventsForMap(mappableEventsBetweenDates);

		bind();
	}

	public void bind() {

		eventBinder.bindEventHandlers(this, eventBus);

		view.addValueChangeHandler(new ValueChangeHandler<LocationRect>() {
			@Override
			public void onValueChange(ValueChangeEvent<LocationRect> event) {
				// GWT.log("locationrect valuechange");
				currentLocationRect = event.getValue();
				calculateEventsToShow();
			}
		});

		view.getStartDateBox().addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				from = event.getValue();

				CalendarUtil.resetTime(from);

				if (from.before(new Date())) {
					from = new Date();
					view.getStartDateBox().setValue(from);
				}

				if (from.after(to)) {
					to = new Date(from.getTime());
					CalendarUtil.addDaysToDate(to, 1);
					CalendarUtil.resetTime(to);
					view.getEndDateBox().setValue(to);
				}

				calculateEventsToShow();
			}
		});

		view.getEndDateBox().addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				to = event.getValue();

				if (to.before(new Date())) {
					to = new Date();
					view.getEndDateBox().setValue(to);
				}

				if (to.before(from)) {
					from = new Date(to.getTime());
					view.getStartDateBox().setValue(from);
				}

				// Use the following day at 0:00 (= tonight at midnight)
				CalendarUtil.addDaysToDate(to, 1);
				// TODO check if the datebox is already returning a 0 timed date
				CalendarUtil.resetTime(to);

				calculateEventsToShow();
			}
		});

	}

	/**
	 * To be called when anything changes Filters using the date then the map
	 */
	private void calculateEventsToShow() {

		mappableEventsBetweenDates.clear();

		// For an event to be running between the specified dates, its start
		// time will be before the final date and its
		// end time will be after the start date.
		for (GraphEvent checkEvent : mappableEvents.values())
			// if ( checkEvent.getStartTime() == from ||
			if ((checkEvent.getStartTimeDate().after(from) && checkEvent.getStartTimeDate().before(to))
					|| (checkEvent.getEndTime() != null && checkEvent.getEndTimeDate().after(from)
							&& checkEvent.getEndTimeDate().before(to)))
				mappableEventsBetweenDates.add(checkEvent);

		view.mapListUpdated();

		currentlyMappedEventsForList.clear();

		for (GraphEvent currentMapEvent : mappableEventsBetweenDates)
			if (currentLocationRect.getNorth() > currentMapEvent.getPlace().getLocation().getLatitude()
					&& currentMapEvent.getPlace().getLocation().getLatitude() > currentLocationRect.getSouth()
					&& currentLocationRect.getWest() < currentMapEvent.getPlace().getLocation().getLongitude()
					&& currentMapEvent.getPlace().getLocation().getLongitude() < currentLocationRect.getEast())
				currentlyMappedEventsForList.add(currentMapEvent);

		// GWT.log("mappableEvents " + mappableEvents.size());
		// GWT.log("currentEventsFromMap " +
		// mappableEventsBetweenDates.size());
		// GWT.log("currentEventsForList " +
		// currentlyMappedEventsForList.size());

		view.eventsListUpdated();
	}

}