package ie.sortons.friendsevents.client;

import ie.sortons.friendsevents.client.appevents.EventsReceivedEvent;
import ie.sortons.friendsevents.client.appevents.LoginEvent;
import ie.sortons.friendsevents.client.presenter.FriendsEventsPresenter;
import ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent;

import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import com.gwtfb.sdk.FBCore;
import com.kfuntak.gwt.json.serialization.client.HashMapSerializer;

public class RpcService {

	// Get all attendees
	// SELECT eid, inviter, inviter_type, rsvp_status, start_time, uid FROM event_member WHERE uid IN (SELECT uid2 FROM
	// friend WHERE uid1 = me())

	interface MyEventBinder extends EventBinder<RpcService> {
	}

	private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

	private FBCore fbCore = GWT.create(FBCore.class);

	TreeMap<Long, FqlEvent> allFriendsEvents = new TreeMap<Long, FqlEvent>();

	// private HashSet<FqlEvent> allPagesEvents = new HashSet<FqlEvent>();

	FriendsEventsPresenter friendsEventsPresenter;

	// TODO
	// just calculate it in here
	String startTime;

	private int numberPerPage = 500;

	private String friends = "SELECT uid2 FROM friend WHERE uid1 = me()";

	private String getMembersEventsFql(String sourceIds) {
		return "SELECT eid FROM event_member WHERE start_time > '" + startTime + "' AND uid IN (" + sourceIds + ")";
	}

	private EventBus eventBus;

	String getEventDetailsFql(String sourceIds) {
		return "SELECT name, location, venue, eid, start_time, end_time, is_date_only FROM event WHERE eid IN (" + getMembersEventsFql(sourceIds)
				+ ") ORDER BY start_time LIMIT " + numberPerPage;
	}

	public RpcService(EventBus eventBus) {

		this.eventBus = eventBus;

		eventBinder.bindEventHandlers(this, eventBus);

		// Date for searching
		Date startTimeDate = new Date();
		CalendarUtil.resetTime(startTimeDate);
		CalendarUtil.addDaysToDate(startTimeDate, -2);
		startTime = DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ssZ").format(startTimeDate);

	}

	@EventHandler
	void getPageOfEvents(LoginEvent event) {
		getPageOfEvents();
	}

	HashMapSerializer hashMapSerializer = (HashMapSerializer) GWT.create(HashMapSerializer.class);

	/**
	 * Makes the Facebook API call to get the actual events
	 */
	private void getPageOfEvents() {

		String fql = getEventDetailsFql(friends);

		String method = "fql.query";
		JSONObject query = new JSONObject();
		query.put("method", new JSONString(method));
		query.put("query", new JSONString(fql));

		System.out.println(fql);

		fbCore.api(query.getJavaScriptObject(), new AsyncCallback<JavaScriptObject>() {
			public void onSuccess(JavaScriptObject response) {

				JSONObject jsonResponse = new JSONObject(response);

				System.out.println("response from fb");

				try {

					// TODO... Slow somewhere around here.. maybe not on this line

					
					@SuppressWarnings("unchecked")
					HashMap<String, FqlEvent> eventsMap = (HashMap<String, FqlEvent>) hashMapSerializer.deSerialize(jsonResponse,
							"ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent");

					System.out.println("size: " + eventsMap.values().size());

					// If it looks like there are more events, recurse.

					if (eventsMap.values().size() > 0) {

						TreeMap<Long, FqlEvent> newEvents = new TreeMap<Long, FqlEvent>();

						for (FqlEvent event : eventsMap.values()) {
							allFriendsEvents.put(event.getEid(), event);
							newEvents.put(event.getEid(), event);
						}

						eventBus.fireEvent(new EventsReceivedEvent(newEvents));

						startTime = null;

						// TODO.. I think there's a bug here... Indian time? (IST)
						for (int countBack = eventsMap.values().size() - 1; countBack > 0 && startTime == null; countBack--) {
							System.out.println("checking " + countBack + " : " + eventsMap.get(Integer.toString(countBack)).getStartTime());
							if (!eventsMap.get(Integer.toString(countBack)).getIsDateOnly()) {
								startTime = DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ssZ").format(
										eventsMap.get(Integer.toString(countBack)).getStartTime());
								break;
							}
						}

						// It's getting here when list size is 1... though I don't know when that is

						System.out.println("startTime: " + startTime);

						// Only fetch more than one page in production mode!
						//if (!(!GWT.isProdMode() && GWT.isClient()))
							if (startTime != null)
								getPageOfEvents();
							else
								System.out.println("done?");

					}

				} catch (JSONException e) {

					// error in parsing could be an actual error from Facebook which we can understand
					// or an error in the JSON which we'll narrow down and then skip over

					// System.out.println("error");

					if (jsonResponse.containsKey("error_code") && jsonResponse.containsKey("error_msg")) {

						// Serializer serializer = (Serializer) GWT.create(Serializer.class);

						// JsFqlError error = (JsFqlError) serializer.deSerialize(new JSONObject(response),
						// "ie.sortons.gwtfbplus.shared.domain.JsFqlError");
						// TODO Error popup

					}
				}

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}

	public void getUserLocation(AsyncCallback<JavaScriptObject> aCallback) {

		String fql = "SELECT current_location.latitude, current_location.longitude FROM user WHERE uid = me()";

		String method = "fql.query";
		JSONObject query = new JSONObject();
		query.put("method", new JSONString(method));
		query.put("query", new JSONString(fql));

		fbCore.api(query.getJavaScriptObject(), aCallback);

	}

	public TreeMap<Long, FqlEvent> getFriendsEvents() {
		return allFriendsEvents;
	}

}
