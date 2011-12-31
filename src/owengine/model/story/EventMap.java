package owengine.model.story;

import java.awt.Point;
import java.util.HashMap;

import owengine.model.entities.character.PlayerChar;
import owengine.model.map.Entity;
import owengine.model.map.EntityMap;

public class EventMap extends EntityMap {

	private HashMap<Point, StoryEvent> eventTiles = new HashMap<Point, StoryEvent>();
	private Entity player;
	private boolean eventRunning;
	private StoryEvent event;

	public EventMap() {
		player = PlayerChar.getInstance();
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
		if (event == null) {
			event = eventTiles.get(player.getPos());
		}
		if (event != null) {
			eventRunning = true;
			event.start();
		}
	}

	public void eventStarted(StoryEvent event) {
		this.event = event;
		eventRunning = true;
	}

	public void eventFinished() {
		eventRunning = false;
		event = null;
	}

	public void pauseEvents() {
		if (event != null) {
			synchronized (event) {
				event.setPaused(true);
			}
		}
	}

	public void unpauseEvents() {
		if (event != null) {
			synchronized (event) {
				event.setPaused(false);
				event.notify();
			}
		}
	}

}
