package owengine.core.world.impl;

import java.awt.Point;
import java.util.ArrayList;

import owengine.core.util.direction.Direction;
import owengine.core.world.Entity;
import owengine.core.world.GameMap;
import owengine.core.world.MapEvent;
import owengine.core.world.StoryEvent;

/**
 * An entity that is setup to move a certain path.
 * Adds a map event to its map.
 */
public class MovingEntity extends Entity {

	private class MovingEvent extends MapEvent {
	
		public MovingEvent() {
			super("moving_"+id);
			
			String pathProperty = properties.get("path");
			if (pathProperty != null) {
				for (String dir : pathProperty.split(";")) {
					path.add(Direction.valueOf(dir));
				}
			}
		}

		private ArrayList<Direction> path = new ArrayList<Direction>();
	
		@Override
		public void runRepetitiveEvent() {
			for (Direction step : path) {
				while (isActionFinished()) {
					applyMovement(step);
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				pauseForAction(MovingEntity.this);
			}
		}
	
	}

	public MovingEntity(int id, String type, Point position) {
		super(id, type, position);
	}

	public MovingEntity(int id, String type, Point position, StoryEvent event) {
		super(id, type, position, event);
	}

	public MovingEntity(int id, String type, Point position, StoryEvent event,
			String message) {
		super(id, type, position, event, message);
	}

	public MovingEntity(int id, String type, Point position, String message) {
		super(id, type, position, message);
	}

	@Override
	public void setMap(GameMap map) {
		super.setMap(map);
		map.addMapEvent(new MovingEvent());
	}

}
