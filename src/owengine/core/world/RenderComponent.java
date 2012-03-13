package owengine.core.world;

import java.awt.Graphics;

public abstract class RenderComponent {

	protected Entity entity;

	void setEntity(Entity entity) {
		this.entity = entity;
	}

	public abstract void paint(Graphics g);

}
