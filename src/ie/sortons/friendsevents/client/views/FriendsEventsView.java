package ie.sortons.friendsevents.client.views;

import ie.brianhenry.gwtbingmaps.client.api.LocationRect;
import ie.sortons.friendsevents.client.presenter.FriendsEventsPresenter;
import ie.sortons.friendsevents.client.widgets.EventWidget;
import ie.sortons.friendsevents.client.widgets.EventsMap;
import ie.sortons.gwtfbplus.client.overlay.fql.FqlUser;
import ie.sortons.gwtfbplus.client.widgets.datepicker.DatePickerResources;
import ie.sortons.gwtfbplus.client.widgets.datepicker.FbDateBox;
import ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class FriendsEventsView extends Composite implements FriendsEventsPresenter.Display, HasValueChangeHandlers<LocationRect> {

	FlowPanel panel = new FlowPanel();

	FriendsEventsPresenter presenter;

	// TODO
	// Can I just put value change handlers on these??
	PriorityQueue<FqlEvent> eventsForList;
	HashSet<FqlEvent> eventsForMap;

	FlowPanel menuPanel = new FlowPanel();
	EventsMap eventsMap = new EventsMap();
	ScrollPanel listScroll = new ScrollPanel();
	FlowPanel list = new FlowPanel();

	DatePickerResources dpResources = DatePickerResources.INSTANCE;

	FbDateBox toDate = new FbDateBox();
	FbDateBox fromDate = new FbDateBox();

	public FriendsEventsView() {

		initWidget(panel);

		FriendsEventsResources resources = FriendsEventsResources.INSTANCE;

		panel.add(menuPanel);

		panel.add(eventsMap);

		SimplePanel listScrollHolder = new SimplePanel();
		listScrollHolder.setStyleName(resources.css().listScrollHolder());

		listScrollHolder.add(listScroll);
		listScroll.add(list);

		panel.add(listScrollHolder);

		// Do I have to do that here? Shouldn't it be done in the datepicker class? Maybe it's just the style
		dpResources.css().ensureInjected();

		resources.css().ensureInjected();

		menuPanel.setStyleName(resources.css().menuPanel());
		eventsMap.setStyleName(resources.css().map());
		listScroll.setStyleName(resources.css().eventsList());

		fromDate.getElement().getStyle().setMarginLeft(10, Unit.PX);
		toDate.getElement().getStyle().setMarginLeft(10, Unit.PX);

		fromDate.getElement().getStyle().setMarginRight(20, Unit.PX);

		menuPanel.add(new InlineHTML("from:"));
		menuPanel.add(fromDate);
		menuPanel.add(new InlineHTML("to:"));
		menuPanel.add(toDate);

		fromDate.setValue(new Date());

		/*
		 * fromDate.getDatePicker().addValueChangeHandler(new ValueChangeHandler<Date>() {
		 * 
		 * @Override public void onValueChange(ValueChangeEvent<Date> event) {
		 * presenter.updateFromDate(event.getValue()); } });
		 * 
		 * toDate.getDatePicker().addValueChangeHandler(new ValueChangeHandler<Date>() {
		 * 
		 * @Override public void onValueChange(ValueChangeEvent<Date> event) { presenter.updateToDate(event.getValue());
		 * } });
		 */

	}

	@Override
	public FbDateBox getStartDateBox() {
		return fromDate;
	}

	@Override
	public FbDateBox getEndDateBox() {
		return toDate;
	}

	@Override
	public void setEventsForList(PriorityQueue<FqlEvent> eventsForList) {
		this.eventsForList = eventsForList;
	}

	@Override
	public void setEventsForMap(PriorityQueue<FqlEvent> mappableEventsBetweenDates) {
		eventsMap.setItemList(mappableEventsBetweenDates);
	}

	@Override
	public void eventsListUpdated() {
		
		// TODO this should maybe be done with some celllist?
		list.clear();

		while (!eventsForList.isEmpty()) {
			FqlEvent nextEvent = eventsForList.remove();
			if (eventWidgets.containsKey(nextEvent.getEid()))
				list.add(eventWidgets.get(nextEvent.getEid()));
			else {
				EventWidget eventWidget = new EventWidget(nextEvent);
				eventWidgets.put(nextEvent.getEid(), eventWidget);
				list.add(eventWidget);
			}
		}
	}

	private HashMap<Long, EventWidget> eventWidgets = new HashMap<Long, EventWidget>();

	@Override
	public void mapListUpdated() {
		eventsMap.updateMapPins();
	}

	@Override
	public void setPresenter(FriendsEventsPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<LocationRect> handler) {
		return eventsMap.addValueChangeHandler(handler);
	}

	@Override
	public void setUserLocation(FqlUser user) {
		eventsMap.setUserLocation(user);
	}

	@Override
	public LocationRect getLocationRect() {
		return eventsMap.getMap().getBounds();
	}

}