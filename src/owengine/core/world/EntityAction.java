package owengine.core.world;

import owengine.core.util.timed.TimedAction;

public abstract class EntityAction extends TimedAction {

	public static final EntityAction NULL_ACTION = new EntityAction(null, 0) {

		@Override
		public void updateAction(float delta) {}

		@Override
		public boolean isFinished() {
			return true;
		}

		@Override
		public void finish() {}
		
	};

	protected Entity entity;

	public EntityAction(String name, int duration) {
		super(name, duration);
	}

	void setEntity(Entity entity) {
		this.entity = entity;
	}

}
