package ie.sortons.friendsevents.client.gwtbingmaps;

import com.google.gwt.core.client.JavaScriptObject;


public class Location extends JavaScriptObject { 
	protected Location(){ }
	public static Location getMicrosoftMapsLocation(Double latitude, Double longitude) {
		return getMicrosoftMapsLocation(String.valueOf(latitude), String.valueOf(longitude));
	}
	
	public static native Location getMicrosoftMapsLocation(String latitude, String longitude) /*-{
		return new $wnd.Microsoft.Maps.Location(latitude, longitude);
	}-*/;
}