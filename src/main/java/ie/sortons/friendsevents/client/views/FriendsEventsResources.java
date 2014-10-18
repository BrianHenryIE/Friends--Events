package ie.sortons.friendsevents.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface FriendsEventsResources extends ClientBundle {

	public interface Style extends CssResource {
		String map();

		String eventsList();

		String menuPanel();

		String appHeading();

		String listScrollHolder();
	}

	public static final FriendsEventsResources INSTANCE = GWT.create(FriendsEventsResources.class);

	@Source("friendsevents.css")
	Style css();

}
