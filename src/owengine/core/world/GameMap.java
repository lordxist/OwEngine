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
	private HashMap<Point, Warp> warps = new HashMap<Point, Warp>();
	private HashMap<String, Layer> layers = new HashMap<String, Layer>();

	private class Layer {
	
		private String name;
		private HashMap<Point, TimedAction> posActions = new HashMap<Point, TimedAction>();
	
		private Layer(String name) {
			this.name = name;
		}

		private void addPosAction(final Point pos, final TimedAction action) {
			TimedAction tileAction;
			int tileId;
			if ((tileId = getTileId(name, pos)) != -1) {
				tileAction = new TimedAction(action.getName() + "_" + tileId,
						action.getDuration()) {
					@Override
					public void start() {
						name = action.getName() + "_" + getTileId(Layer.this.name, pos);
						super.start();
					}
					
					@Override
					public void updateAction(float delta) {
						action.updateAction(delta);
					}
				};
			} else {
				tileAction = action;
			}
			posActions.put(pos, tileAction);
		}

		private void addPosAction(Point pos, String name, int duration) {
			addPosAction(pos, new TimedAction(name, duration) {
				@Override public void updateAction(float delta) {}
			});
		}
	
		private void touchPos(Entity entity) {
			if (posActions.get(entity.getPosition()) != null) {
				posActions.get(entity.getPosition()).start();
			}
		}
	
	}

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
		return Collections.unmodifiableSet(entities);
	}

	public void touchPos(Entity entity) {
		for (Layer layer : layers.values()) {
			layer.touchPos(entity);
		}
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
			player.action = TimedAction.NULL_ACTION;
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

	public void addPosAction(String layerName, Point pos, String name, int duration) {
		layers.get(layerName).addPosAction(pos, name, duration);
	}

	public void addPosAction(String layerName, Point pos, TimedAction action) {
		layers.get(layerName).addPosAction(pos, action);
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
