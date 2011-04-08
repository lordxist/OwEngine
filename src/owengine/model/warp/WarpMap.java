package owengine.model.warp;

import java.awt.Point;

import owengine.model.entity.Entity;
import owengine.model.entity.EntityMap;
import owengine.model.util.NullObjectHashMap;

public class WarpMap<T extends Entity<T>> extends EntityMap<T> {

	private WarpTile<T> nullWarpTile = new WarpTile<T>(null, null) {
		@Override public void warp(WarpMapPosition<T> w) {}
	};
	private NullObjectHashMap<Point, WarpTile<T>>
		warpLayer = new NullObjectHashMap<Point, WarpTile<T>>(nullWarpTile),
		doorLayer = new NullObjectHashMap<Point, WarpTile<T>>(nullWarpTile);

	public void putWarpTile(Point p, WarpTile<T> w) {
		warpLayer.put(p, w);
	}

	public void putDoorTile(Point p, DoorTile<T> d) {
		doorLayer.put(p, d);
	}

	public void warp(WarpMapPosition<T> w) {
		warpLayer.get(w.getPos()).warp(w);
	}

	public void moveThroughDoor(WarpMapPosition<T> w) {
		doorLayer.get(w.getPos()).warp(w);
	}

}
