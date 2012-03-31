package owengine.core.world;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import owengine.core.util.timed.TimedAction;

public class GameMap {

	private MapRenderer renderComponent;
	private Set<Entity> entities = new HashSet<Entity>();
	private HashMap<Point, StoryEvent> events = new HashMap<Point, StoryEvent>();
	private StoryEvent event = StoryEvent.NULL_EVENT;
	private StoryEvent mapEvent = StoryEvent.NULL_EVENT;
	private HashMap<Point, TimedAction> posActions = new HashMap<Point, TimedAction>();
	private HashMap<Point, Warp> warps = new HashMap<Point, Warp>();

	public void update(int delta) {
		for (Entity e : new HashSet<Entity>(this.entities)) {
			e.update(delta);
		}
		
		for (Entity e : entities) {
			e.updateController(delta);
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
		for (Entity entity : entities) {
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

	public void addPosAction(Point pos, TimedAction action) {
		posActions.put(pos, action);
	}

	public void addPosAction(Point pos, String name, int duration) {
		addPosAction(pos, new TimedAction(name, duration) {
			@Override public void updateAction(float delta) {}
		});
	}

	public void touchPos(Point pos) {
		posActions.get(pos).start();
	}

	public void changePosition(Entity entity) {
		if (!(entity.equals(World.getInstance().getPlayer())) || !event.isFinished()) {
			return;
		}
		
		Warp warp = warps.get(entity.getPosition());
		if (warp != null) {
			warp.warp(entity);
		}
		
		StoryEvent event = events.get(entity.getPosition());
		if (event != null) {
			startEvent(event);
		}
	}

	public void addWarp(Point pos, Warp warp) {
		warps.put(pos, warp);
	}

	/**
	 * Convenience method to retrieve a specific tile's id.
	 * Implemented only in subclasses in extensions.
	 */
	public int getTileId(String layer, Point pos) {
		return -1;
	}

	/**
	 * Convenience method to manipulate a specific tile.
	 * Implemented only in subclasses in extensions.
	 */
	public void setTileId(String layer, Point pos, int id) {}

}
