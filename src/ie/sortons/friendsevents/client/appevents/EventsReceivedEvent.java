package ie.sortons.friendsevents.client.appevents;

import ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent;

import java.util.TreeMap;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class EventsReceivedEvent extends GenericEvent {

	private final TreeMap<Long, FqlEvent> newEvents;

	public EventsReceivedEvent(TreeMap<Long, FqlEvent> newEvents) {
		this.newEvents = newEvents;
	}

	public TreeMap<Long, FqlEvent> getNewEvents() {
		return newEvents;
	}

}