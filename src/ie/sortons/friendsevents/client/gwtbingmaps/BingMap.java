package ie.sortons.friendsevents.client.gwtbingmaps;

import ie.sortons.friendsevents.client.gwtbingmaps.BingMapsAPI.MicrosoftMapsInfobox;
import ie.sortons.friendsevents.client.gwtbingmaps.BingMapsAPI.MicrosoftMapsLocation;
import ie.sortons.friendsevents.client.gwtbingmaps.BingMapsAPI.PushPinOptions;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class BingMap extends Composite {

	private String credentials;
	private String mapName;
	private HTML panel;

	
	public BingMap(String credentials, String mapName) {
		this.credentials = credentials;
		this.mapName = mapName;
		panel = new HTML();
		initWidget(panel);
		this.getElement().setId(mapName);		
	}

	protected void onAttach(){
	
		if(BingMapsAPI.isBingMapsLibraryLoaded()){
			BingMapsAPI.getMap(mapName);
		}
	}

	public void addPinToMap(MicrosoftMapsLocation location, String iconPath, MicrosoftMapsInfobox itemInfobox) {
				
		PushPinOptions options = BingMapsAPI.setPushPinOptions(25, 28, "http://www.facebook.com/images/map_pushpin.png", false, null, null, null);
		
		BingMapsAPI.addPinToMap(location, options, itemInfobox);
	}
	
}
