package owengine.model.movement;

import java.awt.Point;

import owengine.model.map.ActionEntity;
import owengine.model.map.Entity;
import owengine.model.map.EntityMap;
import owengine.model.map.MoveDir;
import owengine.model.util.action.Action;

public class MovablePosition extends Point {

	private static final long serialVersionUID = 1L;

	protected MoveDir dir;
	protected int moveSpeed;
	private EntityMap map;
	protected ActionEntity entity;
	private boolean turning = true;
	private boolean bumping = true;

	public MovablePosition(
			ActionEntity entity, EntityMap map ,int x, int y, MoveDir dir, int moveSpeed) {
		super(x, y);
		this.entity = entity;
		this.map = map;
		this.dir = dir;
		this.moveSpeed = moveSpeed;
	}

	public void activateMovement(MoveDir dir) {
		if (this.dir == dir) {
			moveForward();
		} else if (turning) {
			turn(dir);
		} else {
			this.dir = dir;
			moveForward();
		}
	}

	public void moveForward() {
		if (!map.isPosBlocked(getPointNextTo())) {
			entity.startAction(new Action(MovementActionType.move, moveSpeed) {
				@Override
				protected void start() {
					getMap().prepareMove(entity, getPointNextTo());
					getMap().moveOnMap(Entity.NULL, getPointNextTo());
				}
				
				@Override
				protected void finish() {
					getMap().moveOnMap(entity, getPointNextTo());
				}
			});
		} else if (bumping) {
			entity.startAction(new Action(MovementActionType.bump, moveSpeed));
		}
	}

	public void turn(final MoveDir dir) {
		if (this.dir != dir) {
			entity.startAction(new Action(MovementActionType.turn, moveSpeed/2) {
				@Override
				protected void finish() {
					MovablePosition.this.dir = dir;
				}
			});
		}
	}

	public MoveDir getDir() {
		return dir;
	}

	public void setDir(MoveDir dir) {
		this.dir = dir;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public Entity getEntityNextTo() {
		return map.getEntityAt(getPointNextTo());
	}

	public Entity getEntityNextTo(Entity nullObject) {
		Entity nextTo = getEntityNextTo();
		if (nextTo == null) return nullObject;
		else return nextTo;
	}

	private Point getPointNextTo() {
		return new Point(x+dir.getX(), y+dir.getY());
	}

	public boolean equals(Object o) {
		return super.equals(o) || (isMoving() && getPointNextTo().equals(o));
	}

	public boolean isMoving() {
		return entity.getActionType() == MovementActionType.move;
	}

	public void face(Directed caller) {
		setDir(caller.getDir().getOpposite());
	}

	public Entity getEntity() {
		return entity;
	}

	public void setMap(EntityMap map) {
		this.map = map;
	}

	public EntityMap getMap() {
		return map;
	}

	public void setPos(Point p) {
		setLocation(p);
	}

	public boolean isTurning() {
		return turning;
	}

	public void setTurning(boolean turning) {
		this.turning = turning;
	}

	public boolean isBumping() {
		return bumping;
	}

	public void setBumping(boolean bumping) {
		this.bumping = bumping;
	}

}
