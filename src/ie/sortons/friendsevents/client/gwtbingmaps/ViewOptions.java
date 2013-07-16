package ie.sortons.friendsevents.client.gwtbingmaps;

import com.google.gwt.core.client.JavaScriptObject;

public class ViewOptions extends JavaScriptObject {
	
	protected ViewOptions(){ }		
	

	public static ViewOptions getViewOptions(Boolean animate, Location center, Point centerOffset, Integer heading, String mapTypeId, Integer padding, Integer zoom){
		return getViewOptions(String.valueOf(animate), center, centerOffset, String.valueOf(heading), mapTypeId, String.valueOf(padding), String.valueOf(zoom));
	}
	
	
	private static native ViewOptions getViewOptions(String animate, Location center, Point centerOffset, String heading, String mapTypeId, String padding, String zoom) /*-{
		var viewOptions = {};
		
		// It was very difficult to pass the boolean in to here while also allowing it to be null 
		if(animate != "null") { animate == "true" ? viewOptions.animate = true : viewOptions.animate = false; }
		  
		// viewOptions.bounds = bounds; // Not implemented!
		
		viewOptions.center = center;
		viewOptions.centerOffset = centerOffset; 
		
		if(heading != "null") { viewOptions.heading = parseInt(heading); }
		
		// viewOptions.labelOverlay = labelOverlay; // Not implemented!
		
		viewOptions.mapTypeId = mapTypeId; 

		if(padding != "null") { viewOptions.padding = parseInt(padding); }
		if(zoom != "null") { viewOptions.zoom = parseInt(zoom); }
		
		return viewOptions;
	}-*/;
	
}
