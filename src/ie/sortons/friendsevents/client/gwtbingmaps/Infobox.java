package ie.sortons.friendsevents.client.gwtbingmaps;

import com.google.gwt.core.client.JavaScriptObject;

public class Infobox extends JavaScriptObject { 

	protected Infobox(){}

	public static native Infobox getInfoBox(Location location, InfoboxOptions infoboxOptions) /*-{
		 var newInfobox = new $wnd.Microsoft.Maps.Infobox(location, infoboxOptions);    
		 return newInfobox;
	}-*/;
		
}
