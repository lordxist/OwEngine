package owengine.core.world.impl;

import owengine.core.world.Direction;
import owengine.core.world.EntityController;

public class MovingController extends EntityController {

	private Direction[] path;

	public MovingController(Direction[] path) {
		this.path = path;
	}

	@Override
	public void update(int delta) {
		
	}

}
