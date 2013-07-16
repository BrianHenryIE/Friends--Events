package ie.sortons.friendsevents.client.gwtbingmaps;

import com.google.gwt.core.client.JavaScriptObject;

public class Pushpin extends JavaScriptObject {
	
	protected Pushpin(){ }	
	
	public static native Pushpin getPushpin(Location location, PushpinOptions options) /*-{

		var pushpin = new $wnd.Microsoft.Maps.Pushpin(location, options);
		return pushpin;
		
	}-*/;

}
