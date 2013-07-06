package ie.sortons.friendsevents.client.facebook;

/**
 * Facebook posts data to the servlet, the signed request
 * our servlet decodes it and then these methods can be used
 * to read it.
 * 
 * @author brianhenry
 *
 */
public class ServedData {


	/**
	 * Reads the servlet outputted html for a uid passed by Facebook in the signed_request
	 * @return uid as String
	 */
	private static native String getUId() /*-{ return (typeof $wnd._rs_info === "undefined" ? "" : $wnd._rs_info.fbUid ) }-*/;
	
	
}
