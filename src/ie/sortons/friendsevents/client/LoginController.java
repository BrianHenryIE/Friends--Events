package ie.sortons.friendsevents.client;

import ie.sortons.friendsevents.client.events.LoginEvent;
import ie.sortons.friendsevents.client.events.NotLoggedInEvent;
import ie.sortons.friendsevents.client.events.PermissionsEvent;
import ie.sortons.friendsevents.client.events.PermissionsPresentEvent;
import ie.sortons.friendsevents.client.facebook.overlay.DataObject;
import ie.sortons.friendsevents.client.facebook.overlay.Permissions;
import ie.sortons.friendsevents.client.gwtfb.FBCore;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

class LoginController {

	private static FBCore fbCore = GWT.create(FBCore.class);
	
	interface MyEventBinder extends EventBinder<LoginController> {}
	private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);
	  
	private static EventBus eventBus;
	private String requiredPermissions;
	  
	LoginController(EventBus eventBus, String requiredPermissions) {
		LoginController.eventBus = eventBus;
		this.requiredPermissions = requiredPermissions;
	    eventBinder.bindEventHandlers(this, eventBus);
	}

	@EventHandler
	void onLoginEvent(LoginEvent event) {
		System.out.println("LoginEvent seen by Login Controller");
		
		if(event.getLoginObject().isConnected() == false) {
			System.out.println("We are not connected");
			
			eventBus.fireEvent(new NotLoggedInEvent());
			
		} else {
			System.out.println("We are connected");
			
			// check we've got the correct permissions
			fbCore.api ( "/me/permissions", new AsyncCallback<JavaScriptObject>(){
		 		public void onSuccess ( JavaScriptObject response ) {
		 			eventBus.fireEvent(new PermissionsEvent(response));
		 		}
		 	 	public void onFailure(Throwable caught) {
	 	 			// Print something on the screen about no response from fb.
	 	 			throw new RuntimeException ( caught );
	 			}
			} );
		}
	}
	
	@EventHandler
	void onPermissionsEvent(PermissionsEvent event) {
		System.out.println("PermissionsEvent seen by Login Controller");
		
		// TODO
		// Tidy this up
		DataObject permissionsDo = event.getResponse().cast();
		DataObject permissions2 = permissionsDo.getObject("data").cast();
		Permissions perms = permissions2.getObject("0").cast();	 			
		
		if(perms.hasPermissions(requiredPermissions)){
		
			System.out.println("All required permissions present");
			
			eventBus.fireEvent(new PermissionsPresentEvent());
			
			// addAppButton.setVisible(false);
			// getPageOfEvents(resultPanel);
			
		} else {
			System.out.println("Some required permissions missing");

			// TODO
			// Alert the user that we need more permissions
			
		}
	}
	

	public static void login(String requiredPermissions) {
		fbCore.login(new AsyncCallback<JavaScriptObject>(){
	 		public void onSuccess ( JavaScriptObject response ) {
	 			eventBus.fireEvent(new LoginEvent(response));
	 		}
	 	 	public void onFailure(Throwable caught) {
 	 			// Print something on the screen about no response from fb.
 	 			throw new RuntimeException ( caught );
 			}
		}, requiredPermissions);
	}


	public void getLoginStatus() {
		fbCore.getLoginStatus( new AsyncCallback<JavaScriptObject>(){
	 		public void onSuccess ( JavaScriptObject response ) {
	 			System.out.println("firing logged in event");
	 			eventBus.fireEvent(new LoginEvent(response));
	 		}
	 	 	public void onFailure(Throwable caught) {
 	 			// Print something on the screen about no response from fb.
 	 			throw new RuntimeException ( caught );
 			}
		} );
		
	}
}
