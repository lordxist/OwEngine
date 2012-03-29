package owengine.core.world.impl;

import owengine.core.world.Direction;
import owengine.core.world.EntityController;

public class MovingController extends EntityController {

	protected Direction[] path;
	protected int step;

	public MovingController(Direction[] path) {
		this.path = path;
	}

	@Override
	public void update(int delta) {
		if (entity.isActionFinished()) {
			entity.applyMovement(path[step]);
			if (!entity.isActionFinished()) {
				nextStep();
			}
		}
	}

	protected void nextStep() {
		step++;
		if (step >= path.length) {
			step = 0;
		}
	}

}
