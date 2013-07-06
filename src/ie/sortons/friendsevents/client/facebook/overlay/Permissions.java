package ie.sortons.friendsevents.client.facebook.overlay;

import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;



public class Permissions extends JavaScriptObject {
	
	protected Permissions() { }

	
	public final native String hasInstalled() /*-{ return (this.installed==1); }-*/;

	public final native boolean hasStatus_update() /*-{ return (this.status_update==1); }-*/;
    public final native boolean hasPublish_checkins() /*-{ return (this.publish_checkins==1); }-*/;
    public final native boolean hasPhoto_upload() /*-{ return (this.photo_upload==1); }-*/;
    public final native boolean hasVideo_upload() /*-{ return (this.video_upload==1); }-*/;
    public final native boolean hasSms() /*-{ return (this.sms==1); }-*/;
    public final native boolean hasOffline_access() /*-{ return (this.offline_access==1); }-*/;
    public final native boolean hasEmail() /*-{ return (this.email==1); }-*/;
  	public final native boolean hasCreate_event() /*-{ return (this.create_event==1); }-*/;
  	public final native boolean hasCreate_note() /*-{ return (this.create_note==1); }-*/;
  	public final native boolean hasExport_stream() /*-{ return (this.export_stream==1); }-*/;
  	public final native boolean hasShare_item() /*-{ return (this.share_item==1); }-*/;
  	public final native boolean hasRsvp_event() /*-{ return (this.rsvp_event==1); }-*/;
  	public final native boolean hasRead_stream() /*-{ return (this.read_stream ==1); }-*/;
  	public final native boolean hasPublish_stream() /*-{ return (this.publish_stream==1); }-*/;
  	public final native boolean hasRead_mailbox() /*-{ return (this.read_mailbox==1); }-*/;
  	public final native boolean hasAds_management() /*-{ return (this.ads_management==1); }-*/;
  	public final native boolean hasRead_friendlists() /*-{ return (this.read_friendlists==1); }-*/; 
  	public final native boolean hasManage_friendlists() /*-{ return (this.manage_friendlists==1); }-*/;
  	public final native boolean hasXmpp_login() /*-{ return (this.xmpp_login==1); }-*/;
  	public final native boolean hasRead_insights() /*-{ return (this.read_insights==1); }-*/;
  	public final native boolean hasRead_requests() /*-{ return (this.read_requests==1); }-*/;
  	public final native boolean hasManage_notifications() /*-{ return (this.manage_notifications==1); }-*/; 
  	public final native boolean hasManage_pages() /*-{ return (this.manage_pages==1); }-*/;
  	public final native Boolean hasUser_birthday() /*-{ return (this.user_birthday==1); }-*/;
  	public final native boolean hasUser_religion_politics() /*-{ return (this.user_religion_politics==1); }-*/;
  	public final native boolean hasUser_relationships() /*-{ return (this.user_relationships==1); }-*/;
  	public final native boolean hasUser_relationship_details() /*-{ return (this.user_relationship_details==1); }-*/; 
  	public final native boolean hasUser_hometown() /*-{ return (this.user_hometown==1); }-*/;
  	public final native boolean hasUser_location() /*-{ return (this.user_location==1); }-*/;
  	public final native boolean hasUser_likes() /*-{ return (this.user_likes==1); }-*/;
  	public final native boolean hasUser_activities() /*-{ return (this.user_activities==1); }-*/;
  	public final native boolean hasUser_interests() /*-{ return (this.user_interests==1); }-*/;
  	public final native boolean hasUser_education_history() /*-{ return (this.user_education_history==1); }-*/;
  	public final native boolean hasUser_work_history() /*-{ return (this.user_work_history==1); }-*/;
  	public final native boolean hasUser_online_presence() /*-{ return (this.user_online_presence==1); }-*/;
  	public final native boolean hasUser_website() /*-{ return (this.user_website==1); }-*/;
  	public final native boolean hasUser_groups() /*-{ return (this.user_groups==1); }-*/;
  	public final native boolean hasUser_events() /*-{ return (this.user_events==1); }-*/;
  	public final native boolean hasUser_photos() /*-{ return (this.user_photos==1); }-*/;
  	public final native boolean hasUser_videos() /*-{ return (this.user_videos==1); }-*/;
  	public final native boolean hasUser_photo_video_tags() /*-{ return (user_photo_video_tags==1); }-*/;
  	public final native boolean hasUser_notes() /*-{ return (this.user_notes==1); }-*/;
  	public final native boolean hasUser_location_posts() /*-{ return (this.user_location_posts==1); }-*/;
  	public final native boolean hasUser_checkins() /*-{ return (this.user_checkins==1); }-*/;
  	public final native boolean hasUser_questions() /*-{ return (this.user_questions==1); }-*/;
  	public final native boolean hasUser_about_me() /*-{ return (this.user_about_me==1); }-*/;
  	public final native boolean hasUser_status() /*-{ return (this.user_status==1); }-*/;
  	public final native boolean hasUser_games_activity() /*-{ return (this.user_games_activity==1); }-*/;
  	public final native boolean hasUser_subscriptions() /*-{ return (this.user_subscriptions==1); }-*/;
  	public final native boolean hasFriends_birthday() /*-{ return (this.friends_birthday==1); }-*/;
  	public final native boolean hasFriends_religion_politics() /*-{ return (this.friends_religion_politics==1); }-*/;
  	public final native boolean hasFriends_relationships() /*-{ return (this.friends_relationships==1); }-*/;
  	public final native boolean hasFriends_relationship_details() /*-{ return (this.friends_relationship_details==1); }-*/;
  	public final native boolean hasFriends_hometown() /*-{ return (this.friends_hometown==1); }-*/;
  	public final native boolean hasFriends_location() /*-{ return (this.friends_location==1); }-*/;
  	public final native boolean hasFriends_likes() /*-{ return (this.friends_likes==1); }-*/;
  	public final native boolean hasFriends_activities() /*-{ return (this.friends_activities==1); }-*/;
  	public final native boolean hasFriends_interests() /*-{ return (this.friends_interests==1); }-*/;
  	public final native boolean hasFriends_education_history() /*-{ return (this.friends_education_history==1); }-*/;
  	public final native boolean hasFriends_work_history() /*-{ return (this.friends_work_history==1); }-*/;
  	public final native boolean hasFriends_online_presence() /*-{ return (this.friends_online_presence==1); }-*/;
  	public final native boolean hasFriends_website() /*-{ return (this.friends_website==1); }-*/;
  	public final native boolean hasFriends_groups() /*-{ return (this.friends_groups==1); }-*/;
  	public final native boolean hasFriends_events() /*-{ return (this.friends_events==1); }-*/;
  	public final native boolean hasFriends_photos() /*-{ return (this.friends_photos==1); }-*/;
  	public final native boolean hasFriends_videos() /*-{ return (this.friends_videos==1); }-*/;
  	public final native boolean hasFriends_photo_video_tags() /*-{ return (this.friends_photo_video_tags==1); }-*/;
  	public final native boolean hasFriends_notes() /*-{ return (this.friends_notes==1); }-*/;
  	public final native boolean hasFriends_location_posts() /*-{ return (this.friends_location_posts==1); }-*/;
  	public final native boolean hasFriends_checkins() /*-{ return (this.friends_checkins==1); }-*/;
  	public final native boolean hasFriends_questions() /*-{ return (this.friends_questions==1); }-*/;
  	public final native boolean hasFriends_about_me() /*-{ return (this.friends_about_me==1); }-*/;
  	public final native boolean hasFriends_status() /*-{ return (this.friends_status==1); }-*/;
  	public final native boolean hasFriends_games_activity() /*-{ return (this.friends_games_activity==1); }-*/;
  	public final native boolean hasFriends_subscriptions() /*-{ return (this.friends_subscriptions==1); }-*/;
    	   
  	public final native boolean hasBookmarked() /*-{ return (this.bookmarked==1); }-*/;
  	
  	public final Set<String> list() {
  		JSONObject jo = new JSONObject(this);

  		Set<String> list = jo.keySet();
  		
  		// TODO:
  		// remove installed and bookmarked
  		/*
  		for(String li : list){
  			if((li.equals("installed"))||(li.equals("bookmarked"))){
  				list.remove(li);
  				list.
  			}
  		}
  		*/
  		
  		return list;
  	}

  	
	// TODO
	// There's got to be a better way than this JSONObject approach.
  	public final boolean hasPermission(String permission) {
  		// If it has the key we're looking for, check that it's "1"
  		JSONObject jo = new JSONObject(this);
  		return (jo.containsKey(permission) ? ( (jo.get(permission).toString().equals("1")) ? true : false) : false );
  	}

  	
	public final boolean hasPermissions(String requiredPermissions) {
	
		String[] permissionsList = requiredPermissions.split(","); 
		
		// If any of them aren't there, return false
		for(String permission : permissionsList){
			
			if(!hasPermission(permission.trim())){
				return false;
			}
		}
		
		return true;
	}
    
}