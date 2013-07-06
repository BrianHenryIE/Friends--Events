package ie.sortons.friendsevents.client.facebook;

/*
	* Canvas Methods

    * FB.Canvas.Prefetcher.addStaticResource
    * FB.Canvas.Prefetcher.setCollectionMode
    * FB.Canvas.hideFlashElement
    * FB.Canvas.scrollTo
    * FB.Canvas.setAutoGrow
    * FB.Canvas.setDoneLoading
    * FB.Canvas.setSize
    * FB.Canvas.setUrlHandler
    * FB.Canvas.showFlashElement
    * FB.Canvas.startTimer
    * FB.Canvas.stopTimer
    * 
 */

/**
 * Methods for managing the Facebook canvas
 * 
 * @author brianhenry
 *
 */
public class Canvas {


	/**
	 * Call this whenever you need a resize. This usually means, once after pageload, and whenever your content size changes. 
	 * 
	 * This method will attempt to determine the size of the Canvas app content and set the height automatically. Note: when determining the Canvas Height automatically the height it can only be increased, not decreased. When possible please pass the explicit dimensions as properties of an object.
	 * 
	 * @see http://developers.facebook.com/docs/reference/javascript/FB.Canvas.setSize/
	 */
	public native static void setSize () /*-{	$wnd.FB.Canvas.setSize();	}-*/;

	/**
	 * Call this whenever you need a resize. This usually means, once after pageload, and whenever your content size changes. When possible please pass the explicit dimensions as properties of an object. 
	 *  
	 * @see http://developers.facebook.com/docs/reference/javascript/FB.Canvas.setSize/
	 */	
	public native static void setSize(int width, int height) /*-{	$wnd.FB.Canvas.setSize({ 'width': width, 'height': height });	}-*/;
 
	
	
}
