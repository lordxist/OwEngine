package owengine.core.world;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

import javax.vecmath.Vector2f;

import owengine.core.util.timed.ActionUser;
import owengine.core.util.timed.TimedAction;

public class Entity implements ActionUser, PositionedRenderer.Positioned {

	public static abstract class EntityEvent extends StoryEvent {

		public static final EntityEvent NULL_ENTITY_EVENT = new EntityEvent(null) {
			@Override
			public void runEvent() {}
		};
		
		protected Entity entity;

		public EntityEvent(String name) {
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

	protected PositionedRenderer<Entity> renderComponent;
	protected EntityController controller = EntityController.NULL_CONTROLLER;
	protected int id;
	protected String type;
	protected Point position;
	protected Vector2f deltaPos = new Vector2f(0, 0);
	protected Direction direction = Direction.south;
	protected GameMap map;
	protected boolean blocking = true;
	protected TimedAction action = TimedAction.NULL_ACTION;
	protected int movementDuration = STD_MOVEMENT_DURATION;
	protected EntityEvent event = EntityEvent.NULL_ENTITY_EVENT;
	protected EntityEvent touchEvent = EntityEvent.NULL_ENTITY_EVENT;
	protected String message;
	protected HashMap<String, String> properties = new HashMap<String, String>();

	public Entity(int id) {
		this.id = id;
	}

	public Entity(int id, String type, Point position) {
		this(id);
		this.type = type;
		this.position = position;
		entities.put(id, this);
	}

	public Entity(int id, String type, Point position, EntityEvent event) {
		this(id, type, position);
		this.event = event;
	}

	public Entity(int id, String type, Point position, String message) {
		this(id, type, position);
		this.message = message;
	}

	public Entity(int id, String type, Point position, EntityEvent event, 
			String message) {
		this(id, type, position, event);
		this.message = message;
	}

	public void update(int delta) {
		action.update(delta);
	}

	public void updateController(int delta) {
		controller.update(delta);		
	}

	public void paint(Graphics g) {
		renderComponent.paint(g);
	}

	public PositionedRenderer<Entity> getRenderComponent() {
		return renderComponent;
	}

	public void setRenderComponent(PositionedRenderer<Entity> renderComponent) {
		this.renderComponent = renderComponent;
		renderComponent.setRendered(this);
	}

	public EntityController getController() {
		return controller;
	}

	public void setController(EntityController controller) {
		this.controller = controller;
		controller.setEntity(this);
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Point getPosition() {
		return new Point(position);
	}

	public void setPosition(Point position) {
		Point oldPosition = this.position;
		this.position = position;
		if (!this.position.equals(oldPosition)) {
			map.changePosition(this);
		}
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
			action.start();
			this.action = action;
		}
	}

	public void applyAction(String name, int duration) {
		applyAction(new EntityAction(name, duration) {
			@Override public void updateAction(float delta) {}
		});
	}

	public TimedAction getAction() {
		return action;
	}

	@Override
	public boolean isActionFinished() {
		return action.isFinished();
	}

	public void applyMovement(Direction direction) {
		if (map.isBlocked(posNextTo(direction))) {
			return;
		}
		this.direction = direction;
		Entity touched = map.getEntity(posNextTo(direction));
		if (touched != null) {
			touched.touch(this);
		}
		map.touchPos(this);
		applyAction(new MovementAction(movementDuration, direction));
	}

	public boolean blocks(Point position) {
		if (!blocking) {
			return false;
		}
		boolean result = this.position.equals(position);
		if (!getDeltaPos().equals(new Vector2f(0, 0))) {
			result |= posNextTo(direction).equals(position);
		}
		return result;
	}

	public boolean isBlocking() {
		return blocking;
	}

	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
	}

	public EntityEvent getEvent() {
		return event;
	}

	public void setEvent(EntityEvent event) {
		this.event = event;
		event.setEntity(this);
	}

	public EntityEvent getTouchEvent() {
		return touchEvent;
	}

	public void setTouchEvent(EntityEvent touchEvent) {
		this.touchEvent = touchEvent;
		touchEvent.setEntity(this);
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

	public void touch(Entity toucher) {
		new Thread(touchEvent).start();
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
