package ie.sortons.friendsevents.client.widgets;

import ie.brianhenry.gwtbingmaps.client.BingMap;
import ie.brianhenry.gwtbingmaps.client.api.EventResponse;
import ie.brianhenry.gwtbingmaps.client.api.Events;
import ie.brianhenry.gwtbingmaps.client.api.Infobox;
import ie.brianhenry.gwtbingmaps.client.api.InfoboxOptions;
import ie.brianhenry.gwtbingmaps.client.api.Location;
import ie.brianhenry.gwtbingmaps.client.api.LocationRect;
import ie.brianhenry.gwtbingmaps.client.api.Map;
import ie.brianhenry.gwtbingmaps.client.api.MapOptions;
import ie.brianhenry.gwtbingmaps.client.api.Point;
import ie.brianhenry.gwtbingmaps.client.api.Pushpin;
import ie.brianhenry.gwtbingmaps.client.api.PushpinOptions;
import ie.brianhenry.gwtbingmaps.client.api.ViewOptions;
import ie.sortons.gwtfbplus.client.overlay.fql.FqlUser;
import ie.sortons.gwtfbplus.client.resources.map.MapResources;
import ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent;

import java.util.HashMap;
import java.util.PriorityQueue;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

// This can probably just extend BingMap
public class EventsMap extends Composite implements HasValueChangeHandlers<LocationRect> {

	private final String credentials = "ApmYYZr2urnVJhMJMOaMgjhH7lAISlyMpSEkIs6cqxYMwg85epCC6c1ZXgWIWFao";

	private FlowPanel mapView = new FlowPanel();

	private BingMap mapDiv;

	int centerXOffset = 270;

	private PriorityQueue<FqlEvent> events;
	
	MapResources resources = MapResources.INSTANCE;

	public EventsMap() {

		resources.css().ensureInjected();

		boolean enableSearchLogo = false;
		boolean showDashboard = false;
		boolean showMapTypeSelector = false;
		boolean showScalebar = false;
		boolean useInertia = false;

		MapOptions mapOptions = MapOptions.getMapOptions(credentials, null, null, null, null, null, null, null, null, null, null, null,
				enableSearchLogo, null, null, null, null, showDashboard, showMapTypeSelector, showScalebar, null, useInertia);

		// TODO
		// Figure out what zoomed out is/should be for people who don't have their current location set
		Location center = Location.newLocation(35.906849, -118.937988);

		String mapTypeId = "fb";
		double zoom = 12;

		ViewOptions viewOptions = ViewOptions.newViewOptions(null, center, null, null, mapTypeId, null, zoom);

		// Set up the blank map
		mapDiv = new BingMap("eventsMap", mapOptions, viewOptions);

		mapDiv.setSize("100%", "100%");

		FlowPanel controls = new FlowPanel();

		controls.setStyleName(resources.css().mapZoomControls());

		final Image zoomIn = new Image(resources.zoomInButton());
		final Image zoomOut = new Image(resources.zoomOutButton());

		zoomIn.setStyleName(resources.css().mapZoomButton());
		zoomOut.setStyleName(resources.css().mapZoomButton());

		zoomIn.addStyleName(resources.css().mapZoomButtonUp());
		zoomOut.addStyleName(resources.css().mapZoomButtonUp());

		zoomIn.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				zoomIn.removeStyleName(resources.css().mapZoomButtonUp());
				zoomIn.addStyleName(resources.css().mapZoomButtonDown());
			}
		});
		zoomOut.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				zoomOut.removeStyleName(resources.css().mapZoomButtonUp());
				zoomOut.addStyleName(resources.css().mapZoomButtonDown());
			}
		});

		zoomIn.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				zoomIn.addStyleName(resources.css().mapZoomButtonUp());
				zoomIn.removeStyleName(resources.css().mapZoomButtonDown());
			}
		});
		zoomOut.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				zoomOut.addStyleName(resources.css().mapZoomButtonUp());
				zoomOut.removeStyleName(resources.css().mapZoomButtonDown());
			}
		});

		zoomIn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Location newCentre = mapDiv.getMap().getCenter();
				Point zoomInOffset = Point.getPoint(centerXOffset, 0);
				mapDiv.getMap().setView(ViewOptions.newViewOptions(null, newCentre, zoomInOffset, null, null, null, mapDiv.getMap().getZoom() + 1));
			}
		});

		zoomOut.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Location newCentre = mapDiv.getMap().getCenter();
				Point zoomOutOffset = Point.getPoint((centerXOffset / 2) * -1, 0);
				mapDiv.getMap().setView(ViewOptions.newViewOptions(null, newCentre, zoomOutOffset, null, null, null, mapDiv.getMap().getZoom() - 1));
			}
		});

		controls.add(zoomIn);
		controls.add(zoomOut);

		SimplePanel leftOverMap = new SimplePanel();
		SimplePanel rightOverMap = new SimplePanel();
		SimplePanel bottomOverMap = new SimplePanel();

		leftOverMap.setStyleName(resources.css().leftOverMap());
		rightOverMap.setStyleName(resources.css().rightOverMap());
		bottomOverMap.setStyleName(resources.css().bottomOverMap());

		mapView.add(leftOverMap);
		mapView.add(rightOverMap);
		mapView.add(bottomOverMap);

		mapView.add(controls);

		initWidget(mapView);

		calculateOffset.run();

		mapView.add(mapDiv);

	}

	public void setUserLocation(FqlUser user) {
		Location userLocation = Location.newLocation(user.getCurrent_location().getLatitude(), user.getCurrent_location().getLongitude());
		Point locationOffset = Point.getPoint(centerXOffset * -1, 0);
		mapDiv.getMap().setView(ViewOptions.newViewOptions(null, userLocation, locationOffset, null, null, null, 12.0));
		ValueChangeEvent.fire(this, mapDiv.getMap().getBounds());
	}

	public void setItemList(PriorityQueue<FqlEvent> mappableEventsBetweenDates) {
		this.events = mappableEventsBetweenDates;
	}

	public void updateMapPins() {

		// TODO Not this! Count how many are there and add the new ones.. this way (was) taking ages to make each
		// widget.
		mapDiv.getMap().Entities().clear();

		// TODO
		// Add a timer to delay each pindrop
		for (FqlEvent nextEvent : events) {

			Location location = Location.newLocation(nextEvent.getVenue().getLatitude(), nextEvent.getVenue().getLongitude());

			Infobox itemInfobox = null;

			if (infoboxCache.containsKey(nextEvent.getEid()))
				itemInfobox = infoboxCache.get(nextEvent.getEid());
			else {
				MapEventWidget item = new MapEventWidget(nextEvent);
				InfoboxOptions infoboxOptions = InfoboxOptions.getInfoboxOptions(400, 100, null, true, 0, true, false, null, null, item.getElement()
						.getInnerHTML());
				itemInfobox = Infobox.getInfobox(location, infoboxOptions);
				item = null;
			}

			PushpinOptions options = PushpinOptions
					.setPushPinOptions(25, 28, resources.mapPushPin().getSafeUri().asString(), false, null, null, null);
			Pushpin pushpin = Pushpin.getPushpin(location, options);

			mapDiv.addPinWithClickInfobox(pushpin, itemInfobox);

		}

	}

	private HashMap<Long, Infobox> infoboxCache = new HashMap<Long, Infobox>();

	/*
	 * LocationRect changes when a user pans or zooms the map.
	 */
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<LocationRect> handler) {

		// TODO
		// Events.addHandler won't work until the map is attached/initialized

		Events.addHandler(mapDiv.getMap(), "viewchangeend", new AsyncCallback<JavaScriptObject>() {
			public void onSuccess(JavaScriptObject response) {
				ValueChangeEvent.fire(EventsMap.this, mapDiv.getMap().getBounds());
			}

			public void onFailure(Throwable caught) {
				// TODO
			}
		});

		return addHandler(handler, ValueChangeEvent.getType());
	}

	public void overRideDoubleClickZoom() {
		Events.addHandlerOverride(mapDiv.getMap(), "dblclick", new AsyncCallback<EventResponse>() {
			public void onSuccess(EventResponse response) {
				Point offset = Point.getPoint((-2 * (response.getX() - (mapDiv.getOffsetWidth() / 2))) - (centerXOffset), -2
						* (response.getY() - (mapDiv.getOffsetHeight() / 2)));
				mapDiv.getMap().setView(
						ViewOptions.newViewOptions(null, mapDiv.getMap().getCenter(), offset, null, null, null, mapDiv.getMap().getZoom() + 1));
			}

			public void onFailure(Throwable caught) {
				// TODO
			}
		});

	}

	@Override
	protected void onLoad() {
		super.onLoad();
		overRideDoubleClickZoom();
	}

	public Map getMap() {
		return mapDiv.getMap();
	}

	Timer calculateOffset = new Timer() {

		@Override
		public void run() {
			centerXOffset = -1
					* ((Math.min(502, mapDiv.getOffsetWidth() / 2) + ((mapDiv.getOffsetWidth() - Math.min(502, mapDiv.getOffsetWidth() / 2)) / 2)) - (mapDiv
							.getOffsetWidth() / 2));

			// TODO How much is too much?
			calculateOffset.schedule(1000);
		}
	};
}
