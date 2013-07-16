package ie.sortons.friendsevents.client.widgets;

import ie.sortons.friendsevents.client.facebook.overlay.FqlEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

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
	

	public MapEventWidget(FqlEvent rowEvent) {
		initWidget(uiBinder.createAndBindUi(this));
		
		eventPicture.setUrl(rowEvent.getPic_square());
		
		eventLink.setText(rowEvent.getName());
		eventLink.setHref("http://www.facebook.com/event.php?eid="  + rowEvent.getEid());
		eventLink.setTarget("_blank");
				
	    startTime.setText(rowEvent.getStartTimeString());
	    
	    location.setText(rowEvent.getLocation());
	    if(rowEvent.getVenue().getId()!=null){
	    	location.setHref("//www.facebook.com/"+rowEvent.getVenue().getId());
	    }
	    location.setTarget("_blank");
	}


}
