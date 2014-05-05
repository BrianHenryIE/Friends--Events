package ie.sortons.friendsevents.client.presenter;

import ie.brianhenry.gwtbingmaps.client.api.LocationRect;
import ie.sortons.friendsevents.client.RpcService;
import ie.sortons.friendsevents.client.appevents.EventsReceivedEvent;
import ie.sortons.friendsevents.client.views.FriendsEventsView;
import ie.sortons.gwtfbplus.client.overlay.DataObject;
import ie.sortons.gwtfbplus.client.overlay.fql.FqlUser;
import ie.sortons.gwtfbplus.client.widgets.datepicker.FbDateBox;
import ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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
import com.google.web.bindery.event.shared.binder.EventHandler;

/**
 * Presenter for the main events screen, which shows a list of Facebook events once they've been loaded from Facebook.
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

	private TreeMap<Long, FqlEvent> allFriendsEvents;

	private PriorityQueue<FqlEvent> mappableEvents = new PriorityQueue<FqlEvent>(100, new Comparator<FqlEvent>() {
		public int compare(FqlEvent e1, FqlEvent e2) {
			return e2.getStartTime().compareTo(e1.getStartTime()) * -1;
		}
	});

	private List<FqlEvent> unMappableEvents = new ArrayList<FqlEvent>();

	// currentEventsForMap is the set of events that fall within the specified dates
	private PriorityQueue<FqlEvent> mappableEventsBetweenDates = new PriorityQueue<FqlEvent>(100, new Comparator<FqlEvent>() {
		public int compare(FqlEvent e1, FqlEvent e2) {
			return e2.getStartTime().compareTo(e1.getStartTime()) * -1;
		}
	});

	// currentEventsForList is the set of events that are visible on the map
	private PriorityQueue<FqlEvent> currentlyMappedEventsForList = new PriorityQueue<FqlEvent>(100, new Comparator<FqlEvent>() {
		public int compare(FqlEvent e1, FqlEvent e2) {
			return e2.getStartTime().compareTo(e1.getStartTime()) * -1;
		}
	});

	private EventBus eventBus;

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

		view.setEventsForMap(mappableEvents);

		allFriendsEvents = rpcService.getFriendsEvents();

	}

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

	@EventHandler
	void newEvents(EventsReceivedEvent event) {

		// TODO add new events rather than clearing

		mappableEvents.clear();
		unMappableEvents.clear();

		// First go through the entire list and split between mappable and unmappable events

		for (FqlEvent nextEvent : allFriendsEvents.values())
			if (nextEvent.getVenue() != null && nextEvent.getVenue().getLatitude() != null && nextEvent.getVenue().getLatitude() != null)
				mappableEvents.add(nextEvent);
			else
				unMappableEvents.add(nextEvent);

		// System.out.println("allFriendsEvents " + allFriendsEvents.size());
		// System.out.println("mappableEvents " + mappableEvents.size());
		// System.out.println("unMappableEvents " + unMappableEvents.size());

		calculateEventsToShow();
	}

	/**
	 * To be called when anything changes Filters using the date then the map
	 */
	private void calculateEventsToShow() {

		mappableEventsBetweenDates.clear();

		// For an event to be running between the specified dates, its start time will be before the final date and its
		// end time will be after the start date.
		for (FqlEvent checkEvent : mappableEvents)
			// if ( checkEvent.getStartTime() == from ||
			if ((checkEvent.getStartTime().after(from) && checkEvent.getStartTime().before(to))
					|| (checkEvent.getEndTime() != null && checkEvent.getEndTime().after(from) && checkEvent.getEndTime().before(to)))
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
		// System.out.println("currentEventsFromMap " + mappableEventsBetweenDates.size());
		// System.out.println("currentEventsForList " + currentlyMappedEventsForList.size());

		view.eventsListUpdated();
	}

}