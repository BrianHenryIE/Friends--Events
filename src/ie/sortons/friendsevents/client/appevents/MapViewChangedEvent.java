package ie.sortons.friendsevents.client.appevents;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class MapViewChangedEvent extends GenericEvent { 

	private double north;
	private double south;
	private double east;
	private double west;

	public MapViewChangedEvent(double north, double south, double east, double west) {
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}

	public double getNorth() {
		return north;
	}

	public double getSouth() {
		return south;
	}

	public double getEast() {
		return east;
	}

	public double getWest() {
		return west;
	}

}

