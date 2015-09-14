package ie.sortons.friendsevents.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.kfuntak.gwt.json.serialization.client.HashMapSerializer;

import ie.sortons.friendsevents.client.appevents.LoginEvent;
import ie.sortons.friendsevents.client.presenter.FriendsEventsPresenter;
import ie.sortons.friendsevents.client.presenter.Presenter;
import ie.sortons.friendsevents.client.presenter.WelcomePresenter;
import ie.sortons.friendsevents.client.views.AppHeading;
import ie.sortons.gwtfbplus.client.api.Canvas;
import ie.sortons.gwtfbplus.client.api.Canvas.PageInfo;
import ie.sortons.gwtfbplus.client.api.FBCore;
import ie.sortons.gwtfbplus.client.overlay.FbResponse;
import ie.sortons.gwtfbplus.client.overlay.LoginResponse;
import ie.sortons.gwtfbplus.client.resources.GwtFbPlusResources;
import ie.sortons.gwtfbplus.shared.domain.Permission;

public class AppController implements Presenter {

	// interface MyEventBinder extends EventBinder<AppController> { }
	// private static final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

	// Courtesy of gwtfb.com
	private FBCore fbCore = GWT.create(FBCore.class);

	String APPID;

	private boolean status = true;
	private boolean xfbml = true;
	private boolean cookie = true;

	private String requiredPermissions = "user_location,user_events";

	EventBus eventBus;

	FlowPanel app = new FlowPanel();

	SimplePanel appContainer = new SimplePanel();

	Image loadingImage;
	RpcService rpcService;

	public static native boolean isDevMode() /*-{
												return ($wnd.window.location.href.indexOf("dev")>-1) ? true : false; 
												}-*/;

	public AppController(RpcService rpcService, EventBus eventBus) {

		if (isDevMode())
			APPID = "251403644880972"; // sortonsdev
		else
			APPID = "123069381111681"; // sortonsevents

		this.rpcService = rpcService;
		this.eventBus = eventBus;

		RootPanel.get("gwt").getElement().getStyle().setPosition(Position.ABSOLUTE);
		RootPanel.get("gwt").getElement().getStyle().setWidth(100, Unit.PCT);
		RootPanel.get("gwt").getElement().getStyle().setHeight(100, Unit.PCT);

		// eventBinder.bindEventHandlers(this, eventBus);

		GwtFbPlusResources.INSTANCE.css().ensureInjected();

		GwtFbPlusResources.INSTANCE.facebookStyles().ensureInjected();

		fbCore.init(APPID, status, cookie, xfbml);

		app.add(new AppHeading());
		app.add(appContainer);

		loadingImage = new Image(GwtFbPlusResources.INSTANCE.loadingAnimation());
		loadingImage.getElement().getStyle().setDisplay(Display.BLOCK);
		loadingImage.getElement().getStyle().setProperty("margin", "auto");
		loadingImage.getElement().getStyle().setMarginTop(20, Unit.PX);
		app.add(loadingImage);

		// Tell the Facebook canvas the initial size
		staticNoScrollCalculations.run();

	}

	@Override
	public void go(HasWidgets container) {

		container.add(app);

		// Check login status, then permissions
		fbCore.getLoginStatus(new AsyncCallback<JavaScriptObject>() {
			public void onSuccess(JavaScriptObject response) {
				LoginResponse login = response.cast();

				if (login.isConnected() == false)
					showWelcomePage();
				else
					loggedIn();

			}

			public void onFailure(Throwable caught) {
				// TODO Print something on the screen about no response from fb.
				GWT.log("failed to get login status");
			}
		});

	}

	HashMapSerializer hashMapSerializer = (HashMapSerializer) GWT.create(HashMapSerializer.class);

	void loggedIn() {

		GWT.log("Logged in... check permissions.");

		// check we've got the correct permissions before attempting anything
		fbCore.api("/me/permissions", new AsyncCallback<FbResponse>() {
			public void onSuccess(FbResponse response) {

				// TODO Add convenience method to GwtProJsonSerializer
				@SuppressWarnings("unchecked")
				Map<String, Permission> permissionsMap = (HashMap<String, Permission>) hashMapSerializer.deSerialize(
						new JSONObject(response.getData()), "ie.sortons.gwtfbplus.shared.domain.Permission");

				List<Permission> permissions = new ArrayList<Permission>(permissionsMap.values());

				if (Permission.permissionsPresent(permissions, requiredPermissions)) {

					GWT.log("Required permissions present.");

					eventBus.fireEvent(new LoginEvent(response));

					app.remove(loadingImage);

					// Create the events presenter
					FriendsEventsPresenter friendsEventsPresenter = new FriendsEventsPresenter(eventBus, rpcService);
					friendsEventsPresenter.go(appContainer);

				} else {
					GWT.log("Some required permissions missing");

					// TODO
					// Alert the user that we need more permissions

					showWelcomePage();
				}
			}

			public void onFailure(Throwable caught) {
				// Print something on the screen about no response from fb.
			}
		});
	}

	private void showWelcomePage() {

		app.remove(loadingImage);
		WelcomePresenter welcomePresenter = new WelcomePresenter(eventBus, requiredPermissions);
		welcomePresenter.go(appContainer);
		welcomePresenter.setLoginCallback(new Callback<String, String>() {
			@Override
			public void onSuccess(String result) {

				loggedIn();

			}

			@Override
			public void onFailure(String reason) {
				// TODO Auto-generated method stub

			}
		});

	}

	//
	Timer staticNoScrollCalculations = new Timer() {

		@Override
		public void run() {

			Canvas.getPageInfo(new AsyncCallback<PageInfo>() {

				@Override
				public void onFailure(Throwable caught) {
					System.out.println("fail");
				}

				@Override
				public void onSuccess(PageInfo info) {

					// Canvas.setSize(info.getClientWidth(), 1193);

					if (info.getScrollTop() < info.getOffsetTop()) {
						RootPanel.get("gwt").getElement().getStyle().setMarginTop(0, Unit.PX);
						RootPanel.get("gwt")
								.setHeight(info.getClientHeight() - info.getOffsetTop() + info.getScrollTop() + "px");
					} else {
						RootPanel.get("gwt").setHeight(info.getClientHeight() + "px");
						RootPanel.get("gwt").getElement().getStyle()
								.setMarginTop(info.getScrollTop() - info.getOffsetTop(), Unit.PX);
					}

					// TODO How much is too much?
					staticNoScrollCalculations.schedule(50);
				}
			});
		}
	};

}
