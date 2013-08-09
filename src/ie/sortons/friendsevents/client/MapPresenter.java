package ie.sortons.friendsevents.client;


import ie.brianhenry.gwtbingmaps.client.api.BingMap;
import ie.brianhenry.gwtbingmaps.client.api.Events;
import ie.brianhenry.gwtbingmaps.client.api.Infobox;
import ie.brianhenry.gwtbingmaps.client.api.InfoboxOptions;
import ie.brianhenry.gwtbingmaps.client.api.Location;
import ie.brianhenry.gwtbingmaps.client.api.MapOptions;
import ie.brianhenry.gwtbingmaps.client.api.Pushpin;
import ie.brianhenry.gwtbingmaps.client.api.PushpinOptions;
import ie.brianhenry.gwtbingmaps.client.api.ViewOptions;
import ie.sortons.friendsevents.client.appevents.MapViewChangedEvent;
import ie.sortons.friendsevents.client.appevents.PermissionsPresentEvent;
import ie.sortons.friendsevents.client.appevents.UpdatedMapItemsEvent;
import ie.sortons.friendsevents.client.widgets.MapEventWidget;
import ie.sortons.gwtfbplus.client.fql.FqlEvent;
import ie.sortons.gwtfbplus.client.fql.FqlUser;
import ie.sortons.gwtfbplus.client.newresources.Resources;
import ie.sortons.gwtfbplus.client.overlay.DataObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import com.gwtfb.sdk.FBCore;

public class MapPresenter extends Composite  {

	private final String credentials = "ApmYYZr2urnVJhMJMOaMgjhH7lAISlyMpSEkIs6cqxYMwg85epCC6c1ZXgWIWFao";
	
	interface MyEventBinder extends EventBinder<MapPresenter> {
	}

	private static final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

	@SuppressWarnings("unused")
	private HasWidgets view;
	
	private BingMap mapDiv;

	private SimpleEventBus eventBus;

	private FBCore fbCore = GWT.create(FBCore.class);
	
	
	public MapPresenter(SimpleEventBus eventBus, HasWidgets view) {
		eventBinder.bindEventHandlers(this, eventBus);
		this.view = view;
		this.eventBus = eventBus;
		
		boolean enableSearchLogo = false;
		boolean showDashboard = true;
		boolean showMapTypeSelector = false;
		boolean showScalebar = false;
		boolean useInertia = false;
		
		MapOptions mapOptions = MapOptions.getMapOptions(credentials, null, null, null, null, null, null, null, null, null, null, null, enableSearchLogo, null, null, null, null, showDashboard, showMapTypeSelector, showScalebar, null, useInertia);
		
		// TODO
		// Figure out what zoomed out is for people who don't have their current location set
	 	Location center = Location.newLocation(35.906849,-118.937988);
	 	String mapTypeId = "fb";
	 	int zoom = 12;
	 	
		ViewOptions viewOptions = ViewOptions.newViewOptions(null, center, null, null, mapTypeId, null, zoom);
		
		
		// Set up the blank map
		mapDiv = new BingMap("eventsMap", mapOptions, viewOptions);
		 	
		
		mapDiv.setSize("760px", "400px");
		mapDiv.getElement().getStyle().setPosition(Position.RELATIVE);
		
		view.add(mapDiv);
		
	}
	
	@EventHandler
	void getUserLocation(PermissionsPresentEvent event) {
		System.out.println("PermissionsPresentEvent seen by MapPresenter/getUserLocation");
		
		String fql = "SELECT current_location.latitude, current_location.longitude FROM user WHERE uid = me()";
				
		String method = "fql.query";
		JSONObject query = new JSONObject();
		query.put("method", new JSONString(method));
		query.put("query", new JSONString(fql));
			
		fbCore.api(query.getJavaScriptObject(),
			new AsyncCallback<JavaScriptObject>() {
				public void onSuccess(JavaScriptObject response) {
					
					DataObject dataObject = response.cast();
					
					JsArray<FqlUser> users;	
					
					dataObject = response.cast();
					
					users = dataObject.getData().cast();
					
					mapDiv.getMap().setView(ViewOptions.newViewOptions(null, Location.newLocation(users.get(0).getCurrent_location().getLatitude(), users.get(0).getCurrent_location().getLongitude()), null, null, null, null, 12));
				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
		});
		
		
	}

	public void setView(HasWidgets view) {
		this.view = view;
	}


	private boolean firstRun = true;
	
	
	// TODO
	// Expose a method in BingMap to take care of this 
	boolean handlerattachend = false;

	void attachHandler() {

		AsyncCallback<JavaScriptObject> callback = new AsyncCallback<JavaScriptObject>() {  
		    public void onSuccess(JavaScriptObject response) {
		    	eventBus.fireEvent(new MapViewChangedEvent(mapDiv.getMap().getBounds().getNorth(), mapDiv.getMap().getBounds().getSouth(), mapDiv.getMap().getBounds().getEast(), mapDiv.getMap().getBounds().getWest()));
		    }		  
		    public void onFailure(Throwable caught) {  
		    	
		    }  
		};
		
		@SuppressWarnings("unused")
		JavaScriptObject o = Events.addHandler(mapDiv.getMap(), "viewchangeend", callback);
		handlerattachend = true;
	}

	@EventHandler
	void eventsReceived(final UpdatedMapItemsEvent event) {
		System.out.println("EventsReceivedEvent seen by MapPresenter: mapping points");
		
		// TODO
		// Not this
		// can't attach the hadnler too early.
		if(!handlerattachend){ attachHandler(); }
		

		// TODO
		// Not this.
		mapDiv.getMap().Entities().clear();
		
		
		// TODO
		// Add a timer to delay each pindrop
		for(FqlEvent nextEvent : event.getEvents()){

			Location location = Location.newLocation(nextEvent.getVenue().getLatitude(), nextEvent.getVenue().getLongitude());

			MapEventWidget item = new MapEventWidget(nextEvent);
			InfoboxOptions infoboxOptions = InfoboxOptions.getInfoboxOptions(400, 100, null, true, 0, true, false, null, null, item.getElement().getInnerHTML());
			Infobox itemInfobox = Infobox.getInfobox(location, infoboxOptions);

			PushpinOptions options = PushpinOptions.setPushPinOptions(25, 28, Resources.INSTANCE.mapPushPin().getSafeUri().asString(), false, null, null, null);
			Pushpin pushpin = Pushpin.getPushpin(location, options);

			mapDiv.addPinWithClickInfobox(pushpin, itemInfobox);

		}

		// TODO
		// Better
		if(firstRun){
			eventBus.fireEvent(new MapViewChangedEvent(mapDiv.getMap().getBounds().getNorth(), mapDiv.getMap().getBounds().getSouth(), mapDiv.getMap().getBounds().getEast(), mapDiv.getMap().getBounds().getWest()));
			firstRun=false;
		}
		
		
	}
	
}
