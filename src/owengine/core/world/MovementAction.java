package owengine.core.world;

import java.awt.Point;

import javax.vecmath.Vector2f;

public class MovementAction extends EntityAction {

	private Direction direction;

	public MovementAction(int duration, Direction direction) {
		super("move", duration);
		this.direction = direction;
	}

	@Override
	public void updateAction(float delta) {
		entity.setDirection(direction);
		entity.setDeltaPos(newDeltaPos(delta));
	}

	private Vector2f newDeltaPos(float delta) {
		Point vector = direction.getVector();
		Vector2f result = new Vector2f(vector.x, vector.y);
		result.scale(delta);
		return result;
	}

	@Override
	protected void finish() {
		entity.setPosition(entity.posNextTo(direction));
		entity.setDeltaPos(new Vector2f(0, 0));
	}

}
