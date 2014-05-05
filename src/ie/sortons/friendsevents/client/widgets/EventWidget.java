package ie.sortons.friendsevents.client.widgets;

import ie.sortons.gwtfbplus.client.overlay.AuthResponse;
import ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent;

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


	@UiField Anchor eventLink;

	@UiField Image eventPicture;

	@UiField Label startTime;
	
	@UiField Label location;
	
	// TODO: Editor framework
	public EventWidget(FqlEvent rowEvent) {
	
		initWidget(uiBinder.createAndBindUi(this));
	
		// System.out.println("new event list widget: "+ rowEvent.getEid() + " : " + rowEvent.getName());
		
		String accessToken = AuthResponse.getAuthResponse().getAccessToken();
		
		eventLink.setText(rowEvent.getName());
		eventLink.setHref("//www.facebook.com/events/"  + rowEvent.getEid());
		eventLink.setTarget("_blank");
		eventPicture.setUrl("https://graph.facebook.com/" + rowEvent.getEid() + "/picture?type=square&access_token=" + accessToken);
	    startTime.setText(rowEvent.is_date_only ? DateTimeFormat.getFormat("EEEE, dd MMMM, yyyy").format(rowEvent.getStartTime()) : DateTimeFormat.getFormat("EEEE, dd MMMM, yyyy, 'at' k:mm").format(rowEvent.getStartTime()) );
	    location.setText(rowEvent.getLocation());

		
	}

}
