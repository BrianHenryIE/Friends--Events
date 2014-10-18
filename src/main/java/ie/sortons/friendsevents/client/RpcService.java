package ie.sortons.friendsevents.client;

import ie.sortons.friendsevents.client.presenter.FriendsEventsPresenter;
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
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.kfuntak.gwt.json.serialization.client.HashMapSerializer;

public class RpcService {

	// Get all attendees
	// SELECT eid, inviter, inviter_type, rsvp_status, start_time, uid FROM
	// event_member WHERE uid IN (SELECT uid2 FROM
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

	String friends = "SELECT uid2 FROM friend WHERE uid1 = me()";

	String getMembersEventsFql(String sourceIds) {
		return "SELECT eid FROM event_member WHERE start_time > '" + startTime + "' AND uid IN (" + sourceIds + ") LIMIT " + numberPerPage + " OFFSET " + offset;
	}

	String getEventDetailsFql(String sourceIds) {
		return "SELECT name, location, venue, eid, start_time, end_time, is_date_only FROM event WHERE eid IN ("
				+ getMembersEventsFql(sourceIds) + ") ORDER BY start_time LIMIT " + numberPerPage;
	}

	boolean finishedFetchingEvents = false;
	int spareFetchSlots = 5;
	int offset = 0;

	private int numberPerPage = 500;

	public RpcService(EventBus eventBus) {

		eventBinder.bindEventHandlers(this, eventBus);

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

		if (finishedFetchingEvents || spareFetchSlots <= 0)
			return;

		String fql = getEventDetailsFql(friends);

		String method = "fql.query";
		JSONObject query = new JSONObject();
		query.put("method", new JSONString(method));
		query.put("query", new JSONString(fql));

		System.out.println(fql);

		spareFetchSlots--;
		offset += numberPerPage;

		fbCore.api(query.getJavaScriptObject(), new AsyncCallback<JavaScriptObject>() {
			public void onSuccess(JavaScriptObject response) {

				spareFetchSlots++;

				JSONObject jsonResponse = new JSONObject(response);

				System.out.println("response from fb");

				TreeMap<Long, FqlEvent> newEvents = new TreeMap<Long, FqlEvent>();

				for (String key : jsonResponse.keySet()) {
					FqlEvent e = jsonResponse.get(key).isObject().getJavaScriptObject().cast();
					allFriendsEvents.put(e.getEid(), e);
					newEvents.put(e.getEid(), e);

				}

				asc.onSuccess(newEvents);

				if (jsonResponse.keySet().size() > 0)
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
