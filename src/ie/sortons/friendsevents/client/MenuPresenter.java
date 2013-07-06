package ie.sortons.friendsevents.client;

import ie.sortons.friendsevents.client.events.NotLoggedInEvent;
import ie.sortons.friendsevents.client.events.PermissionsPresentEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class MenuPresenter {

	  interface MyEventBinder extends EventBinder<MenuPresenter> {}
	  private static final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

	  private HasWidgets view;
	  
	  private String requiredPermissions;
	  
	  MenuPresenter(EventBus eventBus, String requiredPermissions) {
		  this.requiredPermissions = requiredPermissions;
		  eventBinder.bindEventHandlers(this, eventBus);
	  }

	  void setView(HasWidgets view) {
	    this.view = view;
	  }

	  @EventHandler
	  void notLoggedIn(NotLoggedInEvent event) {
	    view.clear();
	    addAppButton();
	  }
	  
	  @EventHandler
	  void loggedInWithPermissiosn(PermissionsPresentEvent event) {
		System.out.println("PermissionsPresentEvent seen by MenuPresenter");
	    view.clear();
	  }

		
		HTML addAppButton = new HTML();

		private void addAppButton() {
			
			System.out.println("Add \"Add App\" button");

			addAppButton.setHTML("<div id=\"footer\"><span class=\"greenButton\"><a id=\"addlink\" >Add Application</a></span> </div>");
			 
			// Listen for mouse events on the addAppButton.
			addAppButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					LoginController.login(requiredPermissions);
				}
			});
			
			view.add(addAppButton);
		}
		

	}