package ie.sortons.friendsevents.client.widgets;

import ie.sortons.friendsevents.client.FqlEvent;
import ie.sortons.gwtfbplus.client.overlay.AuthResponse;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
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


	private static final Date now = new Date();
	
	@UiField Anchor eventLink;

	@UiField Image eventPicture;

	@UiField Label startTime;
	
	@UiField Label location;
	
	// TODO: Editor framework
	public EventWidget(FqlEvent nextEvent) {
	
		initWidget(uiBinder.createAndBindUi(this));
	
		// System.out.println("new event list widget: "+ rowEvent.getEid() + " : " + rowEvent.getName());
		
		String accessToken = AuthResponse.getAuthResponse().getAccessToken();
		
		eventLink.setText(nextEvent.getName());
		eventLink.setHref("//www.facebook.com/events/"  + nextEvent.getEid());
		eventLink.setTarget("_blank");
		eventPicture.setUrl("https://graph.facebook.com/" + nextEvent.getEid() + "/picture?type=square&access_token=" + accessToken);
	    startTime.setText(nextEvent.getIsDateOnly() ? DateTimeFormat.getFormat("EEEE, dd MMMM, yyyy").format(nextEvent.getStartTimeDate()) : DateTimeFormat.getFormat("EEEE, dd MMMM, yyyy, 'at' k:mm").format(nextEvent.getStartTimeDate()) );
	    location.setText(nextEvent.getLocation());

	    if(!nextEvent.getIsDateOnly() && nextEvent.getStartTimeDate().before(now))
	    	this.getElement().getStyle().setOpacity(0.7);
		
	}

}
