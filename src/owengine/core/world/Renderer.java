package owengine.core.world;

import java.awt.Graphics;

import javax.vecmath.Vector2f;

public abstract class Renderer {

	public static abstract class CenteredRenderer extends Renderer {

		protected Vector2f center;
		protected Entity centerEntity;

		public void setCenter(Vector2f center) {
			this.center = center;
		}

		public void setCenterEntity(Entity centerEntity) {
			this.centerEntity = centerEntity;
		}

		@Override
		public int getX() {
			return (int) (super.getX() + helper.getX(center.x - centerEntity.getExactPos().x));
		}

		@Override
		public int getY() {
			return (int) (super.getY() + helper.getY(center.y - centerEntity.getExactPos().y));
		}

	}

	protected RenderHelper helper;
	protected Entity entity;

	void setEntity(Entity entity) {
		this.entity = entity;
	}

	public abstract void paint(Graphics g);

	public RenderHelper getHelper() {
		return helper;
	}

	public void setHelper(RenderHelper helper) {
		this.helper = helper;
	}

	public int getX() {
		return helper.getX(entity.getExactPos().x);
	}

	public int getY() {
		return helper.getY(entity.getExactPos().y);
	}

	public int getWidth() {
		return helper.getWidth();
	}

	public int getHeight() {
		return helper.getHeight();
	}

}
