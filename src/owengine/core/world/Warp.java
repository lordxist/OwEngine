package owengine.core.world;

import java.awt.Point;

public class Warp {

	private GameMap targetMap;
	private Point targetPos;

	public Warp(GameMap targetMap, Point targetPos) {
		this.targetMap = targetMap;
		this.targetPos = targetPos;
	}

	public void warp(Entity e) {
		targetMap.addEntity(e);
		e.position = targetPos;
		targetMap.enter();
		targetMap.startMapEvent();
	}

}
