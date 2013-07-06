package ie.sortons.friendsevents.client.facebook.overlay;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Wrapper class
 * 
 */
public class BasicInfo extends JavaScriptObject {
    
    protected BasicInfo () { }
    
    public final native String getId() /*-{ return this.id; }-*/;
    public final native String getName() /*-{ return this.name; }-*/;
    public final native String getFirstName() /*-{ return this.first_name; }-*/;
    public final native String getLastName() /*-{ return this.last_name; }-*/;
    public final native String getPhotoUrl() /*-{ return 'http://graph.facebook.com/' + this.id + '/picture'; }-*/;
}
