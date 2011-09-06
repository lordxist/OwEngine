package owengine.model.story;

import java.awt.Point;

import owengine.model.map.Entity;
import owengine.model.map.EntityMap;
import owengine.model.util.NullObjectHashMap;

public class EventMap extends EntityMap {

	private NullObjectHashMap<Point, StoryEvent> eventTiles =
		new NullObjectHashMap<Point, StoryEvent>(StoryEvent.NULL);
	private Entity player;

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
		eventTiles.get(player.getPos()).start();
	}

}
