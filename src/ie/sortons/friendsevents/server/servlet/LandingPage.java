package ie.sortons.friendsevents.server.servlet;

import ie.sortons.gwtfbplus.server.LandingPageServlet;

@SuppressWarnings("serial")
public class LandingPage extends LandingPageServlet {

	private final static String GWTNOCACHE = "friends__events/friends__events.nocache.js";
	
	public LandingPage(){
		super(GWTNOCACHE);
	}
	
}
