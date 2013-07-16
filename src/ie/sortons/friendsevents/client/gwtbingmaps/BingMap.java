package ie.sortons.friendsevents.client.gwtbingmaps;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class BingMap extends Composite {

	private String credentials;
	private String mapName;
	private HTML panel;

	private Map theMap;
	
	public BingMap(String credentials, String mapName) {
		this.credentials = credentials;
		this.mapName = mapName;
		panel = new HTML();
		initWidget(panel);
		this.getElement().setId(mapName);		
	}

	protected void onAttach(){
		super.onAttach();
		if(isBingMapsLibraryLoaded()){
			
			
			boolean enableSearchLogo = false;
			boolean showDashboard = true;
			boolean showMapTypeSelector = false;
			boolean showScalebar = false;
			
			
			int labelOverlay = 0;
			
			MapOptions mapOptions = MapOptions.getMapOptions(credentials, null, null, null, null, null, null, null, null, null, null, null, enableSearchLogo, null, null, null, null, showDashboard, showMapTypeSelector, showScalebar, null, null);
		 	
			
		 	Location center = Location.getMicrosoftMapsLocation(35.906849,-118.937988);
		 	String mapTypeId = "fb";
		 	int zoom = 5;
		 	
			ViewOptions viewOptions = ViewOptions.getViewOptions(null, center, null, null, mapTypeId, null, zoom);
			
		 	theMap = Map.getMap(mapName, mapOptions);
		 	theMap.setView(viewOptions);
		}
	}

	public void addPinToMap(Location location, String iconPath, Infobox itemInfobox) {
				
		PushpinOptions options = PushpinOptions.setPushPinOptions(25, 28, "http://www.facebook.com/images/map_pushpin.png", false, null, null, null);
		Pushpin pushpin = Pushpin.getPushpin(location, options);
		
		theMap.addPinWithClickInfobox(pushpin, itemInfobox);
	}

	
	public static native boolean isBingMapsLibraryLoaded() /*-{
		return !(($wnd.Microsoft === "undefined") || ($wnd.eval("typeof Microsoft") === "undefined"));
	}-*/;

	
}
