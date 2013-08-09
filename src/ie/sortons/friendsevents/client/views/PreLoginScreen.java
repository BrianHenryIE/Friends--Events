package ie.sortons.friendsevents.client.views;

import ie.sortons.friendsevents.client.LoginController;
import ie.sortons.friendsevents.client.appevents.PermissionsPresentEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class PreLoginScreen extends Composite {

	private static PreLoginScreenUiBinder uiBinder = GWT
			.create(PreLoginScreenUiBinder.class);

	interface PreLoginScreenUiBinder extends UiBinder<Widget, PreLoginScreen> {
	}

	interface MyEventBinder extends EventBinder<PreLoginScreen> {}
	private static final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

	private String requiredPermissions;

	@UiField
	Button addAppButton;
	
	public PreLoginScreen(EventBus eventBus, String requiredPermissions) {
		eventBinder.bindEventHandlers(this, eventBus);
		initWidget(uiBinder.createAndBindUi(this));
		this.requiredPermissions = requiredPermissions;
		
		addAppButton.getElement().getStyle().setMargin(10, Unit.PX);
	}


	@UiHandler("addAppButton")
	void onClick(ClickEvent e) {	
		LoginController.login(requiredPermissions);
	}


	@EventHandler
	void loggedInWithPermissiosn(PermissionsPresentEvent event) {
		System.out.println("PermissionsPresentEvent seen by PreLoginScreen");
		this.removeFromParent();
	}

}
