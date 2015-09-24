package ie.sortons.friendsevents.client;

import java.util.Date;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.CalendarUtil;

import ie.sortons.gwtfbplus.client.api.FBCore;
import ie.sortons.gwtfbplus.client.overlay.FbResponse;
import ie.sortons.gwtfbplus.client.overlay.graph.GraphEvent;

public class RpcService {

	// Get all attendees
	// SELECT eid, inviter, inviter_type, rsvp_status, start_time, uid FROM
	// event_member WHERE uid IN (SELECT uid2 FROM
	// friend WHERE uid1 = me())

	private FBCore fbCore = GWT.create(FBCore.class);

	TreeMap<String, GraphEvent> allFriendsEvents = new TreeMap<String, GraphEvent>();

	// TODO
	// just calculate it in here
	String startTime;

	private int numberPerPage = 350;
	private int offset = 0;
	//
	// private String getFriends() {
	// return "SELECT uid2 FROM friend WHERE uid1 = me() LIMIT " + numberPerPage + " OFFSET " + offset;
	// }

	private String getMembersEventsFql(String sourceIds) {
		return "SELECT eid FROM event_member WHERE start_time > '" + startTime + "' AND uid IN (" + sourceIds
				+ ") ORDER BY start_time";
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

	// HashMapSerializer hashMapSerializer = (HashMapSerializer) GWT.create(HashMapSerializer.class);

	/**
	 * Makes the Facebook API call to get the actual events
	 */
	public void getEvents(final AsyncCallback<TreeMap<String, GraphEvent>> callback) {

		String query = "/search?type=event&q=Dublin{place}";

		GWT.log("query: " + query);

		fbCore.api("v2.4", query, new AsyncCallback<JavaScriptObject>() {
			public void onSuccess(JavaScriptObject response) {

				FbResponse fbResponse = response.cast();

				JSONObject jsonResponse = new JSONObject(fbResponse.getData());

				GWT.log("response from fb");
				GWT.log(jsonResponse.toString());

				startTime = null;

				TreeMap<String, GraphEvent> newEvents = new TreeMap<String, GraphEvent>();

				for (String key : jsonResponse.keySet()) {
					if (jsonResponse.get(key).isObject()!=null) {
						GraphEvent e = jsonResponse.get(key).isObject().getJavaScriptObject().cast();
						GWT.log(e.getName());
						allFriendsEvents.put(e.getId(), e);
						newEvents.put(e.getId(), e);
					}
				}

				GWT.log("response size: " + newEvents.size());

				callback.onSuccess(newEvents);

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}

	public void getUserLocation(AsyncCallback<JavaScriptObject> aCallback) {

		// me/?fields=location{location}

		/*
		 * { "location": { "location": { "city": "Dublin", "country": "Ireland", "latitude": 53.3478, "longitude":
		 * -6.2597 }, "id": "110769888951990" }, "id": "37302520" }
		 * 
		 */

		GWT.log("getUserLocation");

		fbCore.api("v2.4", "/me/?fields=location{location}", aCallback);

	}

}
