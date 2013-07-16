package ie.sortons.friendsevents.client.gwtbingmaps;


import com.google.gwt.core.client.JavaScriptObject;


/*
These properties can only be set when using the Map constructor.:

disableBirdseye
enableClickableLogo
enableSearchLogo
fixedMapPosition
showBreadcrumb
showCopyright
showDashboard
showMapTypeSelector 
showScalebar

*/
public class Map extends JavaScriptObject {
	
	protected Map() { }
	
	/**
	 * Why anyone would create a map without the credentials is beyond
	 * me, but Microsoft allow it, so here it is.
	 * 
	 * @param mapName
	 */
	public final Map getMap(String mapName) {
		return getMap(mapName, null);
	}
	
	public static native Map getMap(String mapName, MapOptions mapOptions) /*-{
 		$doc[mapName] = new $wnd.Microsoft.Maps.Map($doc.getElementById(mapName), mapOptions);
		return $doc[mapName];
	}-*/;
	


	public final native void setView(ViewOptions viewOptions) /*-{
		this.setView(viewOptions);
	}-*/;
	
	

	
	
	
	
	public final native void addPinToMap(Pushpin pushpin) /*-{
		this.entities.push(pushpin);
	}-*/;
	
	
	public final native void addPinWithClickInfobox(Pushpin pushpin, Infobox pinInfobox) /*-{

		// Add handler for the pushpin click event.
		$wnd.Microsoft.Maps.Events.addHandler(pushpin, 'click', function displayInfobox(e) { pinInfobox.setOptions({ visible:true }); } );  
		
		// Hide the infobox when the map is moved/clicked.
        $wnd.Microsoft.Maps.Events.addHandler(this, 'viewchange', function hideInfobox(e) { pinInfobox.setOptions({ visible: false }); });
        $wnd.Microsoft.Maps.Events.addHandler(this, 'click', function hideInfobox(e) { pinInfobox.setOptions({ visible: false }); });
		
		pinInfobox.setOptions({ visible: false });
		
		// Add both to the map		
		this.entities.push(pushpin);
		this.entities.push(pinInfobox);
	}-*/;

	
	// TODO
	// Map name
	public final native void addInfoBoxToMap(Infobox infobox) /*-{			 
		 this.entities.push($doc.infobox);
	}-*/;
	
	
	// TODO
	// Map name
	public final native void removeAllInfoboxes() /*-{
			
		for(var i=this.entities.getLength()-1;i>=0;i--) {
			var pushpin= this.entities.get(i); 
			if (pushpin instanceof $wnd.Microsoft.Maps.Infobox) { 
				this.entities.removeAt(i);  
			};
		} 	 
	}-*/;
	
	
}
