package ie.sortons.friendsevents.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * 
 */
public class FriendsEvents implements EntryPoint {

	public void onModuleLoad() {

		GWT.log("entrypoint");

		SimpleEventBus eventBus = new SimpleEventBus();
		RpcService rpcService = new RpcService(eventBus);
		AppController appViewer = new AppController(rpcService, eventBus);
		appViewer.go(RootPanel.get("gwt"));
	}

}
