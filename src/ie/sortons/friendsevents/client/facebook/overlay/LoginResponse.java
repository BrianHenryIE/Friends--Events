package ie.sortons.friendsevents.client.facebook.overlay;

import com.google.gwt.core.client.JavaScriptObject;


public class LoginResponse extends JavaScriptObject {
	
	protected LoginResponse() { }

	/**
	 * getStatus() get the current status of the userKey.
	 * 
	 * From http://developers.facebook.com/docs/reference/javascript/FB.login/
	 * > On Canvas, if the user is 'unknown' (that is, when the login status == 'unknown'), do not use the JS SDK to sign in the user, instead do a full redirect of the page to the oauth dialog so that we get a consistent state.
	 * 
	 * @return one of 
	 * "unknown" - The userKey is not logged into facebook.
	 * "not_authorized" - The userKey has not authorized the app.
	 * "connected" - Logged in and authorized the app.
	 * 
	 * oh fu facebook. On a failed (popup) authorization request, 
	 * the status returned will be "unknown". 
	 * Calling getLoginStatus() again will return "not_authorized".
	 * 
	 */
	
	public final native String getStatus()  /*-{ return this.status; }-*/;
	public final native AuthResponse getAuthResponse() /*-{ return this.authResponse; }-*/;

	public final boolean isConnected() {
		return (getStatus().equals("connected"));
	}
	
}

