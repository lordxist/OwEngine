package owengine.core.world;

import javax.vecmath.Vector2f;


public class MovementAction extends EntityAction {

	private Vector2f dir;

	public MovementAction(int duration, Vector2f dir) {
		super(duration);
		this.dir = dir;
	}

	@Override
	public void updateAction(float delta) {
		Vector2f deltaPos = new Vector2f(dir);
		deltaPos.scale(delta);
		entity.setDeltaPos(deltaPos);
	}

	@Override
	protected void finish() {
		entity.setX(entity.getX()+(int)dir.x);
		entity.setY(entity.getY()+(int)dir.y);
		entity.setDeltaPos(new Vector2f(0, 0));
	}

}
