package ie.sortons.friendsevents.client;

import ie.sortons.friendsevents.client.appevents.DatesChangedEvent;
import ie.sortons.friendsevents.client.appevents.MapViewChangedEvent;
import ie.sortons.friendsevents.client.appevents.PermissionsPresentEvent;
import ie.sortons.friendsevents.client.appevents.UpdatedListItemsEvent;
import ie.sortons.friendsevents.client.appevents.UpdatedMapItemsEvent;
import ie.sortons.gwtfbplus.client.fql.FqlEvent;
import ie.sortons.gwtfbplus.client.overlay.DataObject;

import java.util.Date;
import java.util.HashSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import com.gwtfb.sdk.FBCore;

public class Model {

	// Get all attendees
	// SELECT eid, inviter, inviter_type, rsvp_status, start_time, uid FROM event_member WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me())
	
	
	private FBCore fbCore = GWT.create(FBCore.class);

	private EventBus eventBus;
	
	private DataObject dataObject;

	
	// TODO
	// just calculate it in here
	String startTime;
	
	// All friends
	// private String sourceIds = "SELECT uid2 FROM friend WHERE uid1 = me()";
	// Some friends
	private String sourceIds = "SELECT uid2 FROM friend WHERE uid1 = me() LIMIT 100";
	
	// TODO 
	// StringBuilder
	String getFql(String startTime, String sourceIds){
		return  "SELECT name, location, venue, eid, start_time, end_time, pic_square FROM event WHERE eid IN (SELECT eid FROM event_member WHERE start_time > '"
			+ startTime
			+ "' AND uid IN ("
			+ sourceIds
			+ ")) ORDER BY start_time LIMIT 250";	// + pageFrom + ","+
													// (pageFrom+100);
													// start_time is used instead of paging.
	}

	private HashSet<FqlEvent> allEvents = new HashSet<FqlEvent>();
	
	private HashSet<FqlEvent> mappableEvents = new HashSet<FqlEvent>();
		
	private HashSet<FqlEvent> unMappableEvents = new HashSet<FqlEvent>();

	private Date from;
	private Date to;
	
	// currentEventsForMap is the set of events that fall within the specified dates
	private HashSet<FqlEvent> currentEventsForMap = new HashSet<FqlEvent>();
	
	// currentEventsForList is the set of events that are visible on the map
	private HashSet<FqlEvent> currentEventsForList = new HashSet<FqlEvent>();

	
	
	interface MyEventBinder extends EventBinder<Model> {
	}

	private static final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

	
	@SuppressWarnings("deprecation")
	Model(EventBus eventBus) {
		this.eventBus = eventBus;
		eventBinder.bindEventHandlers(this, eventBus);

		// Date for searching
		Date startTimeDate = new Date();
		startTimeDate.setHours(0);
		startTimeDate.setMinutes(0);
		startTimeDate.setSeconds(0);
		CalendarUtil.addDaysToDate(startTimeDate, -2);
		startTime = DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ssZ").format(startTimeDate);
		
		// Dates for filtering
		from = new Date();
		setFromDate(from);
		
		to = new Date();
		CalendarUtil.addMonthsToDate(to, 2);
		setToDate(to);
	}
	
	
	
	@EventHandler
	void permissionsPresentLetsGo(PermissionsPresentEvent event) {
		System.out.println("PermissionsPresentEvent seen by DataController");
		getPageOfEvents();
	}


	/**
	 * Makes the Facebook API call to get the actual events
	 */
	private void getPageOfEvents() {

		String fql = getFql(startTime, sourceIds);

		String method = "fql.query";
		JSONObject query = new JSONObject();
		query.put("method", new JSONString(method));
		query.put("query", new JSONString(fql));

		fbCore.api(query.getJavaScriptObject(),
				new AsyncCallback<JavaScriptObject>() {
			public void onSuccess(JavaScriptObject response) {

				JsArray<FqlEvent> events;	

				dataObject = response.cast();

				events = dataObject.getData().cast();

				System.out.println("Events received from Facebook: " + events.length());

				// If it looks like there are more events, recurse.
				if (events.length() > 0) {

					addEventsFromSource(events);

					// update the start time for the next run
					startTime = events.get((events.length() - 1)).getStartTime();

					getPageOfEvents();

				} else {
					// TODO
					// reset the start time and get the friends who are attending


				}
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});	
	}

	

	/**
	 * Creates the lists allEvents, mappableEvents and unmappableEvents from the data
	 * returned from Facebook.
	 * 
	 * @param newEvents
	 */
	private void addEventsFromSource(JsArray<FqlEvent> newEvents) {
		System.out.println("Model is processing events from source");
		
		boolean allEventsChanged = false;

		for(int i=0; i < newEvents.length(); i++){
			
			if(allEvents.add(newEvents.get(i))==true) {
				allEventsChanged = true;
			
				if(newEvents.get(i).getVenue() != null && newEvents.get(i).getVenue().getLatitude()!="" && newEvents.get(i).getVenue().getLatitude()!="null" && newEvents.get(i).getVenue().getLatitude()!=null) {
					mappableEvents.add(newEvents.get(i));
				} else {
					unMappableEvents.add(newEvents.get(i));
				}
			}
		}
				
		if(allEventsChanged){ calculateEventsForMap(); }
	}
	
	@EventHandler
	void calculateEventsForMap(DatesChangedEvent event){
		System.out.println("DatesChangedEvent heard by Model");
		
		// Update the date range
		setFromDate(event.getFrom());
		setToDate(event.getTo());
		
		calculateEventsForMap();
	}
	
	

	@SuppressWarnings("deprecation")
	private void setFromDate(Date newFrom){
		if(newFrom.before(new Date())){
			newFrom = new Date();
		}
		this.from = newFrom;
		from.setHours(0);
		from.setMinutes(0);
		from.setSeconds(0);
	}
	
	
	/**
	 * Takes the new date for events to be searched up to, e.g. September 18th
	 * and adds a day and zeros the hours/minutes/seconds so calculations are 
	 * < September 19th at 0:00 
	 * 
	 * Ensures the to date can't be set before the from date though doesn't update
	 * the UI for that.
	 * 
	 * @param newTo
	 */
	@SuppressWarnings("deprecation")
	private void setToDate(Date newTo){
		if(newTo.before(from)){
			newTo = from;
		}
		this.to = newTo;
		CalendarUtil.addDaysToDate(to, 1);
		to.setHours(0);
		to.setMinutes(0);
		to.setSeconds(0);
	}
		
	
	
	/**
	 * Uses the date range to calculate which events should be displayed on the map
	 */
	void calculateEventsForMap() {
		System.out.println("Calculating events for map.");
		
		// Empty the set
		currentEventsForMap.clear();
		
		Date tonight = from;
		CalendarUtil.addDaysToDate(tonight, 1);
		
		Date yesterday = from;
		CalendarUtil.addDaysToDate(yesterday, -1);
		
		System.out.println(from);
		for(FqlEvent checkEvent : mappableEvents){
					
			// First, any events whose start dates are between the two specified.
			if(checkEvent.getEndTimeDate() != null && checkEvent.getStartTimeDate().after(yesterday) && checkEvent.getEndTimeDayDate().before(tonight)){
				
				currentEventsForMap.add(checkEvent);
			
			// If it's ending today and started yesterday, we'll allow it
			} else if(checkEvent.getStartTimeDate().after(from) && checkEvent.getStartTimeDayDate().before(to)){
				
				currentEventsForMap.add(checkEvent);
			}
		}
		eventBus.fireEvent(new UpdatedMapItemsEvent(currentEventsForMap));
		filterEventsByMapView();
	}
	
	private double north = 85.2153824698741;
	private double south = -69.95952698670646;
	private double east = 180.0;
	private double west = -180.0;
	
	
	@EventHandler
	void filterEventsByMapView(MapViewChangedEvent event){
		System.out.println("MapViewChangedEvent heard by Model");
	
		north = event.getNorth();
		south = event.getSouth();
		east = event.getEast();
		west = event.getWest();
				
		filterEventsByMapView();
	}
	
	/**
	 * Finds the subset of events on the map that are currently on screen
	 */
	void filterEventsByMapView(){
		
		currentEventsForList.clear();

		for(FqlEvent currentMapEvent : currentEventsForMap) {
			
			if( north > Double.parseDouble(currentMapEvent.getVenue().getLatitude()) && 
				Double.parseDouble(currentMapEvent.getVenue().getLatitude()) > south &&
				west < Double.parseDouble(currentMapEvent.getVenue().getLongitude()) &&
				Double.parseDouble(currentMapEvent.getVenue().getLongitude()) < east){

				currentEventsForList.add(currentMapEvent);
			}
		}
		
		eventBus.fireEvent(new UpdatedListItemsEvent(currentEventsForList));
	}

}
