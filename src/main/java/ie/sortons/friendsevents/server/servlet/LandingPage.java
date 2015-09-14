package ie.sortons.friendsevents.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import ie.sortons.gwtfbplus.shared.domain.SignedRequest;

@SuppressWarnings("serial")
public class LandingPage extends HttpServlet {

	String head = "<script src='//ecn.dev.virtualearth.net/mapcontrol/mapcontrol.ashx?v=7.0&amp;s=1' ></script>";
	String body = "";

	private String gwtEntryPoint = "../friendsevents/friendsevents.nocache.js";

	private String signedRequestData = "";
	private String style = "";

	SignedRequest signedRequest;

	private Gson gson = new Gson();

	// Inside Facebook, it will always be POST
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// encrypt and add the signed request cookies
		if (request.getParameter("signed_request") != null) {

			signedRequest = SignedRequest.parseSignedRequest(request.getParameter("signed_request"));

			signedRequestData = "  <script id=\"signedRequest\">\n" + "    var _sr_data = " + gson.toJson(signedRequest)
					+ "\n  </script>\n\n";

		}
		// This isn't needed/desirable outside the fb canvas
		style += " overflow: hidden;";

		doGet(request, response);

	}

	/*
	 * If it's a GET, i.e. outside the FB Canvas frame, page_id can be passed in for testing.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("Servlet execution... " + request.getMethod());

		if (request.getParameter("page_id") != null) {

			signedRequestData = "  <script id=\"signedRequest\">\n" + "    var _sr_data = {\"page\": { \"id\": \""
					+ request.getParameter("page_id") + "\", \"admin\": false}, \"user_id\": \"\" }\n  </script>\n\n";

		}

		PrintWriter out = response.getWriter();

		// Write out head
		out.print(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> \n"
						+ // As specified for Bing Maps API
						"<html xmlns=\"http://www.w3.org/1999/xhtml\"" + "style=\"" + style + " margin:0;\"" + "> \n\n"
						+ "<head> \n\n"
						+ "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/> \n\n"
						+ signedRequestData + // If available, print the Signed Request
						"  <meta name=\"gwt:property\" content=\"" + request.getLocale() + "\">\n\n"
						+ "  <script type=\"text/javascript\" language=\"javascript\" src=\"" + gwtEntryPoint
						+ "\"></script> \n\n" + "  <script src=\"//connect.facebook.net/en_US/all.js\"></script> \n\n" + // Facebook
																															// API
		head + "</head> \n\n");

		// Write out body
		out.print("<body" + style + "> \n\n" + "  <div id='fb-root'></div> \n\n" + // required for Facebook API
				"  <div id=\"gwt\"></div> \n\n" + // root of document for GWT
				body + "</body> \n\n");

		out.print("</html>");
		out.flush();

	}
}
