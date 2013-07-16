package ie.sortons.friendsevents.client.gwtbingmaps;

import com.google.gwt.core.client.JavaScriptObject;

public class EntityCollectionOptions extends JavaScriptObject {

	protected EntityCollectionOptions() {}
	
	public static EntityCollectionOptions getEntityCollectionOptions(Boolean bubble, Boolean visible, Integer zIndex) {
		return getEntityCollectionOptions(String.valueOf(bubble), String.valueOf(visible), String.valueOf(zIndex));
	}
		
	
	private static native EntityCollectionOptions getEntityCollectionOptions(String bubble, String visible, String zIndex) /*-{
		
		var entityCollectionOptions = {};
		
		if(bubble != "null") { bubble == "true" ? entityCollectionOptions.bubble = true : entityCollectionOptions.bubble = false; }
		if(visible != "null") { visible == "true" ? entityCollectionOptions.visible = true : entityCollectionOptions.visible = false; }
		if(zIndex != "null") { entityCollectionOptions.zIndex = parseInt(height); }
		
		return entityCollectionOptions;
	}-*/;
	
}
