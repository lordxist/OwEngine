package owengine.model.movement;

import owengine.model.entity.Entity;
import owengine.model.util.direction.MoveDir;
import owengine.model.warp.WarpMap;

class TurningMovablePosition<T extends Entity> extends BasicMovablePosition<T> {

	private static final long serialVersionUID = 4079563121521830727L;

	public TurningMovablePosition(T entity, WarpMap<T> map, int x, int y,
			MoveDir dir, int moveSpeed) {
		super(entity, map, x, y, dir, moveSpeed);
	}

	@Override
	void forceActivateMovement(MoveDir dir) {
		if (getDir() != dir) {
			this.dir = dir;
			action = MovementAction.createTurnAction(moveSpeed);
		}
		else if (isNextToBlocked())
			action = MovementAction.createBumpAction(moveSpeed);
		else super.forceActivateMovement(dir);
	}

}
