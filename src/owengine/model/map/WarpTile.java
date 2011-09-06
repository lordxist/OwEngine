package owengine.model.map;

import java.awt.Point;

public class WarpTile extends AbstractWarpTile {

	public static final WarpTile NULL = new WarpTile(null, null) {
		@Override
		public void warp(Entity e) {}
	};

	public WarpTile(Point targetPos, EntityMap targetMap) {
		super(targetPos, targetMap);
	}

	@Override
	public void warp(Entity e) {
		super.warp(e);
	}

}
