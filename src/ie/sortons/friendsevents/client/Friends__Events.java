package ie.sortons.friendsevents.client;


import ie.sortons.friendsevents.client.appevents.NotLoggedInEvent;
import ie.sortons.friendsevents.client.appevents.PermissionsNotPresentEvent;
import ie.sortons.friendsevents.client.appevents.PermissionsPresentEvent;
import ie.sortons.friendsevents.client.appevents.UpdatedListItemsEvent;
import ie.sortons.friendsevents.client.views.AppHeading;
import ie.sortons.friendsevents.client.views.PreLoginScreen;
import ie.sortons.gwtfbplus.client.api.Canvas;
import ie.sortons.gwtfbplus.client.newresources.Resources;
import ie.sortons.gwtfbplus.client.overlay.SignedRequest;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import com.gwtfb.sdk.FBCore;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * 
 */
public class Friends__Events implements EntryPoint {


	interface MyEventBinder extends EventBinder<Friends__Events> {}
	private static final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);
	
	// Courtesy of gwtfb.com
	private FBCore fbCore = GWT.create(FBCore.class);

	
	// public String APPID = "123069381111681"; // sortonsevents
	public String APPID = "251403644880972"; // sortonsdev
	
	private String requiredPermissions = "user_location,user_events,friends_events";

	private boolean status = true;
	private boolean xfbml = true;
	private boolean cookie = true;

	// TODO : Gin
	// "Create the object graph - a real application would use Gin"
    final SimpleEventBus eventBus = new SimpleEventBus();
    
    FlowPanel app = new FlowPanel();
    Image loadingImage;
    MenuPresenter menuPresenter;
    SimplePanel menuBar;
    SimplePanel mapPanel;
    FlowPanel listPanel;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		System.out.println("Entrypoint");
		
		@SuppressWarnings("unused")
		SignedRequest signedRequest = SignedRequest.parseSignedRequest();
		
		
		Resources.INSTANCE.css().ensureInjected(); 
		
		fbCore.init(APPID, status, cookie, xfbml);

		eventBinder.bindEventHandlers(this, eventBus);
			
	    // Add the inert heading
	    app.add(new AppHeading());
	    
	    // Add a loading screen until we figure out if the user is logged in.
	    loadingImage = new Image(Resources.INSTANCE.loadingAnimation());
	    loadingImage.getElement().getStyle().setDisplay(Display.BLOCK);
	    loadingImage.getElement().getStyle().setProperty("margin", "auto");
	    loadingImage.getElement().getStyle().setMarginTop(20, Unit.PX);
	    app.add(loadingImage);
	    
	    
	    // Create the menu presenter
	    menuPresenter = new MenuPresenter(eventBus);
	    menuBar = new SimplePanel();
	    menuPresenter.setView(menuBar);

	    
	    // Create the map presenter
	    mapPanel = new SimplePanel();
	    @SuppressWarnings("unused")
		MapPresenter mapPresenter = new MapPresenter(eventBus, mapPanel);
	   
	    // Create the events presenter
	    EventsListPresenter eventsListPanel = new EventsListPresenter(eventBus);
		listPanel = new FlowPanel();
		listPanel.getElement().getStyle().setMarginBottom(75, Unit.PX);
		eventsListPanel.setView(listPanel);
			    

		RootPanel.get("gwt").add(app);

		
		
		
	    // Tell the Facebook canvas the initial size
		Canvas.setSize(760,800);

		@SuppressWarnings("unused")
		Model dataController = new Model(eventBus);
		
		LoginController loginController = new LoginController(eventBus, requiredPermissions);
		
		// To begin, check if we're logged in
		// If we are, the EventsPresenter will show the events, if not 
		// the MenuPresenter will show an "Add app" button
		System.out.println("Logging in client");
		
		loginController.getLoginStatus();
					 
		
	}
	
	@EventHandler
	void notLoggedIn(NotLoggedInEvent event){
		System.out.println("NotLoggedInEvent seen by EntryPoint");
		app.remove(loadingImage);
		app.add(new PreLoginScreen(eventBus, requiredPermissions));
	}
	@EventHandler
	void permissionsMissing(PermissionsNotPresentEvent event){
		System.out.println("PermissionsNotPresentEvent seen by EntryPoint");
		app.remove(loadingImage);
		app.add(new PreLoginScreen(eventBus, requiredPermissions));
	}
	
	@EventHandler
	void loggedInWithPermissiosn(PermissionsPresentEvent event) {
		System.out.println("PermissionsPresentEvent seen by EntryPoint");
		
		// If we're logged in, add the panels
		app.remove(loadingImage);
	    app.add(menuBar);
	    app.add(mapPanel);
	    app.add(listPanel);
	}
	
	@EventHandler
	void listBeingUpdated(UpdatedListItemsEvent event) {
		System.out.println("UpdatedListItemsEvent seen by EntryPoint");
			
		// resize the window one last time
		Timer timer = new Timer() {
			public void run() {
				Canvas.setSize(760, app.getElement().getOffsetHeight()+50);
			}
		};
		// Execute the timer 2 seconds in the future
		timer.schedule(1000);
	}
	
	
}
