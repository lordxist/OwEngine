package owengine.core.world;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GameMap {

	private MapRenderer renderComponent;
	private Set<Entity> entities = new HashSet<Entity>();
	private HashMap<String, Set<Entity>> entityGroups = new HashMap<String, Set<Entity>>();
	private HashMap<Point, StoryEvent> events = new HashMap<Point, StoryEvent>();
	private StoryEvent event = StoryEvent.NULL_EVENT;
	private StoryEvent mapEvent = StoryEvent.NULL_EVENT;
	private HashMap<Point, Warp> warps = new HashMap<Point, Warp>();

	public void update(int delta) {
		for (Entity e : entities) {
			e.updateController(delta);
		}
		
		for (Entity e : new HashSet<Entity>(this.entities)) {
			e.update(delta);
		}
		
		synchronized (event) {
			event.notify();
		}
	}

	private void startEvent(StoryEvent event) {
		this.event = event;
		World.getInstance().getPlayer().getController().disable();
		new Thread(event).start();
	}

	public void enter() {}

	public void startMapEvent() {
		startEvent(mapEvent);
	}

	public void paint(Graphics g) {
		renderComponent.paint(g);
	}

	public void setRenderComponent(MapRenderer renderComponent) {
		this.renderComponent = renderComponent;
		renderComponent.setMap(this);
	}

	public void addEntity(Entity e) {
		if (e.getMap() != null) {
			e.getMap().removeEntity(e);
		}
		e.setMap(this);
		entities.add(e);
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	public Entity getEntity(Point pos) {
		for (Entity entity : getEntities()) {
			if (entity.getPosition().equals(pos)) {
				return entity;
			}
		}
		return null;
	}

	public void addEvent(Point pos, StoryEvent event) {
		events.put(pos, event);
	}

	public void setMapEvent(StoryEvent mapEvent) {
		this.mapEvent = mapEvent;
		mapEvent.setMap(this);
	}

	public boolean isBlocked(Point position) {
		if (position.x < 0 || position.y < 0) {
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
		HashSet<Entity> result = new HashSet<Entity>();
		for (Set<Entity> entities : entityGroups.values()) {
			result.addAll(entities);
		}
		result.addAll(entities);
		return Collections.unmodifiableSet(result);
	}

	public void addEntityGroup(String name, Set<Entity> entities) {
		entityGroups.put(name, entities);
	}

	public Set<Entity> getEntityGroup(String name) {
		return Collections.unmodifiableSet(entityGroups.get(name));
	}

	public void touchPos(Entity entity) {
		if (entity.equals(World.getInstance().getPlayer())) {
			playerTouchPos(entity);
		}
	}

	private void playerTouchPos(Entity player) {
		if (!event.isFinished()) {
			return;
		}
		Warp warp = warps.get(player.posNextTo(player.getDirection()));
		if (warp != null && warp.isDoorWarp()) {
			warp.warp(player);
		}
	}

	public void changePosition(Entity entity) {
		if (entity.equals(World.getInstance().getPlayer())) {
			playerChangePosition(entity);
		}
	}

	private void playerChangePosition(Entity player) {
		if (!event.isFinished()) {
			return;
		}
		Warp warp = warps.get(player.getPosition());
		if (warp != null) {
			warp.warp(player);
		}
		StoryEvent event = events.get(player.getPosition());
		if (event != null) {
			startEvent(event);
		}
	}

	public void addWarp(Point pos, Warp warp) {
		warps.put(pos, warp);
	}

}
