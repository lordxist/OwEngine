package owengine.model.warp;

import java.awt.Point;

import owengine.model.entity.Entity;
import owengine.model.util.direction.MoveDir;

public class DoorTile<T extends Entity<T>> extends WarpTile<T> {

	private MoveDir dir;

	public DoorTile(Point targetPos, WarpMap<T> targetMap, MoveDir dir) {
		super(targetPos, targetMap);
		this.dir = dir;
	}

	@Override
	public void warp(WarpMapPosition<T> w) {
		if (w.getDir() == dir) super.warp(w);
	}

}
