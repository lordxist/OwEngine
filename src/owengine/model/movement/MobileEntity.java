package owengine.model.movement;

import java.awt.Point;

import owengine.model.map.ActionEntity;
import owengine.model.map.Entity;
import owengine.model.map.EntityMap;
import owengine.model.map.MoveDir;

public abstract class MobileEntity extends ActionEntity implements Movable, Directed {

	private MovablePosition movablePos;

	public MobileEntity(Point pos, EntityMap map, MoveDir dir, int speed) {
		this.movablePos = new MovablePosition(this, map, pos.x, pos.y, dir, speed);
	}

	public void activateMovement(MoveDir dir) {
		movablePos.activateMovement(dir);
	}

	public void turn(MoveDir dir) {
		movablePos.turn(dir);
	}

	public void moveForward() {
		movablePos.moveForward();
	}

	public MoveDir getDir() {
		return movablePos.getDir();
	}

	public void setDir(MoveDir dir) {
		movablePos.setDir(dir);
	}

	public EntityMap getMap() {
		return movablePos.getMap();
	}

	public void setMap(EntityMap map) {
		movablePos.setMap(map);
	}

	public int getMoveSpeed() {
		return movablePos.getMoveSpeed();
	}

	public double getX() {
		return movablePos.getX();
	}

	public double getY() {
		return movablePos.getY();
	}

	@Override
	public MovablePosition getPos() {
		return movablePos;
	}

	@Override
	public void setPos(Point pos) {
		movablePos.setPos(pos);
	}

	public boolean isTurning() {
		return movablePos.isTurning();
	}

	public void setTurning(boolean turning) {
		movablePos.setTurning(turning);
	}

	public boolean isBumping() {
		return movablePos.isBumping();
	}

	public void setBumping(boolean bumping) {
		movablePos.setBumping(bumping);
	}

	public Entity getEntityNextTo() {
		return movablePos.getEntityNextTo(Entity.NULL);
	}

	public boolean isMoving() {
		return movablePos.isMoving();
	}

	public void face(Directed caller) {
		movablePos.face(caller);
	}

}
