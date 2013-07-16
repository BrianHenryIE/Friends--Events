package ie.sortons.friendsevents.client.gwtbingmaps;

import com.google.gwt.core.client.JavaScriptObject;

public class MapOptions extends JavaScriptObject {
	protected MapOptions(){ }		
	
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
	public static MapOptions getMapOptions(String credentials, Integer width, Integer height, Boolean customizeOverlays, Boolean disableBirdseye, Boolean disableKeyboardInput, Boolean disableMouseInput, Boolean disablePanning, Boolean disableTouchInput, Boolean disableUserInput, Boolean disableZooming, Boolean enableClickableLogo, Boolean enableSearchLogo, Boolean fixedMapPosition, Integer inertiaIntensity, Boolean showBreadcrumb, Boolean showCopyright, Boolean showDashboard, Boolean showMapTypeSelector, Boolean showScalebar, Integer tileBuffer, Boolean useInertia) {
		return getMapOptions(credentials, String.valueOf(width), String.valueOf(height), String.valueOf(customizeOverlays), String.valueOf(disableBirdseye), String.valueOf(disableKeyboardInput), String.valueOf(disableMouseInput), String.valueOf(disablePanning), String.valueOf(disableTouchInput), String.valueOf(disableUserInput), String.valueOf(disableZooming), String.valueOf(enableClickableLogo), String.valueOf(enableSearchLogo), String.valueOf(fixedMapPosition), String.valueOf(inertiaIntensity), String.valueOf(showBreadcrumb), String.valueOf(showCopyright), String.valueOf(showDashboard), String.valueOf(showMapTypeSelector), String.valueOf(showScalebar), String.valueOf(tileBuffer), String.valueOf(useInertia));
	}
	
	private static native MapOptions getMapOptions(String credentials, String width, String height, String customizeOverlays, String disableBirdseye, String disableKeyboardInput, String disableMouseInput, String disablePanning, String disableTouchInput, String disableUserInput, String disableZooming, String enableClickableLogo, String enableSearchLogo, String fixedMapPosition, String inertiaIntensity, String showBreadcrumb, String showCopyright, String showDashboard, String showMapTypeSelector, String showScalebar, String tileBuffer, String useInertia) /*-{
		var mapOptions = {};
				
		// mapOptions.backgroundColor = backgroundColor; // Not implemented!
		mapOptions.credentials = credentials;
		
		if(customizeOverlays != "null") { customizeOverlays == "true" ? mapOptions.customizeOverlays = true : mapOptions.customizeOverlays = false; }
		if(disableBirdseye != "null") { disableBirdseye == "true" ? mapOptions.disableBirdseye = true : mapOptions.disableBirdseye = false; }
		if(disableKeyboardInput != "null") { disableKeyboardInput == "true" ? mapOptions.disableKeyboardInput = true : mapOptions.disableKeyboardInput = false; }
		if(disableMouseInput != "null") { disableMouseInput == "true" ? mapOptions.disableMouseInput = true : mapOptions.disableMouseInput = false; }
		if(disablePanning != "null") { disablePanning == "true" ? mapOptions.disablePanning = true : mapOptions.disablePanning = false; }
		if(disableTouchInput != "null") { disableTouchInput == "true" ? mapOptions.disableTouchInput = true : mapOptions.disableTouchInput = false; }
		if(disableUserInput != "null") { disableUserInput == "true" ? mapOptions.disableUserInput = true : mapOptions.disableUserInput = false; }
		if(disableZooming != "null") { disableZooming == "true" ? mapOptions.disableZooming = true : mapOptions.disableZooming = false; }
		if(enableClickableLogo != "null") { enableClickableLogo == "true" ? mapOptions.enableClickableLogo = true : mapOptions.enableClickableLogo = false; }
		if(enableSearchLogo != "null") { enableSearchLogo == "true" ? mapOptions.enableSearchLogo = true : mapOptions.enableSearchLogo = false; }
		if(fixedMapPosition != "null") { fixedMapPosition == "true" ? mapOptions.fixedMapPosition = true : mapOptions.fixedMapPosition = false; }
		if(height != "null") { mapOptions.height = parseFloat(height); }
		if(inertiaIntensity != "null") { mapOptions.inertiaIntensity = parseFloat(inertiaIntensity); }
		if(showBreadcrumb != "null") { showBreadcrumb == "true" ? mapOptions.showBreadcrumb = true : mapOptions.showBreadcrumb = false; }
		if(showCopyright != "null") { showCopyright == "true" ? mapOptions.showCopyright = true : mapOptions.showCopyright = false; }
		if(showDashboard != "null") { showDashboard == "true" ? mapOptions.showDashboard = true : mapOptions.showDashboard = false; }
		if(showMapTypeSelector != "null") { showMapTypeSelector == "true" ? mapOptions.showMapTypeSelector = true : mapOptions.showMapTypeSelector = false; }
		if(showScalebar != "null") { showScalebar == "true" ? mapOptions.showScalebar = true : mapOptions.showScalebar = false; }
		
		// mapOptions.theme = theme; // Not implemented!
		
		if(tileBuffer != "null") { mapOptions.tileBuffer = parseFloat(tileBuffer); }
		if(useInertia != "null") { useInertia == "true" ? mapOptions.useInertia = true : mapOptions.useInertia = false; }
		if(width != "null") { mapOptions.width = parseFloat(width); }
				
		return mapOptions;
	}-*/;
	
}