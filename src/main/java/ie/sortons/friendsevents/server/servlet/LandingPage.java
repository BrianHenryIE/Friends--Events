package ie.sortons.friendsevents.server.servlet;

import ie.sortons.gwtfbplus.server.LandingPageServlet;

@SuppressWarnings("serial")
public class LandingPage extends LandingPageServlet {

	private final static String GWTNOCACHE = "../friendsevents/friendsevents.nocache.js";
	
	public LandingPage(){
		super(GWTNOCACHE, "123069381111681", "appppppssseeecccrreeett");
		
		head ="<script src='//ecn.dev.virtualearth.net/mapcontrol/mapcontrol.ashx?v=7.0&amp;s=1' ></script>";
		body = "";
		
	}
	
}
