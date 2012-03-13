package owengine.core.world;

import java.awt.Graphics;

import javax.vecmath.Vector2f;

import owengine.core.util.direction.Direction;
import owengine.core.util.timed.ActionUser;
import owengine.core.util.timed.TimedAction;

public class Entity implements ActionUser {

	public static final int STD_MOVEMENT_DURATION = 300;

	protected RenderComponent renderComponent;
	protected int x;
	protected int y;
	protected Vector2f deltaPos = new Vector2f(0, 0);
	protected Direction direction = Direction.south;
	protected GameMap map;
	protected String type;
	protected TimedAction action = TimedAction.NULL_ACTION;
	protected int movementDuration = STD_MOVEMENT_DURATION;

	public Entity(String type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}

	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}

	public RenderComponent getRenderComponent() {
		return renderComponent;
	}

	public void setRenderComponent(RenderComponent renderComponent) {
		this.renderComponent = renderComponent;
		renderComponent.setEntity(this);
	}

	public void applyAction(EntityAction action) {
		if (isActionFinished()) {
			action.setEntity(this);
			this.action = action;
		}
	}

	@Override
	public boolean isActionFinished() {
		return action.isFinished();
	}

	public String getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void paint(Graphics g) {
		renderComponent.paint(g);
	}

	public void update(int delta) {
		action.update(delta);
	}

	public Vector2f getDeltaPos() {
		return deltaPos;
	}

	public void setDeltaPos(Vector2f deltaPos) {
		this.deltaPos = deltaPos;
	}

	public float getXf() {
		return getX()+getDeltaPos().x;
	}

	public float getYf() {
		return getY()+getDeltaPos().y;
	}

	public TimedAction getAction() {
		return action;
	}

	public void explore() {}

	public void applyMovement(Direction direction) {
		this.direction = direction;
		Vector2f vector = direction.getVector();
		if (map.isBlocked(x+(int)vector.x, y+(int)vector.y)){
			return;
		}
		applyAction(new MovementAction(movementDuration, direction));
	}

	public Direction getDirection() {
		return direction;
	}

}
