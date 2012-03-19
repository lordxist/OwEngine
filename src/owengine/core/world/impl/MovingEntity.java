package owengine.core.world.impl;

import java.awt.Point;
import java.util.ArrayList;

import owengine.core.world.Entity;
import owengine.core.world.GameMap;
import owengine.core.world.StoryEvent;
import owengine.core.world.impl.controller.SimpleController;

public class MovingEntity extends Entity {

	private class MovingController extends SimpleController {
	
		private ArrayList<Point> path = new ArrayList<Point>();
	
		@Override
		public void update(int delta) {
			applyMovement(direction);
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
		map.addController(new MovingController());
	}

}
