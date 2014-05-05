package ie.sortons.friendsevents.client.appevents;

import ie.sortons.gwtfbplus.client.overlay.LoginResponse;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.web.bindery.event.shared.binder.GenericEvent;

public class LoginEvent extends GenericEvent {

	private final LoginResponse loginObject;

	public LoginEvent(JavaScriptObject response) {
		loginObject = response.cast();
	}

	public LoginResponse getLoginObject() {
		return loginObject;
	}

}