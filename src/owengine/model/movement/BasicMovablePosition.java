package owengine.model.movement;

import owengine.model.entity.Entity;
import owengine.model.util.direction.MoveDir;
import owengine.model.warp.WarpMap;

class BasicMovablePosition<T extends Entity> extends MovablePosition<T>
		implements MovementAction.Movable {

	private static final long serialVersionUID = -4215041581933790391L;

	public BasicMovablePosition(
			T entity, WarpMap<T> map, int x, int y, MoveDir dir, int moveSpeed) {
		super(entity, map, x, y, dir, moveSpeed);
	}

	@Override
	void forceActivateMovement(MoveDir dir) {
		this.dir = dir;
		if (!isNextToBlocked()) {
			action = MovementAction.createMoveAction(moveSpeed, this);
			moveThroughDoor();
		}
	}

	@Override
	public void finishMove() {
		translate(dir.getX(), dir.getY());
		warp();
		
	}

}
