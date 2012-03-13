package owengine.core.world;

import owengine.core.util.timed.TimedAction;

public abstract class EntityAction extends TimedAction {

	protected Entity entity;

	public EntityAction(String name, int duration) {
		super(name, duration);
	}

	void setEntity(Entity entity) {
		this.entity = entity;
	}

}
