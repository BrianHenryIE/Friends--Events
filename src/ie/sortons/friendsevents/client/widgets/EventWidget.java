package ie.sortons.friendsevents.client.widgets;

import ie.sortons.gwtfbplus.client.fql.FqlEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EventWidget extends Composite {

	private static EventWidgetUiBinder uiBinder = GWT
			.create(EventWidgetUiBinder.class);

	interface EventWidgetUiBinder extends UiBinder<Widget, EventWidget> {
	}


	@UiField Anchor eventLink;

	@UiField Image eventPicture;

	@UiField Label startTime;
	
	@UiField Label location;
	
	// TODO: Editor framework
	public EventWidget(FqlEvent rowEvent) {
	
		initWidget(uiBinder.createAndBindUi(this));
		
		eventLink.setText(rowEvent.getName());
		eventLink.setHref("http://www.facebook.com/event.php?eid="  + rowEvent.getEid());
		eventLink.setTarget("_blank");
		eventPicture.setUrl(rowEvent.getPic_square());
	    startTime.setText(rowEvent.getStartTimeString());
	    location.setText(rowEvent.getLocation());

		
	}

}
