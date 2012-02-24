package framework.world;

public abstract class Component {

	protected Entity entity;

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public abstract void update(int delta);

}
