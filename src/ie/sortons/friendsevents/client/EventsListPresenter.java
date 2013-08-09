package ie.sortons.friendsevents.client;

import ie.sortons.friendsevents.client.appevents.UpdatedListItemsEvent;
import ie.sortons.friendsevents.client.widgets.EventWidget;
import ie.sortons.gwtfbplus.client.fql.FqlEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

/**
 * Presenter for the main events screen, which shows a list of Facebook events once
 * they've been loaded from Facebook.
 */
class EventsListPresenter {


	interface MyEventBinder extends EventBinder<EventsListPresenter> {
	}

	private static final MyEventBinder eventBinder = GWT
			.create(MyEventBinder.class);

	private HasWidgets view;


	EventsListPresenter(EventBus eventBus) {
		eventBinder.bindEventHandlers(this, eventBus);
	}

	void setView(HasWidgets view) {
		this.view = view;
	}
  
	
	@EventHandler
	void eventsReceived(UpdatedListItemsEvent event) {
		System.out.println("UpdatedListItemsEvent seen by EventsPresenter: " + event.getEvents().size());
		
		// TODO
		// insert and remove individually
		view.clear();
		
		for (FqlEvent nextEvent : event.getEvents()) {

			view.add(new EventWidget(nextEvent));
			
		}
	}
}