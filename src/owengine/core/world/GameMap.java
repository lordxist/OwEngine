package owengine.core.world;

import java.awt.Graphics;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Vector2d;

public class GameMap {

	private Set<Entity> entities = new HashSet<Entity>();
	private HashMap<Vector2d, StoryEvent> events = new HashMap<Vector2d, StoryEvent>();
	private StoryEvent event = StoryEvent.NULL_EVENT;
	private MapRenderComponent renderComponent;

	public void addEntity(Entity e) {
		e.setMap(this);
		entities.add(e);
	}

	public void addEvent(Vector2d pos, StoryEvent event) {
		events.put(pos, event);
	}

	public void paint(Graphics g) {
		renderComponent.paint(g);
		for (Entity e : entities) {
			e.paint(g);
		}
	}

	public void update(int delta) {
		for (Entity e : entities) {
			e.update(delta);
		}
		
		if (!event.isFinished()) {
			return;
		}
		Entity player = World.getInstance().getPlayer();
		StoryEvent event = events.get(new Vector2d(player.getX(), player.getY()));
		if (event != null) {
			this.event = event;
			new Thread(event).start();
		}
	}

	public boolean isBlocked(int x, int y) {
		for (Entity e : entities) {
			if (e.getX() == x && e.getY() == y) {
				return true;
			}
		}
		return false;
	}

	public void setRenderComponent(MapRenderComponent renderComponent) {
		this.renderComponent = renderComponent;
		renderComponent.setMap(this);
	}

	public Set<Entity> getEntities() {
		return Collections.unmodifiableSet(entities);
	}

}
