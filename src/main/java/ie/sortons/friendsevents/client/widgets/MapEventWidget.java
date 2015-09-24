package ie.sortons.friendsevents.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import ie.sortons.gwtfbplus.client.overlay.AuthResponse;
import ie.sortons.gwtfbplus.client.overlay.graph.GraphEvent;

public class MapEventWidget extends Composite {

	private static MapEventWidgetUiBinder uiBinder = GWT
			.create(MapEventWidgetUiBinder.class);

	interface MapEventWidgetUiBinder extends UiBinder<Widget, MapEventWidget> {
	}

	public MapEventWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	@UiField Anchor eventLink;

	@UiField Image eventPicture;

	@UiField Label startTime;
	
	@UiField Anchor location;
	

	public MapEventWidget(GraphEvent rowEvent) {
		initWidget(uiBinder.createAndBindUi(this));
		
		// System.out.println("new map widget: "+ rowEvent.getEid() + " : " + rowEvent.getName());
		String accessToken = AuthResponse.getAuthResponse().getAccessToken();
		eventPicture.setUrl("https://graph.facebook.com/" + rowEvent.getId() + "/picture?type=square&access_token="+accessToken);
		
		eventLink.setText(rowEvent.getName());
		eventLink.setHref("//www.facebook.com/events/"  + rowEvent.getId());
		eventLink.setTarget("_blank");
				
	    startTime.setText(rowEvent.getStartTime());
	    
	    location.setText(rowEvent.getPlace().getName());
	    if(rowEvent.getPlace().getId()!=null){
	    	location.setHref("//www.facebook.com/"+rowEvent.getPlace().getId());
	    }
	    location.setTarget("_blank");
	}


}
