package owengine.model.map;

import java.awt.Point;

public class DoorTile extends AbstractWarpTile {

	public static final DoorTile NULL = new DoorTile(null, null, null) {
		@Override
		public void warp(Entity e, MoveDir dir) {}
	};

	private MoveDir dir;

	public DoorTile(Point targetPos, EntityMap targetMap, MoveDir dir) {
		super(targetPos, targetMap);
		this.dir = dir;
	}

	public void warp(Entity e, MoveDir dir) {
		if (this.dir == dir) {
			warp(e);
		}
	}

}
