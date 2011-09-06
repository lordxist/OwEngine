package owengine.model.map;

import java.awt.Point;

abstract class AbstractWarpTile {

	private Point targetPos;
	private EntityMap targetMap;

	AbstractWarpTile(Point targetPos, EntityMap targetMap) {
		this.targetPos = targetPos;
		this.targetMap = targetMap;
	}

	void warp(Entity e) {
		e.getMap().removeEntity(e);
		targetMap.addEntity(targetPos, e);
	}

}
