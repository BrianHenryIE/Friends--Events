package ie.sortons.friendsevents.client.gwtbingmaps;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @see http://msdn.microsoft.com/en-us/library/gg675210.aspx
 */
public class InfoboxOptions  extends JavaScriptObject {

	protected InfoboxOptions(){ }		


	public static InfoboxOptions getInfoboxOptions(Integer width, Integer height, String id, Boolean showCloseButton, Integer zIndex, Boolean showPointer, Boolean visible, String title, String description, String htmlContent) {
		return getInfoboxOptions(String.valueOf(width), String.valueOf(height), id, String.valueOf(showCloseButton), String.valueOf(zIndex), String.valueOf(showPointer), String.valueOf(visible), title, description, htmlContent);
	}

	/**
	 * JSNI doesn't autobox nicely, so the other method deals with that.
	 */
	private static native InfoboxOptions getInfoboxOptions(String width, String height, String id, String showCloseButton, String zIndex, String showPointer, String visible, String title, String description, String htmlContent) /*-{ 
		var options = {};
		
		//TODO
		//Sort this out
		
		options.width = width;
		options.height = height;
		options.id = id;
		options.showCloseButton = showCloseButton;
		options.zIndex = zIndex;
		options.showPointer = showPointer;
		options.visible = visible;
		options.title = title;
		options.description = description;
		options.htmlContent = htmlContent;
		
		// TODO
		// offset:new Microsoft.Maps.Point(10,0)
		
		return options;
	}-*/;
	


}