package owengine.core.world;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GameMap {

	private MapRenderComponent renderComponent;
	private Set<Entity> entities = new HashSet<Entity>();
	private Set<Point> blocked = new HashSet<Point>();
	private HashMap<Point, StoryEvent> events = new HashMap<Point, StoryEvent>();
	private StoryEvent event = StoryEvent.NULL_EVENT;
	private HashSet<Controller> controllers = new HashSet<Controller>();

	public void update(int delta) {
		for (Controller controller : controllers) {
			controller.update(delta);
		}
		for (Entity e : entities) {
			e.update(delta);
		}
		synchronized (event) {
			event.notify();
		}
		
		Entity player = World.getInstance().getPlayer();
		if (player != null) {
			playerUpdate(player);
		}
		
	}

	private void playerUpdate(Entity player) {
		if (!event.isFinished()) {
			return;
		}
		
		StoryEvent event = events.get(player.getPosition());
		if (event != null) {
			this.event = event;
			new Thread(event).start();
		}
	}

	public void paint(Graphics g) {
		renderComponent.paint(g);
		for (Entity e : entities) {
			e.paint(g);
		}
	}

	public void setRenderComponent(MapRenderComponent renderComponent) {
		this.renderComponent = renderComponent;
		renderComponent.setMap(this);
	}

	public void addEntity(Entity e) {
		e.setMap(this);
		entities.add(e);
	}

	public void block(Point point) {
		blocked.add(point);
	}

	public void addEvent(Point pos, StoryEvent event) {
		events.put(pos, event);
	}

	public boolean isBlocked(Point position) {
		if (blocked.contains(position)) {
			return true;
		}
		for (Entity e : entities) {
			if (e.blocks(position)) {
				return true;
			}
		}
		return false;
	}

	public Set<Entity> getEntities() {
		return Collections.unmodifiableSet(entities);
	}

	public void addController(Controller controller) {
		controllers.add(controller);
	}

	public void disableControllers() {
		for (Controller controller : controllers ) {
			controller.disable();
		}
	}

	public void enableControllers() {
		for (Controller controller : controllers ) {
			controller.enable();
		}
	}

	public Set<Controller> getControllers() {
		return Collections.unmodifiableSet(controllers);
	}

}
