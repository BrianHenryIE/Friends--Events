package ie.sortons.friendsevents.client;

import ie.sortons.friendsevents.client.appevents.DatesChangedEvent;
import ie.sortons.gwtfbplus.client.widgets.datepicker.DateResources;
import ie.sortons.gwtfbplus.client.widgets.datepicker.DayPickerSmallResources;
import ie.sortons.gwtfbplus.client.widgets.datepicker.DayPicker.DatePopup;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.web.bindery.event.shared.binder.EventBinder;


public class MenuPresenter {


	interface MyEventBinder extends EventBinder<MenuPresenter> {}
	private static final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

	@SuppressWarnings("unused")
	private HasWidgets view;

	TextBox fromDate;	
	DayPickerSmallResources dpSmallResources = GWT.create(DayPickerSmallResources.class);

	private TextBox toDate;
	
	private Date from;
	private Date to;
	
	MenuPresenter(final EventBus eventBus) {
		eventBinder.bindEventHandlers(this, eventBus);
		
		dpSmallResources.style().ensureInjected();
		
		DateResources.INSTANCE.dateboxCss().ensureInjected();
		
		fromDate = new TextBox();
		toDate = new TextBox();
		
		fromDate.setReadOnly(true);
		toDate.setReadOnly(true);
		
		from = new Date();
		fromDate.setText(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).format(from));
		to = new Date();
		CalendarUtil.addMonthsToDate(to, 2);
		toDate.setText(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).format(to));

		
		fromDate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e) {      
				DatePopup popup = new DatePopup(dpSmallResources.style().fbStyleDatePicker(), new ValueChangeHandler<Date>(){
					@Override
					public void onValueChange(ValueChangeEvent<Date> event) {
						// event.getValue()
						fromDate.setText(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).format(event.getValue()));
						from = event.getValue();
						
						eventBus.fireEvent(new DatesChangedEvent(from, to));
					}});
				popup.showRelativeTo(fromDate);
			}
		});
		
		toDate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e) {      
				DatePopup popup = new DatePopup(dpSmallResources.style().fbStyleDatePicker(), new ValueChangeHandler<Date>(){
					@Override
					public void onValueChange(ValueChangeEvent<Date> event) {
						toDate.setText(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).format(event.getValue()));
						to = event.getValue();
						eventBus.fireEvent(new DatesChangedEvent(from, to));

					}});
				popup.showRelativeTo(toDate);
			}
		});
	}


	void setView(HasWidgets view) {
		this.view = view;
		
		FlowPanel panel = new FlowPanel();
			
		panel.getElement().getStyle().setMarginBottom(15, Unit.PX);
		panel.getElement().getStyle().setTextAlign(TextAlign.RIGHT);
		panel.getElement().getStyle().setColor("gray");
		panel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		
		fromDate.getElement().getStyle().setMarginLeft(10, Unit.PX);
		toDate.getElement().getStyle().setMarginLeft(10, Unit.PX);
		
		fromDate.getElement().getStyle().setMarginRight(20, Unit.PX);
		
		panel.add(new InlineHTML("from:"));
		panel.add(fromDate);
		panel.add(new InlineHTML("to:"));
		panel.add(toDate);

		view.add(panel);
		
	}

	

}