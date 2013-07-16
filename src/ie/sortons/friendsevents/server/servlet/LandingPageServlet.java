package ie.sortons.friendsevents.server.servlet;



import ie.sortons.friendsevents.server.facebook.gson.SignedRequest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LandingPageServlet extends HttpServlet {
	

	private PrintWriter out;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		System.out.println("Servlet execution... GET");
	            

		String mapsUrl = "http://ecn.dev.virtualearth.net/mapcontrol/mapcontrol.ashx?v=7.0";

		if(request.getHeader("referer")!=null){
			String referrer = request.getHeader("referer"); 
			System.out.println("Referrer: " + referrer);
			if(referrer.contains("https")){
				mapsUrl = "https://ecn.dev.virtualearth.net/mapcontrol/mapcontrol.ashx?v=7.0&s=1";
			}
		}
		out = response.getWriter();
		
		
		// Write out Head 
		out.print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n" + // As specified for Bing Maps API
				"<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"overflow: hidden\"> \n\n" +
				"<head> \n\n" +
				"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/> \n\n" +
				"  <script charset=\"UTF-8\" type=\"text/javascript\" src=\""+mapsUrl+"\"></script> \n\n" + // Bing Maps API
				"  <script type=\"text/javascript\" language=\"javascript\" src=\"../friends__events/friends__events.nocache.js\"></script> \n\n" + // Check the GWT cache and fetch the correct JS
				"  <script src=\"//connect.facebook.net/en_US/all.js\"></script> \n\n" + // Facebook API
				"</head> \n\n");
		
		// "  <script charset=\"UTF-8\" type=\"text/javascript\" src=\"//ecn.dev.virtualearth.net/mapcontrol/mapcontrol.ashx?v=7.0\"></script> \n\n" + // Bing Maps API
		
		// TODO
		// Check the Bing Maps SSL setup. Supposedly required &s=1 at the end of the URL
		// http://msdn.microsoft.com/en-us/library/gg427624.aspx
		
		// Write out Body
		out.print("<body style=\"overflow=hidden;\"> \n\n" +
				"  <div id='fb-root'></div> \n\n" + // required for Facebook API
				"  <div id=\"gwt\"></div> \n\n" + // root of document for GWT
				"</body> \n");
		
		out.print("</html>");
		out.flush();
	}
	
	
	
	// Inside Facebook, it will always be POST
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		System.out.println("Servlet execution... POST");
	
		
		SignedRequest signedrequest = SignedRequest.parseSignedRequest(request.getParameter("signed_request"));
		
		System.out.println("gson'd signed_request: " + signedrequest.toJsonString());
		
		
		String mapsUrl = "http://ecn.dev.virtualearth.net/mapcontrol/mapcontrol.ashx?v=7.0";

		if(request.getHeader("referer")!=null){
			String referrer = request.getHeader("referer"); 
			System.out.println("Referrer: " + referrer);
			if(referrer.contains("https")){
				mapsUrl = "https://ecn.dev.virtualearth.net/mapcontrol/mapcontrol.ashx?v=7.0&s=1";
			}
		}
		
		out = response.getWriter();
		
		// Write out Head 
		out.print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n" + // As specified for Bing Maps API
				"<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"overflow: hidden\"> \n\n" +
				"<head> \n\n" +
				"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/> \n\n" +
				"  <script charset=\"UTF-8\" type=\"text/javascript\" src=\""+mapsUrl+"\"></script> \n\n" + // Bing Maps API
				"  <script type=\"text/javascript\" language=\"javascript\" src=\"../friends__events/friends__events.nocache.js\"></script> \n\n" + // Check the GWT cache and fetch the correct JS
				"  <script src=\"//connect.facebook.net/en_US/all.js\"></script> \n\n" + // Facebook API
				"</head> \n\n");
				
		// TODO
		// Check the Bing Maps SSL setup. Supposedly required &s=1 at the end of the URL
		// http://msdn.microsoft.com/en-us/library/gg427624.aspx
		
		// Write out Body
		out.print("<body style=\"overflow=hidden;\"> \n\n" +
				"  <div id='fb-root'></div> \n\n" + // required for Facebook API
				"  <div id=\"gwt\"></div> \n\n" + // root of document for GWT
				"</body> \n");
		
		out.print("</html>");
		
		out.flush();
	}

}
		