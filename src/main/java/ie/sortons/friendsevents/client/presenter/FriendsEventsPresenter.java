package ie.sortons.friendsevents.client.presenter;

import ie.brianhenry.gwtbingmaps.client.api.LocationRect;
import ie.sortons.friendsevents.client.FqlEvent;
import ie.sortons.friendsevents.client.RpcService;
import ie.sortons.friendsevents.client.views.FriendsEventsView;
import ie.sortons.gwtfbplus.client.overlay.DataObject;
import ie.sortons.gwtfbplus.client.overlay.fql.FqlUser;
import ie.sortons.gwtfbplus.client.widgets.datepicker.FbDateBox;

import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.web.bindery.event.shared.binder.EventBinder;

/**
 * Presenter for the main events screen, which shows a list of Facebook events
 * once they've been loaded from Facebook.
 */
public class FriendsEventsPresenter implements Presenter {

	public interface Display {

		FbDateBox getStartDateBox();

		FbDateBox getEndDateBox();

		HandlerRegistration addValueChangeHandler(ValueChangeHandler<LocationRect> handler);

		LocationRect getLocationRect();

		void setEventsForList(PriorityQueue<FqlEvent> eventsForList);

		void eventsListUpdated();

		void setEventsForMap(PriorityQueue<FqlEvent> eventsForMap);

		void mapListUpdated();

		void setPresenter(FriendsEventsPresenter presenter);

		void setUserLocation(FqlUser user);

	}

	interface MyEventBinder extends EventBinder<FriendsEventsPresenter> {
	}

	private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

	private FriendsEventsView view = new FriendsEventsView();

	private Date to = new Date();
	private Date from = new Date();

	private LocationRect currentLocationRect;

	Comparator<FqlEvent> eventsByTime = new Comparator<FqlEvent>() {
		public int compare(FqlEvent e1, FqlEvent e2) {
			return e2.getStartTime().compareTo(e1.getStartTime()) * -1;
		}
	};

	TreeMap<Long, FqlEvent> allFriendsEvents = new TreeMap<Long, FqlEvent>();

	TreeMap<Long, FqlEvent> mappableEvents = new TreeMap<Long, FqlEvent>();

	TreeMap<Long, FqlEvent> unMappableEvents = new TreeMap<Long, FqlEvent>();

	// currentEventsForMap is the set of events that fall within the specified
	// dates
	PriorityQueue<FqlEvent> mappableEventsBetweenDates = new PriorityQueue<FqlEvent>(100, eventsByTime);

	// currentEventsForList is the set of events that are visible on the map
	PriorityQueue<FqlEvent> currentlyMappedEventsForList = new PriorityQueue<FqlEvent>(100, eventsByTime);

	EventBus eventBus;

	public FriendsEventsPresenter(EventBus eventBus, RpcService rpcService) {

		this.eventBus = eventBus;

		rpcService.getUserLocation(new AsyncCallback<JavaScriptObject>() {
			public void onSuccess(JavaScriptObject response) {

				// TODO This should return a FqlUser object directly

				DataObject dataObject = response.cast();

				JsArray<FqlUser> users = dataObject.getData().cast();

				FqlUser theUser = users.get(0);

				view.setUserLocation(theUser);
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO onFailure
			}
		});

		rpcService.getFriendsEvents(asc);
	}

	AsyncCallback<TreeMap<Long, FqlEvent>> asc = new AsyncCallback<TreeMap<Long, FqlEvent>>() {

		@Override
		public void onSuccess(TreeMap<Long, FqlEvent> result) {

			// First go through the entire list and split between mappable
			// and unmappable events

			System.out.println("result: " + result.size());

			for (FqlEvent nextEvent : result.values()) {
				allFriendsEvents.put(nextEvent.getEid(), nextEvent);
				if (nextEvent.getVenue() != null && nextEvent.getVenue().getLatitude() != null
						&& nextEvent.getVenue().getLatitude() != null)
					mappableEvents.put(nextEvent.getEid(), nextEvent);
				else
					unMappableEvents.put(nextEvent.getEid(), nextEvent);
			}

			System.out.println("allFriendsEvents " + allFriendsEvents.size());
			System.out.println("mappableEvents " + mappableEvents.size());
			System.out.println("unMappableEvents " + unMappableEvents.size());

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
				// System.out.println("locationrect valuechange");
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
		for (FqlEvent checkEvent : mappableEvents.values())
			// if ( checkEvent.getStartTime() == from ||
			if ((checkEvent.getStartTimeDate().after(from) && checkEvent.getStartTimeDate().before(to))
					|| (checkEvent.getEndTime() != null && checkEvent.getEndTimeDate().after(from) && checkEvent.getEndTimeDate()
							.before(to)))
				mappableEventsBetweenDates.add(checkEvent);

		view.mapListUpdated();

		currentlyMappedEventsForList.clear();

		for (FqlEvent currentMapEvent : mappableEventsBetweenDates)
			if (currentLocationRect.getNorth() > currentMapEvent.getVenue().getLatitude()
					&& currentMapEvent.getVenue().getLatitude() > currentLocationRect.getSouth()
					&& currentLocationRect.getWest() < currentMapEvent.getVenue().getLongitude()
					&& currentMapEvent.getVenue().getLongitude() < currentLocationRect.getEast())
				currentlyMappedEventsForList.add(currentMapEvent);

		// System.out.println("mappableEvents " + mappableEvents.size());
		// System.out.println("currentEventsFromMap " +
		// mappableEventsBetweenDates.size());
		// System.out.println("currentEventsForList " +
		// currentlyMappedEventsForList.size());

		view.eventsListUpdated();
	}

}