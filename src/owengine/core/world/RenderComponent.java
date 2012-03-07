package owengine.core.world;

import java.awt.Graphics;

public abstract class RenderComponent {

	protected Entity entity;
	protected RenderHelper helper;

	void setEntity(Entity entity) {
		this.entity = entity;
	}

	public void setRenderHelper(RenderHelper helper) {
		this.helper = helper;
	}

	public int getX() {
		return helper.getX(entity.getXf());
	}

	public int getY() {
		return helper.getY(entity.getYf());
	}

	public int getWidth() {
		return helper.getWidth();
	}

	public int getHeight() {
		return helper.getHeight();
	}

	public abstract void paint(Graphics g);

}
