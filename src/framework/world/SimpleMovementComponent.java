package framework.world;

import javax.vecmath.Vector2f;

public class SimpleMovementComponent extends Component {

	public static final int MOVEMENT_DURATION = 1000;

	private boolean moving;
	private int xTarget;
	private int yTarget;
	private int moveTime;

	@Override
	public void setEntity(Entity entity) {
		super.setEntity(entity);
		xTarget = entity.getX();
		yTarget = entity.getY();
	}

	protected void startMove(int xTarget, int yTarget) {
		if (moving || entity.getWorld().isBlocked(xTarget, yTarget)) {
			return;
		}
		this.xTarget = xTarget;
		this.yTarget = yTarget;
		moving = true;
	}

	@Override
	public void update(int delta) {
		if (moving) {
			moveTime += delta;
			float deltaPos = (((float)moveTime)/MOVEMENT_DURATION);
			entity.setDeltaPos(
					new Vector2f((xTarget-entity.getX())*deltaPos,
							(yTarget-entity.getY())*deltaPos));
			if (moveTime >= MOVEMENT_DURATION) {
				moving = false;
				moveTime = 0;
				entity.setDeltaPos(new Vector2f(0, 0));
				entity.setX(xTarget);
				entity.setY(yTarget);
			}
		}
	}

}
