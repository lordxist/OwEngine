package owengine.core.world;

import java.awt.Graphics;

import javax.vecmath.Vector2f;

public abstract class PositionedRenderer<T extends PositionedRenderer.Positioned> {

	public interface Positioned {

		Vector2f getExactPos();

	}

	public static abstract class CenteredRenderer<T extends Positioned> extends PositionedRenderer<T> {

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
	protected T rendered;

	void setRendered(T rendered) {
		this.rendered = rendered;
	}

	public abstract void paint(Graphics g);

	public RenderHelper getHelper() {
		return helper;
	}

	public void setHelper(RenderHelper helper) {
		this.helper = helper;
	}

	public int getX() {
		return helper.getX(rendered.getExactPos().x);
	}

	public int getY() {
		return helper.getY(rendered.getExactPos().y);
	}

	public int getWidth() {
		return helper.getWidth();
	}

	public int getHeight() {
		return helper.getHeight();
	}

}
