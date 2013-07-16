package ie.sortons.friendsevents.client;


import ie.sortons.friendsevents.client.events.EventsReceivedEvent;
import ie.sortons.friendsevents.client.gwtbingmaps.BingMap;
import ie.sortons.friendsevents.client.gwtbingmaps.BingMapsAPI;
import ie.sortons.friendsevents.client.gwtbingmaps.BingMapsAPI.InfoboxOptions;
import ie.sortons.friendsevents.client.gwtbingmaps.BingMapsAPI.MicrosoftMapsInfobox;
import ie.sortons.friendsevents.client.gwtbingmaps.BingMapsAPI.MicrosoftMapsLocation;
import ie.sortons.friendsevents.client.resources.Resources;
import ie.sortons.friendsevents.client.widgets.MapEventWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class MapPresenter extends Composite  {

	private final String bingMapsAPIkey = "ApmYYZr2urnVJhMJMOaMgjhH7lAISlyMpSEkIs6cqxYMwg85epCC6c1ZXgWIWFao";
	
	interface MyEventBinder extends EventBinder<MapPresenter> {
	}

	private static final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

	private HasWidgets view;
	
	private BingMap mapDiv;
	
	public MapPresenter(SimpleEventBus eventBus, HasWidgets view) {
		eventBinder.bindEventHandlers(this, eventBus);
		this.view = view;

		// Set up the blank map
		mapDiv = new BingMap(bingMapsAPIkey, "eventsMap");
		
		mapDiv.setSize("750px", "400px");
		mapDiv.getElement().getStyle().setPosition(Position.RELATIVE);
		
		view.add(mapDiv);
	}

	public void setView(HasWidgets view) {
		this.view = view;
	}
	
	public void go() {
		
		
	}
	
	@EventHandler
	void eventsReceived(EventsReceivedEvent event) {
		System.out.println("EventsReceivedEvent seen by MapPresenter: mapping points");
		
		// Add the locations to the map
		for (int i = 0; i < event.getEvents().length(); i++) {
			
			
			if(event.getEvents().get(i).getVenue().getLatitude()!="" && event.getEvents().get(i).getVenue().getLatitude()!="null" && event.getEvents().get(i).getVenue().getLatitude()!=null) {
			
				// System.out.println("lat, long: " + event.getEvents().get(i).getVenue().getLatitude()+","+event.getEvents().get(i).getVenue().getLongitude());
				
				MapEventWidget item = new MapEventWidget(event.getEvents().get(i));

				MicrosoftMapsLocation location = BingMapsAPI.microsoftMapsLocation(event.getEvents().get(i).getVenue().getLatitude(), event.getEvents().get(i).getVenue().getLongitude());
				
				InfoboxOptions infoboxOptions = BingMapsAPI.setInfoboxOptions(400, 100, null, true, 0, true, false, null, null, item.getElement().getInnerHTML());
				
				MicrosoftMapsInfobox itemInfobox = BingMapsAPI.createInfoBox(location, infoboxOptions);
		
				mapDiv.addPinToMap(location, Resources.INSTANCE.mapPushPin().getSafeUri().asString(), itemInfobox);
			}
		}
		
	}
	

}
