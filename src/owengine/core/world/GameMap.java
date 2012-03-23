package owengine.core.world;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameMap {

	public static class Layer {
	
		private HashMap<Point, String> tileTypes = new HashMap<Point, String>();
		private String name;
		
		public String getName() {
			return name;
		}
	
		public String getTileType(Point pos) {
			return tileTypes.get(pos);
		}
	
		public void setTileType(Point pos, String type) {
			tileTypes.put(pos, type);
		}
	
	}

	private MapRenderer renderComponent;
	private Set<Entity> entities = new HashSet<Entity>();
	private Set<Point> blocked = new HashSet<Point>();
	private List<Layer> layers = new ArrayList<Layer>();
	private HashMap<Point, StoryEvent> events = new HashMap<Point, StoryEvent>();
	private StoryEvent event = StoryEvent.NULL_EVENT;
	private Set<StoryEvent> mapEvents = new HashSet<StoryEvent>();

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
		for (Entity e : entities) {
			e.paint(g);
		}
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

	public void block(Point point) {
		blocked.add(point);
	}

	public void addLayer(Layer layer) {
		layers.add(layer);
	}

	public Layer getLayerByZOrder(int zOrder) {
		return layers.get(zOrder);
	}

	public Layer getLayerByName(String name) {
		for (Layer layer : layers) {
			if (layer.getName().equals(name)) {
				return layer;
			}
		}
		return null;
	}

	public List<Layer> getLayers() {
		return Collections.unmodifiableList(layers);
	}

	public void addEvent(Point pos, StoryEvent event) {
		events.put(pos, event);
	}

	public void addMapEvent(StoryEvent event) {
		mapEvents.add(event);
		event.setMap(this);
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

}
