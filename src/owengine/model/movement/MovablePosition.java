package owengine.model.movement;

import java.awt.Point;

import owengine.model.entity.Entity;
import owengine.model.util.action.Action;
import owengine.model.util.action.ActionUser;
import owengine.model.util.direction.MoveDir;
import owengine.model.warp.WarpMap;
import owengine.model.warp.WarpMapPosition;

public abstract class MovablePosition<T extends Entity> extends WarpMapPosition<T>
	implements ActionUser {

	protected MoveDir dir;
	protected int moveSpeed;
	protected MovementAction action = MovementAction.NULL;

	public static <T extends Entity> MovablePosition<T> createBasic(
			T entity, WarpMap<T> map, int x, int y, MoveDir dir, int moveSpeed) {
		return new BasicMovablePosition<T>(entity, map, x, y, dir, moveSpeed);
	}

	public static <T extends Entity> MovablePosition<T> createTurning(
			T entity, WarpMap<T> map, int x, int y, MoveDir dir, int moveSpeed) {
		return new TurningMovablePosition<T>(entity, map, x, y, dir, moveSpeed);
	}

	MovablePosition(
			T entity, WarpMap<T> map ,int x, int y, MoveDir dir, int moveSpeed) {
		super(entity, map, x, y);
		this.dir = dir;
		this.moveSpeed = moveSpeed;
	}

	public void activateMovement(MoveDir dir) {
		if (!action.isFinished()) return;
		else {
			//transfer(dir);
			forceActivateMovement(dir);
		}
	}

	abstract void forceActivateMovement(MoveDir dir);

	abstract void finishMove();

	public MoveDir getDir() {
		return dir;
	}

	public void setDir(MoveDir dir) {
		this.dir = dir;
	}

	public Action getAction() {
		return action;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public T getEntityNextTo() {
		return getEntityAt(getPointNextTo());
	}

	public T getEntityNextTo(T nullObject) {
		T nextTo = getEntityNextTo();
		if (nextTo == null) return nullObject;
		else return nextTo;
	}

	protected boolean isNextToBlocked() {
		return isPosBlocked(getPointNextTo());
	}

	private Point getPointNextTo() {
		return new Point(x+dir.getX(), y+dir.getY());
	}

	public boolean equals(Object o) {
		return super.equals(o) || (isMoving() && getPointNextTo().equals(o));
	}

	public boolean isMoving() {
		return !action.isFinished() && action.hasType(MovementActionType.move);
	}

	public void face(MovablePosition<T> caller) {
		setDir(caller.getDir().getOpposite());
	}

}
