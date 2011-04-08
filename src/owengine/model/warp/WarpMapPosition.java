package owengine.model.warp;

import java.awt.Point;

import owengine.model.entity.Entity;
import owengine.model.entity.MapPosition;
import owengine.model.util.direction.MoveDir;

public abstract class WarpMapPosition<T extends Entity<T>> extends MapPosition<T>  {

	private static final long serialVersionUID = -7627958841257895940L;

	protected WarpMap<T> warpMap;

	public WarpMapPosition(T entity, WarpMap<T> warpMap, int x, int y) {
		super(entity, warpMap, x, y);
		this.warpMap = warpMap;
	}

	public void setMap(WarpMap<T> warpMap) {
		super.setMap(warpMap);
		this.warpMap = warpMap;
	}

	protected void warp() {
		warpMap.warp(this);
	}

	protected void moveThroughDoor() {
		warpMap.moveThroughDoor(this);
	}

	public Point getPos() {
		return this;
	}

	public void setPos(Point pos) {
		setLocation(pos);
	}

	public abstract MoveDir getDir();

}
