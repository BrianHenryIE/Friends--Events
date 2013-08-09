package ie.sortons.friendsevents.client.appevents;

import java.util.Date;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class DatesChangedEvent extends GenericEvent { 
	
	  
	private Date from;
	private Date to;

	public DatesChangedEvent(Date from, Date to) {
		  
		  this.from = from;
		  this.to = to;
	  }

	  public Date getFrom() {
	    return from;
	  }

	  public Date getTo() {
	    return to;
	  }
		  
}

