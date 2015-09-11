package ie.sortons.friendsevents.client.presenter;

import ie.sortons.friendsevents.client.views.WelcomeView;
import ie.sortons.gwtfbplus.client.api.FBCore;
import ie.sortons.gwtfbplus.client.overlay.LoginResponse;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.web.bindery.event.shared.binder.EventBinder;

public class WelcomePresenter implements Presenter {

	public interface Display {
		void setPresenter(WelcomePresenter presenter);
	}

	interface MyEventBinder extends EventBinder<WelcomePresenter> {
	}

	private static final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

	private static FBCore fbCore = GWT.create(FBCore.class);
	
	WelcomeView view = new WelcomeView();

	private String requiredPermissions;

	EventBus eventBus;

	private Callback<String, String> loginCallback;
	
	public WelcomePresenter(EventBus eventBus, String requiredPermissions) {
		view.setPresenter(this);
		this.eventBus = eventBus;
		eventBinder.bindEventHandlers(this, eventBus);
		this.requiredPermissions = requiredPermissions;
	}

	@Override
	public void go(HasWidgets container) {
		container.add(view);
	}

	public void appLogin() {
		fbCore.login(new AsyncCallback<JavaScriptObject>() {
			public void onSuccess(JavaScriptObject response) {

				LoginResponse login = response.cast();

				if (login.isConnected() == false) {

					// TODO
					// Popup explaining what the permissions all do.

				} else {

					System.out.println("logged in: " + login.getAuthResponse().getUserId());
					loginCallback.onSuccess(new String());
				}

			}

			public void onFailure(Throwable caught) {
				// Do standard fail dialog
			}
		}, requiredPermissions);
	}

	public void setLoginCallback(Callback<String, String> callback) {
		this.loginCallback = callback;		
	}

}
