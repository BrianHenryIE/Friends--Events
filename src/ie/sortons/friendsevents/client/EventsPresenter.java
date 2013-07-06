package ie.sortons.friendsevents.client;

import ie.sortons.friendsevents.client.events.PermissionsPresentEvent;
import ie.sortons.friendsevents.client.facebook.Canvas;
import ie.sortons.friendsevents.client.facebook.overlay.DataObject;
import ie.sortons.friendsevents.client.facebook.overlay.FqlEvent;
import ie.sortons.friendsevents.client.gwtfb.FBCore;
import ie.sortons.friendsevents.client.widgets.EventWidget;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

/**
 * Presenter for the main events screen, which shows a list of Facebook events once
 * they've been loaded from Facebook.
 */
class EventsPresenter {

	private FBCore fbCore = GWT.create(FBCore.class);

	interface MyEventBinder extends EventBinder<EventsPresenter> {
	}

	private static final MyEventBinder eventBinder = GWT
			.create(MyEventBinder.class);

	private HasWidgets view;

	EventsPresenter(EventBus eventBus) {
		eventBinder.bindEventHandlers(this, eventBus);
		calculateStartTime();
	}

	void setView(HasWidgets view) {
		this.view = view;
	}
  
	@EventHandler
	void permissionsPresentLetsGo(PermissionsPresentEvent event) {
		System.out.println("PermissionsPresentEvent seen by EventsPresenter");
		getPageOfEvents(view);
	}

	private int pageFrom = 0;
	
	private JsArray<FqlEvent> events;	

	private DataObject dataObject;


	private String startTime;
	private Date now = new Date();
	private Date today = new Date();
	
	@SuppressWarnings("deprecation")
	private void calculateStartTime() {
		
		// Figure out when today/yesterday started so we can search for events since then.
		// change this to earlier today!
		
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		   
		System.out.println("today "+today.toString());

	   	// String startTime = DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ssZ").format(DateUtils.addHours(new Date(), -12));
		// String startTime = DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ssZ").format(new Date());
		// startTime = "2012-10-06T17:35:00+0000";
		Date aDayAgo = new Date(); 
		aDayAgo.setDate(aDayAgo.getDate() - 1); 
		startTime = DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ssZ").format(aDayAgo);
	
	}
	
	private String sourceIds = "SELECT uid FROM user WHERE uid IN (SELECT uid FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me()) AND current_location) AND current_location.id IN (SELECT current_location.id FROM user WHERE uid = me() AND current_location)";

	private void getPageOfEvents(final HasWidgets view2) {

		System.out.println("Requesting page " + ((pageFrom + 100) / 100));

		String fql = "SELECT name, location, eid, start_time, end_time, pic_square FROM event WHERE eid IN (SELECT eid FROM event_member WHERE start_time > '"
				+ startTime
				+ "' AND uid IN ("
				+ sourceIds
				+ ")) ORDER BY start_time LIMIT 100"; // + pageFrom + ","+
														// (pageFrom+100);

		System.out.println(fql);

		String method = "fql.query";
		JSONObject query = new JSONObject();
		query.put("method", new JSONString(method));
		query.put("query", new JSONString(fql));

		fbCore.api(query.getJavaScriptObject(),
			new AsyncCallback<JavaScriptObject>() {
				public void onSuccess(JavaScriptObject response) {

					System.out.println("Page " + ((pageFrom + 100) / 100)
							+ " of events received");

					dataObject = response.cast();

					JSONObject jo = new JSONObject(response);
					System.out.println(jo.toString());

					System.out.println("response cast to DataObject");

					events = dataObject.getData().cast();

					System.out
							.println("DataObject cast to JsArray<FQLEvent>");

					System.out.println("events.length() = "
							+ events.length());

					// for ( int i = pageFrom; i < events.length(); i++ ) {
					for (int i = 0; i < events.length(); i++) {

						if ((events.get(i).getEndTime() != null)
								&& (events.get(i).getEndTimeDate()
										.compareTo(now) > 0)) {

							System.out.println("           "
									+ events.get(i).getEid()
									+ " : end time after now");

							view2.add(new EventWidget(events.get(i)));

						} else if ((events.get(i).getEndTime() == null)
								&& (events.get(i).getStartTimeDayDate() == today)) {

							System.out.println("           "
									+ events.get(i).getEid()
									+ " : end time null, start time today");

							view2.add(new EventWidget(events.get(i)));

						} else if ((events.get(i).getEndTime() == null)
								&& (events.get(i).getStartTimeDayDate()
										.compareTo(today) > 0)) {

							System.out
									.println("           "
											+ events.get(i).getEid()
											+ " : end time null, start time in the future");

							view2.add(new EventWidget(events.get(i)));

						}/*
						 * else if( (events.get(i).getEndTime()!=null) &&
						 * (events.get(i).getEndTimeDate().compareTo(now) <
						 * 0) ) {
						 * 
						 * System.out.println("           " +
						 * events.get(i).getEid() +
						 * " : end time before now");
						 * 
						 * 
						 * 
						 * } else if( (events.get(i).getEndTime()==null) &&
						 * (
						 * events.get(i).getStartTimeDayDate().compareTo(today
						 * )<0) ) {
						 * 
						 * System.out.println("           " +
						 * events.get(i).getEid() +
						 * " : end time null, start time yesterday");
						 * 
						 * } else {
						 * 
						 * // what the hell
						 * 
						 * System.out.println("EVENT " + i + ": " +
						 * events.get(i).getName());
						 * 
						 * System.out.println("           end_time " +
						 * events.get(i).getEndTimeDate().toString());
						 * System.out.println("           now "+
						 * now.toString());
						 * System.out.println("           today "+
						 * today.toString());
						 * System.out.println("           getStartTimeDayDate "
						 * +events.get(i).getStartTimeDayDate().toString());
						 * 
						 * 
						 * System.out.println("           " +
						 * events.get(i).getEid());
						 * System.out.println("           " +
						 * events.get(i).getStartTimeString());
						 * System.out.println("           " +
						 * events.get(i).getPic_square());
						 * System.out.println("           " +
						 * events.get(i).getLocation());
						 * 
						 * }
						 */
					}

					Canvas.setSize();

					startTime = events.get((events.length() - 1))
							.getStartTime();

					System.out.println("events.length() % 100 = "
							+ (events.length() % 100));

					// If it looks like there are more events, recurse.
					if (events.length() > 0) {

						pageFrom = pageFrom + 100;
						getPageOfEvents(view2);

					} else {

						// if there are no more events... we want to resize
						// the window here.

						Timer timer = new Timer() {
							public void run() {
								Canvas.setSize();
							}
						};

						// Execute the timer 2 seconds in the future
						timer.schedule(2000);

					}

				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});

	}

}