package ie.sortons.friendsevents.client.gwtbingmaps;

import com.google.gwt.core.client.JavaScriptObject;

public class PushpinOptions extends JavaScriptObject {
	
	protected PushpinOptions() {}
	
	
	public static PushpinOptions setPushPinOptions(Integer width, Integer height, String icon, Boolean draggable, String text, Boolean visible, String htmlContent) {
		return setPushPinOptions(String.valueOf(width), String.valueOf(height), icon, String.valueOf(draggable), text, String.valueOf(visible), htmlContent);
	}

	/**
	 * JSNI doesn't autobox nicely, so the other method deals with that.
	 */
	private static native PushpinOptions setPushPinOptions(String width, String height, String icon, String draggable, String text, String visible, String htmlContent) /*-{ 
		var options = {};
		
		options.width = width;
		options.height = height;
		options.icon = icon;
		options.draggable = draggable;
		options.text = text;
		options.visible = visible;
		options.htmlContent = htmlContent;
		
		// TODO
		// textOffset: Microsoft.Maps.Point(0, 5)
		
		return options;
	}-*/;

}
