package owengine.model.warp;

import java.awt.Point;

import owengine.model.entity.Entity;

public class WarpTile<T extends Entity<T>> {

	private Point targetPos;
	private WarpMap<T> targetMap;

	public WarpTile(Point targetPos, WarpMap<T> targetMap) {
		this.targetPos = targetPos;
		this.targetMap = targetMap;
	}

	public void warp(WarpMapPosition<T> warpable) {
		warpable.setPos(targetPos);
		warpable.setMap(targetMap);
	}

}
