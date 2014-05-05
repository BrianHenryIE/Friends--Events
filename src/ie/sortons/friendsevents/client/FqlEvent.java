package ie.sortons.friendsevents.client;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Wrapper class
 * 
 * Updated 30/09/2012
 * 
 * @see http://developers.facebook.com/docs/reference/fql/event/
 *
 */
public class FqlEvent extends JavaScriptObject {
    
    protected FqlEvent () { }
    
    /**
     * @return The ID of the event being queried.
     */
    public final native String getEid() /*-{ return this.eid; }-*/;
    
    /**
     * @return The name of the event being queried.
     */
    public final native String getName() /*-{ return this.name; }-*/;

    /*
   	pic_small	string	
	The URL to the small-sized profile picture for the event being queried. The image can have a maximum width of 50px and a maximum height of 150px. This URL may be blank.
	
	pic_big	string	
	The URL to the largest-sized profile picture for the event being queried. The image can have a maximum width of 200px and a maximum height of 600px. This URL may be blank.
	*/
    
    /**
     * @return The URL to the square-sized profile picture for the event being queried. The image is returned with a width and height of 50px.
     */
    public final native String getPic_square() /*-{ return this.pic_square; }-*/;


	/*
	pic	string	
	The URL to the medium-sized profile picture for the event being queried. The image can have a maximum width of 100px and a maximum height of 300px. This URL may be blank.
	
	host	string	
	The name of the host of the event being queried.
     */
        
    /**
     * @return The description of the event being queried.
     */
    public final native String getDescription() /*-{ return this.description; }-*/;
    
    
    /**
     * @return An ISO-8601 string representing the starting date and (optionally) time of the event being queried. Note - before the 'Events Timezone' migration, this field was a timestamp. See 'Events Timezone Migration Note' above for more information.
     */
    public final native String getStartTime() /*-{ return this.start_time;  }-*/;
    public final Date getStartTimeDate() { return (this.getStartTime().length()>10 ? DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ssZ").parse(getStartTime()) : DateTimeFormat.getFormat("yyyy-MM-dd").parse(getStartTime()) ); }
    /**
     * @return The start time formatted e.g. Thursday, 11 September, 2014, at 14:00
     */
    public final String getStartTimeString() { return DateTimeFormat.getFormat("EEEE, dd MMMM, yyyy, 'at' k:mm").format(getStartTimeDate()); }    
    /**
     * @return The start time's day, i.e. at 00:00:00
     */
    @SuppressWarnings("deprecation")
	public final Date getStartTimeDayDate() { 
    	Date start = getStartTimeDate(); 
    	start.setHours(0);
    	start.setMinutes(0);
    	start.setSeconds(0);    	
    	return start; 
    	}
       
    /**
     * @return An ISO-8601 string representing the ending date and time of the event being queried. This can be empty if no end time has been specified. Note - before the 'Events Timezone' migration, this field was a timestamp. See 'Events Timezone Migration Note' above for more information.
     */
    public final native String getEndTime() /*-{ return this.end_time;  }-*/;
    // TODO: I don't think this works for null
    public final Date getEndTimeDate() { return ((getEndTime()==null) ? null : DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ssZ").parse(getEndTime())); }
    
    /*
		creator	int	
		The user ID of the creator of the event being queried.
		update_time	time	
		The time that the event being queried was last updated.
     */

    /**
     * @return The location of the event being queried.
     */
    public final native String getLocation() /*-{ return this.location; }-*/;

    /**
     * 
     * @return The venue where the event being queried is being held. Contains one or more of the following fields: street, city, state, zip, country, latitude, and longitude.
     */
    public final native Venue getVenue() /*-{ return this.venue; }-*/;
    
    /**
     * 
     * @return The privacy setting of the event being queried, indicating whether the event is OPEN, CLOSED, or SECRET.
     */
    public final native String getPrivacy() /*-{ return this.privacy; }-*/;
    	    
	/*
		hide_guest_list	bool	
		Indicates whether the guest list on the event's page is hidden..
		
		can_invite_friends	bool	
		Indicates whether or not the viewer can invite friends to the event.
		
		all_members_count	int	
		The number of invitees to the event.
		
		attending_count	int	
		The number of invitees who have accepted the event invite.
		
		unsure_count	int	
		The number of invitees who have responded maybe to the event invite.
		
		declined_count	int	
		The number of invitees who have declined the event invite.
		
		not_replied_count	int	
		The number of invitees who have not yet replied to the event invite.
	 */
    
    
    public static class Venue extends JavaScriptObject {

		 protected Venue() { }

		 public final native String getStreet() /*-{ return this.street; }-*/;
		 public final native String getCity() /*-{ return this.city; }-*/;
		 public final native String getState() /*-{ return this.state; }-*/;
		 public final native String getZip() /*-{ return this.zip; }-*/;
		 public final native String getCountry() /*-{ return this.country; }-*/;
		 public final native String getLatitude() /*-{ return this.latitude; }-*/;
		 public final native String getLongitude() /*-{ return this.longitude; }-*/;
		 public final native String getId() /*-{ return this.id; }-*/;

	}
    
}