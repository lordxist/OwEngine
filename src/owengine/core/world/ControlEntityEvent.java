package owengine.core.world;


public abstract class ControlEntityEvent extends StoryEvent {

	protected Entity entity;

	public ControlEntityEvent(String name) {
		super(name);
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
