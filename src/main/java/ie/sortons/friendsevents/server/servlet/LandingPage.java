package ie.sortons.friendsevents.server.servlet;

import ie.sortons.gwtfbplus.server.LandingPageServlet;

@SuppressWarnings("serial")
public class LandingPage extends LandingPageServlet {

	private final static String GWTNOCACHE = "../friendsevents/friendsevents.nocache.js";
	
	// TODO: scriptinject from the bing maps project
	String head ="			<script src='//ecn.dev.virtualearth.net/mapcontrol/mapcontrol.ashx?v=7.0&amp;s=1' />";
		
	public LandingPage(){
		super(GWTNOCACHE, "123069381111681", "appppppssseeecccrreeett");
	}
	
}
