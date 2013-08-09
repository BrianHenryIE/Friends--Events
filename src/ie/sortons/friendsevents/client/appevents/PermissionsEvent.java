package ie.sortons.friendsevents.client.appevents;

import ie.sortons.gwtfbplus.client.overlay.Permissions;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.web.bindery.event.shared.binder.GenericEvent;

public class PermissionsEvent extends GenericEvent {
	
	  private final JavaScriptObject response;
	  private final Permissions permissionsObject;

	  public PermissionsEvent(JavaScriptObject response) {

		  System.out.println("fbCore.getLoginStatus() onSuccess");
		  
		  this.response = response;
		  
		  // Parse the json to an object
		  permissionsObject = response.cast();

	  }

	  public JavaScriptObject getResponse() {
	    return response;
	  }
		  
	  public Permissions getLoginObject() {
	    return permissionsObject;
	  }
		  
}
