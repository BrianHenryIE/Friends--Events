package ie.sortons.friendsevents.server.servlet;

import ie.sortons.gwtfbplus.server.LandingPageServlet;

@SuppressWarnings("serial")
public class LandingPage extends LandingPageServlet {

	private final static String GWTNOCACHE = "friendsevents/friendsevents.nocache.js";
	
	public LandingPage(){
		super(GWTNOCACHE, "123069381111681", "appppppssseeecccrreeett");
	}
	
}
