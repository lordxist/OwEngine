package owengine.model.story;

import java.awt.Point;
import java.util.HashMap;

import owengine.model.map.Entity;
import owengine.model.map.EntityMap;

public class EventMap extends EntityMap {

	private HashMap<Point, StoryEvent> eventTiles = new HashMap<Point, StoryEvent>();
	private Entity player;
	private boolean eventRunning;

	public EventMap(Entity player) {
		this.player = player;
	}

	public void addEventTile(int x, int y, StoryEvent event) {
		eventTiles.put(new Point(x, y), event);
	}

	public void removeEventTile(Point pos) {
		eventTiles.remove(pos);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		if (eventRunning) {
			return;
		}
		StoryEvent event = eventTiles.get(player.getPos());
		if (event != null) {
			eventRunning = true;
			event.start();
		}
	}

	public void eventFinished() {
		eventRunning = false;
	}

}
