package owengine.model.story;

import java.awt.Point;

import owengine.model.entity.Entity;
import owengine.model.movement.MovablePosition;
import owengine.model.util.position.BasicPositioned;
import owengine.model.util.position.PositionedArrayList;
import owengine.model.warp.WarpMap;
import owengine.model.warp.WarpMapPosition;

public class EventMap<T extends Entity> extends WarpMap<T> {

	private static class EventTile extends BasicPositioned {

		private static final EventTile NULL = new EventTile(0, 0, null, null) {
			@Override
			void runEvent() {}
		};

		private Point pos;
		private StoryEvent event;

		private EventTile(int x, int y, StoryEvent event, EventActionQueue queue) {
			this.pos = new Point(x, y);
			this.event = event;
			if (this.event != null) {
				this.event.setQueue(queue);
			}
		}

		@Override
		public Point getPos() {
			return pos;
		}

		private StoryEvent getEvent() {
			return event;
		}

		void runEvent() {
			event.run();
		}

	}

	private PositionedArrayList<EventTile> eventTiles =
		new PositionedArrayList<EventTile>(EventTile.NULL);
	private MovablePosition<T> player;
	private EventActionQueue queue;

	public EventMap(MovablePosition<T> player) {
		this.player = player;
		this.queue = new EventActionQueue();
	}

	public void update(int delta) {
		super.update(delta);
		for (EventTile e : eventTiles)
			e.getEvent().update(delta);
	}

	public void addEventTile(int x, int y, StoryEvent event) {
		eventTiles.add(new EventTile(x, y, event, queue));
	}

	public void removeEventTile(EventTile e) {
		eventTiles.remove(e);
	}

	@Override
	public void warp(WarpMapPosition<T> w) {
		super.warp(w);
		if (w != player) return;
		eventTiles.at(w.getPos()).runEvent();
	}

}
