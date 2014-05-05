package ie.sortons.friendsevents.client.views;

import ie.sortons.gwtfbplus.client.api.FbUi;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * This shouldn't be a UI Binder!
 * 
 * @author Brian Henry
 */
public class AppHeading extends Composite {


	private static AppHeadingUiBinder uiBinder = GWT
			.create(AppHeadingUiBinder.class);

	interface AppHeadingUiBinder extends UiBinder<Widget, AppHeading> {
	}

	@UiField
	Anchor shareLink;
	
	public AppHeading() {
		initWidget(uiBinder.createAndBindUi(this));
		FriendsEventsResources resources = FriendsEventsResources.INSTANCE;
		resources.css().ensureInjected();
		this.setStyleName(resources.css().appHeading());
	}

	@UiHandler("shareLink")
	void onClick(ClickEvent e) {	
		FbUi.Feed("http://apps.facebook.com/sortonsevents/", null, "Friends' Events", null, "See a map of events your friends have been invited to.",
				new AsyncCallback<JavaScriptObject>() {

					@Override
					public void onFailure(Throwable caught) {
						// Don't really want to do much now. Maybe say thanks?
						
					}

					@Override
					public void onSuccess(JavaScriptObject result) {
						// Something went wrong
						
					}
		});
	}
}
