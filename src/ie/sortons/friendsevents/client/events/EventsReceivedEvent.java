package ie.sortons.friendsevents.client.events;

import ie.sortons.friendsevents.client.facebook.overlay.FqlEvent;

import com.google.gwt.core.client.JsArray;
import com.google.web.bindery.event.shared.binder.GenericEvent;

public class EventsReceivedEvent extends GenericEvent { 
	
	  private final JsArray<FqlEvent> events;

	  public EventsReceivedEvent(JsArray<FqlEvent> events) {

		  System.out.println("events received: " + events.length());
		  
		  this.events = events;
	
	  }
	  
	  public JsArray<FqlEvent> getEvents() {
	    return events;
	  }
		  
}

