package ie.sortons.friendsevents.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {

	public static final Resources INSTANCE = GWT.create(Resources.class); 

	@Source("style.css")
	@CssResource.NotStrict
	CssResource css();


	@Source("map_pushpin.png")
	ImageResource mapPushPin();

}