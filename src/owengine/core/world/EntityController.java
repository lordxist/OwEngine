package owengine.core.world;

public abstract class EntityController {

	public static final EntityController NULL_CONTROLLER = new EntityController() {
		@Override protected void doUpdate(int delta) {}
	};

	protected boolean disabled;
	protected Entity entity;

	public Entity getEntity() {
		return entity;
	}

	void setEntity(Entity entity) {
		this.entity = entity;
	}

	public void disable() {
		this.disabled = true;
	}

	public void enable() {
		this.disabled = false;
	}

	protected boolean isDisabled() {
		return disabled;
	}

	public void update(int delta) {
		if (disabled) {
			return;
		}
		
		doUpdate(delta);
	}

	protected abstract void doUpdate(int delta);

}
