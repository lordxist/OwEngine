package owengine.core.world;

import javax.vecmath.Vector2f;

import owengine.core.util.direction.Direction;

public class MovementAction extends EntityAction {

	private Vector2f vector;

	public MovementAction(int duration, Direction direction) {
		super("move", duration);
		vector = direction.getVector();
	}

	@Override
	public void updateAction(float delta) {
		Vector2f deltaPos = new Vector2f(vector);
		deltaPos.scale(delta);
		entity.setDeltaPos(deltaPos);
	}

	@Override
	protected void finish() {
		entity.setX(entity.getX()+(int)vector.x);
		entity.setY(entity.getY()+(int)vector.y);
		entity.setDeltaPos(new Vector2f(0, 0));
	}

}
