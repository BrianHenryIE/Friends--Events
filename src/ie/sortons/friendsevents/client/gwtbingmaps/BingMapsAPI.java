package ie.sortons.friendsevents.client.gwtbingmaps;

import com.google.gwt.core.client.JavaScriptObject;

public class BingMapsAPI {

	// TODO
	// ScriptInject the JS
	static {
	}
	
	
	public static native boolean isBingMapsLibraryLoaded() /*-{
		return !(($wnd.Microsoft === "undefined") || ($wnd.eval("typeof Microsoft") === "undefined"));
	}-*/;


	//TODO
	// $doc.map needs to be $doc.[mapName] so multiple maps can be used
	// Maybe returning a Javascript object for use later might work
	public static native void getMap(String mapName) /*-{
	 	$doc.map = new $wnd.Microsoft.Maps.Map($doc.getElementById(mapName),
	 	{credentials: "ApmYYZr2urnVJhMJMOaMgjhH7lAISlyMpSEkIs6cqxYMwg85epCC6c1ZXgWIWFao",
	 	mapTypeId: $wnd.Microsoft.Maps.MapTypeId.road,
	 	center: new $wnd.Microsoft.Maps.Location(35.906849,-118.937988),
	 	zoom: 5});
	}-*/;
	
	
	public static ViewOptions setViewOptions(Boolean animate, MicrosoftMapsLocation center, Point centerOffset, Integer heading, String mapTypeId, Integer padding, Integer zoom){
		return setViewOptions(String.valueOf(animate), center, centerOffset, String.valueOf(heading), mapTypeId, String.valueOf(padding), String.valueOf(zoom));
	}
	
		
	private static native ViewOptions setViewOptions(String animate, MicrosoftMapsLocation center, Point centerOffset, String heading, String mapTypeId, String padding, String zoom) /*-{
		var viewOptions = {};
					
		viewOptions.animate = animate;
		viewOptions.bounds = bounds;
		viewOptions.center = center;
		viewOptions.centerOffset = centerOffset;
		viewOptions.heading = heading;
		// viewOptions.labelOverlay = labelOverlay; // Not implemented!
		viewOptions.mapTypeId = mapTypeId;
		viewOptions.padding = padding;
		viewOptions.zoom = zoom;
		
		return viewOptions;
	}-*/;
	
		
	/**
 	 * @param credentials	The Bing Maps Key used to authenticate the application. This property can only be set when using the Map constructor.
 	 * @param width	The width of the map. The default value is null. If no width is specified, the width of the div is used. If width is specified, then height must be specified as well.
 	 * @param height	The height of the map. The default value is null. If no height is specified, the height of the div is used. If height is specified, then width must be specified as well.
 	 * @param customizeOverlays	A boolean indicating whether to load the new Bing Maps overlay styles. The default value is false. This property setting only takes effect if the Microsoft.Maps.Overlays.Style module is loaded.
 	 * @param disableBirdseye	A boolean indicating whether to disable the bird's eye map type. The default value is false. If this property is set to true, bird's eye will be removed from the map navigation control and thebirdseyeMapTypeId is disabled. Additionally, the auto map type will only display road or aerial. This property can only be set when using the Map constructor.
 	 * @param disableKeyboardInput	A boolean value indicating whether to disable the map's response to keyboard input. The default value isfalse.
 	 * @param disableMouseInput	A boolean value indicating whether to disable the map's response to mouse input. The default value isfalse.
 	 * @param disablePanning	A boolean value indicating whether to disable the user's ability to pan the map. The default value isfalse.
 	 * @param disableTouchInput	A boolean value indicating whether to disable the map's response to touch input. The default value isfalse.
 	 * @param disableUserInput	A boolean value indicating whether to disable the map's response to any user input. The default value isfalse.
 	 * @param disableZooming	A boolean value indicating whether to disable the user's ability to zoom in or out. The default value is false.
 	 * @param enableClickableLogo	A boolean value indicating whether the BingTM logo on the map is clickable. The default value is true. This property can only be set when using the Map constructor.
 	 * @param enableSearchLogo	A boolean value indicating whether to enable the BingTM hovering search logo on the map. The default value is true. This property can only be set when using the Map constructor.
 	 * @param fixedMapPosition	A boolean indicating whether the div containing the map control is fixed on the page and the browser will not be resized. The default value is false. In this case the map control redraws if necessary based on any div or window resize. If this property is set to true, the map control does not check the size of the div containing it every time the map view changes, thus increasing the performance of the control. This property can only be set when using the Map constructor.	
 	 * @param inertiaIntensity	A number between 0 and 1 specifying the intensity of the inertia animation effect. The inertia effect increases as the intensity value gets larger. The default value is .85. Setting this property to 0 indicates no inertia effect. The useInertia property must be set to true for the inertiaIntensity value to have an effect.
 	 * @param showBreadcrumb	A boolean value indicating whether to display the "breadcrumb control". The breadcrumb control shows the current center location's geography hierarchy. For example, if the location center is Seattle, the breadcrumb control displays "World . United States . WA". The default value is false. The breadcrumb control displays best when the width of the map is at least 300 pixels. This property can only be set when using the Map constructor.
 	 * @param showCopyright	A boolean value indicating whether or not to show the map copyright. The default value is true. This property can only be set when using the Map constructor. Important: Bing Maps Platform API Terms of Use requires copyright information to be displayed. Only set this option to false when copyright information is displayed through alternate means.
 	 * @param showDashboard	A boolean value indicating whether to show the map navigation control. The default value is true. This property can only be set when using the Map constructor.
 	 * @param showMapTypeSelector	A boolean value indicating whether to show the map type selector in the map navigation control. The default value is true. This property can only be set when using the Map constructor.
 	 * @param showScalebar	A boolean value indicating whether to show the scale bar. The default value is true. This property can only be set when using the Map constructor.	
 	 * @param tileBuffer	A number between 0 and 4 specifying how many tiles to use as a buffer around the map view. The default value is 0.
 	 * @param useInertia	A boolean value indicating whether to use the inertia animation effect during map navigation. The default value is true.
	 * @return
	 * @see http://msdn.microsoft.com/en-us/library/gg427603.aspx
	 */
	public static MapOptions setMapOptions(String credentials, Integer width, Integer height, Boolean customizeOverlays, Boolean disableBirdseye, Boolean disableKeyboardInput, Boolean disableMouseInput, Boolean disablePanning, Boolean disableTouchInput, Boolean disableUserInput, Boolean disableZooming, Boolean enableClickableLogo, Boolean enableSearchLogo, Boolean fixedMapPosition, Integer inertiaIntensity, Boolean showBreadcrumb, Boolean showCopyright, Boolean showDashboard, Boolean showMapTypeSelector, Boolean showScalebar, Integer tileBuffer, Boolean useInertia) {
		return setMapOptions(credentials, String.valueOf(width), String.valueOf(height), String.valueOf(customizeOverlays), String.valueOf(disableBirdseye), String.valueOf(disableKeyboardInput), String.valueOf(disableMouseInput), String.valueOf(disablePanning), String.valueOf(disableTouchInput), String.valueOf(disableUserInput), String.valueOf(disableZooming), String.valueOf(enableClickableLogo), String.valueOf(enableSearchLogo), String.valueOf(fixedMapPosition), String.valueOf(inertiaIntensity), String.valueOf(showBreadcrumb), String.valueOf(showCopyright), String.valueOf(showDashboard), String.valueOf(showMapTypeSelector), String.valueOf(showScalebar), String.valueOf(tileBuffer), String.valueOf(useInertia));
	}
	
	
	private static native MapOptions setMapOptions(String credentials, String width, String height, String customizeOverlays, String disableBirdseye, String disableKeyboardInput, String disableMouseInput, String disablePanning, String disableTouchInput, String disableUserInput, String disableZooming, String enableClickableLogo, String enableSearchLogo, String fixedMapPosition, String inertiaIntensity, String showBreadcrumb, String showCopyright, String showDashboard, String showMapTypeSelector, String showScalebar, String tileBuffer, String useInertia) /*-{
		var mapOptions = {};
				
		// mapOptions.backgroundColor = backgroundColor; // Not implemented!
		mapOptions.credentials = credentials;
		mapOptions.customizeOverlays = customizeOverlays;
		mapOptions.disableBirdseye = disableBirdseye;
		mapOptions.disableKeyboardInput = disableKeyboardInput;
		mapOptions.disableMouseInput = disableMouseInput;
		mapOptions.disablePanning = disablePanning;
		mapOptions.disableTouchInput = disableTouchInput;
		mapOptions.disableUserInput = disableUserInput;
		mapOptions.disableZooming = disableZooming;
		mapOptions.enableClickableLogo = enableClickableLogo;
		mapOptions.enableSearchLogo = enableSearchLogo;
		mapOptions.fixedMapPosition = fixedMapPosition;	
		mapOptions.height = height;
		mapOptions.inertiaIntensity = inertiaIntensity;
		mapOptions.showBreadcrumb = showBreadcrumb;	
		mapOptions.showCopyright = showCopyright;
		mapOptions.showDashboard = showDashboard;
		mapOptions.showMapTypeSelector = showMapTypeSelector;
		mapOptions.showScalebar = showScalebar;
		// mapOptions.theme = theme; // Not implemented!
		mapOptions.tileBuffer = tileBuffer;
		mapOptions.useInertia = useInertia;
		mapOptions.width = width;
				
		return mapOptions;
	}-*/;
	
	
	public static native void addPinToMap(MicrosoftMapsLocation location, PushPinOptions options) /*-{

		var pushpin = new $wnd.Microsoft.Maps.Pushpin(location, options);
		$doc.map.entities.push(pushpin);
		
	}-*/;
		
	public static native void addPinToMap(MicrosoftMapsLocation location, PushPinOptions options, MicrosoftMapsInfobox pinInfobox) /*-{

		var pushpin = new $wnd.Microsoft.Maps.Pushpin(location, options);

		// Add handler for the pushpin click event.
		$wnd.Microsoft.Maps.Events.addHandler(pushpin, 'click', function displayInfobox(e) { pinInfobox.setOptions({ visible:true }); } );  
		
		// Hide the infobox when the map is moved/clicked.
        $wnd.Microsoft.Maps.Events.addHandler($doc.map, 'viewchange', function hideInfobox(e) { pinInfobox.setOptions({ visible: false }); });
        $wnd.Microsoft.Maps.Events.addHandler($doc.map, 'click', function hideInfobox(e) { pinInfobox.setOptions({ visible: false }); });
		
		pinInfobox.setOptions({ visible: false });
		
		// Add both to the map		
		$doc.map.entities.push(pushpin);
		$doc.map.entities.push(pinInfobox);
	}-*/;

	
	public static PushPinOptions setPushPinOptions(Integer width, Integer height, String icon, Boolean draggable, String text, Boolean visible, String htmlContent) {
		return setPushPinOptions(String.valueOf(width), String.valueOf(height), icon, String.valueOf(draggable), text, String.valueOf(visible), htmlContent);
	}

	
	/**
	 * JSNI doesn't autobox nicely, so the other method deals with that.
	 */
	private static native PushPinOptions setPushPinOptions(String width, String height, String icon, String draggable, String text, String visible, String htmlContent) /*-{ 
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

	
	public static native MicrosoftMapsInfobox createInfoBox(MicrosoftMapsLocation location, InfoboxOptions infoboxOptions) /*-{
		 var newInfobox = new $wnd.Microsoft.Maps.Infobox(location, infoboxOptions);    
		 return newInfobox;
	}-*/;
	
	
	/**
	 * @see http://msdn.microsoft.com/en-us/library/gg675210.aspx
	 */
	public static InfoboxOptions setInfoboxOptions(Integer width, Integer height, String id, Boolean showCloseButton, Integer zIndex, Boolean showPointer, Boolean visible, String title, String description, String htmlContent) {
		return setInfoboxOptions(String.valueOf(width), String.valueOf(height), id, String.valueOf(showCloseButton), String.valueOf(zIndex), String.valueOf(showPointer), String.valueOf(visible), title, description, htmlContent);
	}

	
	/**
	 * JSNI doesn't autobox nicely, so the other method deals with that.
	 */
	private static native InfoboxOptions setInfoboxOptions(String width, String height, String id, String showCloseButton, String zIndex, String showPointer, String visible, String title, String description, String htmlContent) /*-{ 
		var options = {};
		
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
	
	
	public static native MicrosoftMapsLocation microsoftMapsLocation(String latitude, String longitude) /*-{
		return new $wnd.Microsoft.Maps.Location(latitude, longitude);
	}-*/;
	
	
	// TODO
	// Map name
	public static native void addInfoBoxToMap(MicrosoftMapsInfobox infobox) /*-{			 
		 $doc.map.entities.push($doc.infobox);
	}-*/;
	
	
	// TODO
	// Map name
	public static native void removeAllInfoboxes() /*-{
			
		for(var i=$doc.map.entities.getLength()-1;i>=0;i--) {
			var pushpin= $doc.map.entities.get(i); 
			if (pushpin instanceof $wnd.Microsoft.Maps.Infobox) { 
				$doc.map.entities.removeAt(i);  
			};
		} 	 
	}-*/;
	
	
	
	// Classes extending JavaScriptObject
	
	public static class ViewOptions extends JavaScriptObject {
		protected ViewOptions(){ }		
	}	
	
	public static class MapOptions extends JavaScriptObject {
		protected MapOptions(){ }		
	}
	
	public static class MicrosoftMapsLocation extends JavaScriptObject { 
		protected MicrosoftMapsLocation(){
		}
	}

	public static class PushPinOptions extends JavaScriptObject {
		protected PushPinOptions(){ }		
	}
		
	public static class MicrosoftMapsInfobox extends JavaScriptObject { 
		protected MicrosoftMapsInfobox(){
		}
	}
	
	public static class InfoboxOptions extends JavaScriptObject {
		protected InfoboxOptions(){ }		
	}
	
	public static class Point extends JavaScriptObject {
		protected Point(){ }		
	}
		
}
