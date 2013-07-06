package ie.sortons.friendsevents.client.facebook.overlay;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Wrapper class
 * 
 */
public class Session extends JavaScriptObject {
    
    protected Session () { }
    public final native String getStatus() /*-{ return this.status; }-*/;
    public final native boolean hasSession() /*-{ if (this.session) return true; else return false; }-*/;
    public final native String getAccessToken() /*-{ return this.session.access_token; }-*/;
    public final native int getExpires() /*-{ return this.session.expires; }-*/;
    public final native String getSecret() /*-{ return this.session.secret; }-*/;
    public final native String getSessionKey() /*-{ return this.session.session_key; }-*/;
    public final native String getSig() /*-{ return this.session.sig; }-*/;
    public final native String getUid() /*-{ return this.session.uid; }-*/;
}
