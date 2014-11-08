package ie.sortons.friendsevents.client;

import ie.sortons.gwtfbplus.client.api.FBCore;

import java.util.Date;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.kfuntak.gwt.json.serialization.client.HashMapSerializer;

public class RpcService {

	// Get all attendees
	// SELECT eid, inviter, inviter_type, rsvp_status, start_time, uid FROM
	// event_member WHERE uid IN (SELECT uid2 FROM
	// friend WHERE uid1 = me())

	private FBCore fbCore = GWT.create(FBCore.class);

	TreeMap<Long, FqlEvent> allFriendsEvents = new TreeMap<Long, FqlEvent>();

	// TODO
	// just calculate it in here
	String startTime;

	private int numberPerPage = 350;
	private int offset = 0;

	private String getFriends(){
		return "SELECT uid2 FROM friend WHERE uid1 = me() LIMIT " + numberPerPage + "  OFFSET " + offset; 
	}

	private String getMembersEventsFql(String sourceIds) {
		return "SELECT eid FROM event_member WHERE start_time > '" + startTime + "' AND uid IN (" + sourceIds + ") ORDER BY start_time";
	}

	String getEventDetailsFql(String sourceIds) {
		return "SELECT name, location, venue, eid, start_time, end_time, is_date_only FROM event WHERE eid IN ("
				+ getMembersEventsFql(sourceIds) + ") ORDER BY start_time";
	}

	public RpcService(EventBus eventBus) {

		// Date for searching
		Date startTimeDate = new Date();
		CalendarUtil.resetTime(startTimeDate);
		CalendarUtil.addDaysToDate(startTimeDate, -2);
		startTime = DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ssZ").format(startTimeDate);

	}

	HashMapSerializer hashMapSerializer = (HashMapSerializer) GWT.create(HashMapSerializer.class);

	/**
	 * Makes the Facebook API call to get the actual events
	 */
	public void getFriendsEvents(final AsyncCallback<TreeMap<Long, FqlEvent>> asc) {

		String fql = getEventDetailsFql(getFriends());

		String method = "fql.query";
		JSONObject query = new JSONObject();
		query.put("method", new JSONString(method));
		query.put("query", new JSONString(fql));

		GWT.log(fql);

		fbCore.api(query.getJavaScriptObject(), new AsyncCallback<JavaScriptObject>() {
			public void onSuccess(JavaScriptObject response) {

				JSONObject jsonResponse = new JSONObject(response);

				GWT.log("response from fb");
				startTime = null;

				TreeMap<Long, FqlEvent> newEvents = new TreeMap<Long, FqlEvent>();

				for (String key : jsonResponse.keySet()) {
					FqlEvent e = jsonResponse.get(key).isObject().getJavaScriptObject().cast();
					allFriendsEvents.put(e.getEid(), e);
					newEvents.put(e.getEid(), e);
				}

				GWT.log("response size: " + newEvents.size());

				GWT.log("date: " + startTime);
				
				asc.onSuccess(newEvents);

				System.out.println("startTime: " + startTime);

				offset = offset+numberPerPage;
				
				if (newEvents.size()>1)
					getFriendsEvents(asc);
				else
					System.out.println("done?");

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

}
