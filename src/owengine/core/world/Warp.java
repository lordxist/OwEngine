package owengine.core.world;

import java.awt.Point;

public class Warp {

	private boolean doorWarp;
	private GameMap targetMap;
	private Point targetPos;

	public Warp(GameMap targetMap, Point targetPos) {
		this.targetMap = targetMap;
		this.targetPos = targetPos;
	}

	public Warp(GameMap targetMap, Point targetPos, boolean doorWarp) {
		this(targetMap, targetPos);
		this.doorWarp = doorWarp;
	}

	public void warp(Entity e) {
		e.deleteAction();
		targetMap.addEntity(e);
		e.position = targetPos;
		targetMap.enter();
		targetMap.startMapEvent();
	}

	public boolean isDoorWarp() {
		return doorWarp;
	}

}
