package owengine.core.world;

import owengine.core.util.timed.TimedAction;

public abstract class EntityAction extends TimedAction {

	protected Entity entity;

	public EntityAction(int duration) {
		super(duration);
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
