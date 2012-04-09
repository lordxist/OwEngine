package owengine.core.world;

public abstract class EntityEvent extends StoryEvent {

	public static final EntityEvent NULL_ENTITY_EVENT = new EntityEvent(null) {
		@Override
		public void runEvent() {}
	};
	
	protected Entity entity;

	public EntityEvent(String name) {
		super(name);
	}

	void setEntity(Entity entity) {
		this.entity = entity;
	}

}
