package ie.sortons.friendsevents.client;


import ie.sortons.friendsevents.client.facebook.Canvas;
import ie.sortons.friendsevents.client.gwtfb.FBCore;
import ie.sortons.friendsevents.client.resources.Resources;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * 
 */
public class Friends__Events implements EntryPoint {

	// Courtesy of gwtfb.com
	private FBCore fbCore = GWT.create(FBCore.class);

	
	public String APPID = "123069381111681"; // sortonsevents
	// public String APPID = "251403644880972"; // sortonsdev
	
	private String requiredPermissions = "user_location,friends_location,user_events,friends_events";

	private boolean status = true;
	private boolean xfbml = true;
	private boolean cookie = true;


	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		System.out.println("Entrypoint");
		
		Resources.INSTANCE.css().ensureInjected(); 
		
		fbCore.init(APPID, status, cookie, xfbml);


		// TODO : Gin
		// "Create the object graph - a real application would use Gin"
	    final SimpleEventBus eventBus = new SimpleEventBus();
	    
	    FlowPanel app = new FlowPanel();

	    // Create the menu presenter
	    MenuPresenter menuPresenter = new MenuPresenter(eventBus, requiredPermissions);
	    SimplePanel menuBar = new SimplePanel();
	    menuPresenter.setView(menuBar);
	    app.add(menuBar);
	    
	    // Create the map presenter
	    SimplePanel mapPanel = new SimplePanel();
	    MapPresenter mapPresenter = new MapPresenter(eventBus, mapPanel);
	    app.add(mapPanel);
	    
	    // Create the events presenter
	    EventsPresenter eventsListPanel = new EventsPresenter(eventBus);
		FlowPanel resultPanel = new FlowPanel();
		eventsListPanel.setView(resultPanel);
		app.add(resultPanel);
		
		RootPanel.get("gwt").add(app);

		app.getElement().getStyle().setMarginBottom(75, Unit.PX);
	    
	    // Tell the Facebook canvas the initial size
		Canvas.setSize(640,800);

		mapPresenter.go();
		
		LoginController loginController = new LoginController(eventBus, requiredPermissions);
		
		// To begin, check if we're logged in
		// If we are, the EventsPresenter will show the events, if not 
		// the MenuPresenter will show an "Add app" button
		System.out.println("Logging in client");
		
		loginController.getLoginStatus();
					 
		
	}

		
}
