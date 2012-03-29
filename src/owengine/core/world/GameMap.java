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
	private Set<StoryEvent> mapEvents = new HashSet<StoryEvent>();
	private HashMap<Point, TimedAction> posActions = new HashMap<Point, TimedAction>();
	private HashMap<Point, Warp> warps = new HashMap<Point, Warp>();

	public void update(int delta) {
		for (Entity e : entities) {
			e.update(delta);
		}
		
		synchronized (event) {
			event.notify();
		}
		for (StoryEvent startEvent : mapEvents) {
			synchronized (startEvent) {
				startEvent.notify();
			}
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
		
		Warp warp = warps.get(player.getPosition());
		if (warp != null) {
			warp.warp(player);
		}
		
		StoryEvent event = events.get(player.getPosition());
		if (event != null) {
			this.event = event;
			new Thread(event).start();
		}
	}

	public void startMapEvents() {
		for (StoryEvent mapEvent : mapEvents) {
			new Thread(mapEvent).start();
		}
	}

	public void paint(Graphics g) {
		renderComponent.paint(g);
	}

	public void setRenderComponent(MapRenderer renderComponent) {
		this.renderComponent = renderComponent;
		renderComponent.setMap(this);
	}

	public void addEntity(Entity e) {
		e.setMap(this);
		entities.add(e);
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

	public void addMapEvent(StoryEvent event) {
		mapEvents.add(event);
		event.setMap(this);
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

}
