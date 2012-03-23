package owengine.core.world.impl;

import owengine.core.util.direction.Direction;
import owengine.core.world.ControlEntityEvent;
import owengine.core.world.World;

/**
 * A map event to move an entity a certain path.
 */
public class MovingEvent extends ControlEntityEvent {

	private Direction[] path;

	public MovingEvent(String name, Direction[] path) {
		super(name);
		this.path = path;
	}

	@Override
	public void runEvent() {
		while (getMap().getEntities().contains(World.getInstance().getPlayer())) {
			runRepetitiveEvent();
		}
	}

	protected void runRepetitiveEvent() {
		for (Direction step : path) {
			while (entity.isActionFinished()) {
				entity.applyMovement(step);
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			pauseForAction(entity);
		}
	}

}
