package ie.sortons.friendsevents.client.appevents;

import ie.sortons.gwtfbplus.client.fql.FqlEvent;

import java.util.HashSet;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class UpdatedMapItemsEvent extends GenericEvent { 
	
	  private final HashSet<FqlEvent> events;

	  public UpdatedMapItemsEvent(HashSet<FqlEvent> events) {

		  this.events = events;
	
	  }
	  
	  public HashSet<FqlEvent> getEvents() {
	    return events;
	  }
		  
}

