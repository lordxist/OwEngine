package framework.world;

public abstract class RenderComponent {

	protected Entity entity;

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public abstract void paint();

}
