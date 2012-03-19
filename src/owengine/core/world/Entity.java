package owengine.core.world;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

import javax.vecmath.Vector2f;

import owengine.core.util.direction.Direction;
import owengine.core.util.timed.ActionUser;
import owengine.core.util.timed.TimedAction;

public class Entity implements ActionUser {

	public static abstract class EntityStoryEvent extends StoryEvent {

		protected Entity entity;

		public EntityStoryEvent(String name) {
			super(name);
		}
	
		private void setEntity(Entity entity) {
			this.entity = entity;
		}
	
	}

	private static HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();

	public static Entity getById(int id) {
		return entities.get(id);
	}

	public static final int STD_MOVEMENT_DURATION = 300;

	protected RenderComponent renderComponent;
	protected int id;
	protected String type;
	protected Point position;
	protected Vector2f deltaPos = new Vector2f(0, 0);
	protected Direction direction = Direction.south;
	protected GameMap map;
	protected TimedAction action = TimedAction.NULL_ACTION;
	protected int movementDuration = STD_MOVEMENT_DURATION;
	protected StoryEvent event = StoryEvent.NULL_EVENT;
	protected String message;
	protected HashMap<String, String> properties = new HashMap<String, String>();

	public Entity(int id, String type, Point position) {
		this.id = id;
		this.type = type;
		this.position = position;
		entities.put(id, this);
	}

	public Entity(int id, String type, Point position, StoryEvent event) {
		this(id, type, position);
		this.event = event;
	}

	public Entity(int id, String type, Point position, String message) {
		this(id, type, position);
		this.message = message;
	}

	public Entity(int id, String type, Point position, StoryEvent event, 
			String message) {
		this(id, type, position, event);
		this.message = message;
	}

	public void update(int delta) {
		action.update(delta);
	}

	public void paint(Graphics g) {
		renderComponent.paint(g);
	}

	public RenderComponent getRenderComponent() {
		return renderComponent;
	}

	public void setRenderComponent(RenderComponent renderComponent) {
		this.renderComponent = renderComponent;
		renderComponent.setEntity(this);
	}

	public String getType() {
		return type;
	}

	public Point getPosition() {
		return new Point(position);
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point posNextTo(Direction direction) {
		Point result = getPosition();
		Point vector = direction.getVector();
		result.translate(vector.x, vector.y);
		return result;
	}

	public Vector2f getDeltaPos() {
		return new Vector2f(deltaPos);
	}

	public void setDeltaPos(Vector2f deltaPos) {
		this.deltaPos = deltaPos;
	}

	public Vector2f getExactPos() {
		Point position = getPosition();
		Vector2f result = new Vector2f(position.x, position.y);
		result.add(getDeltaPos());
		return result;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}

	public void applyAction(EntityAction action) {
		if (isActionFinished()) {
			action.setEntity(this);
			this.action = action;
		}
	}

	public TimedAction getAction() {
		return action;
	}

	@Override
	public boolean isActionFinished() {
		return action.isFinished();
	}

	public void applyMovement(Direction direction) {
		if (isBlocked(posNextTo(direction))) {
			return;
		}
		applyAction(new MovementAction(movementDuration, direction));
	}

	public boolean blocks(Point position) {
		return this.position.equals(position);
	}

	protected boolean isBlocked(Point position) {
		return map.isBlocked(position);
	}

	public StoryEvent getEvent() {
		return event;
	}

	public void setEvent(EntityStoryEvent event) {
		this.event = event;
		event.setEntity(this);
	}

	public int getMovementDuration() {
		return movementDuration;
	}

	public void setMovementDuration(int movementDuration) {
		this.movementDuration = movementDuration;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HashMap<String, String> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}

	public void explore() {
		new Thread(event).start();
	}

	@Override
	public String toString() {
		String result = type;
		if (!action.isFinished()) {
			result += "_" + action;
		}
		result += "_" + direction;
		return result;
	}

}
