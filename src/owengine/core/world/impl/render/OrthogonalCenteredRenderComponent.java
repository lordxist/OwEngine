package owengine.core.world.impl.render;

import javax.vecmath.Vector2f;

import owengine.core.world.Entity;
import owengine.core.world.RenderComponent;

public abstract class OrthogonalCenteredRenderComponent extends RenderComponent {

	protected int fieldSize;
	protected Vector2f center;
	protected Entity centerEntity;

	public void setFieldSize(int fieldSize) {
		this.fieldSize = fieldSize;
	}

	public void setCenter(Vector2f center) {
		this.center = center;
	}

	public void setCenterEntity(Entity centerEntity) {
		this.centerEntity = centerEntity;
	}

	public int getX() {
		float x = entity.getExactPos().x - centerEntity.getExactPos().x;
		return (int)((center.x+x)*fieldSize);
	}

	public int getY() {
		float y = entity.getExactPos().y - centerEntity.getExactPos().y;
		return (int)((center.y+y)*fieldSize);
	}

	public int getWidth() {
		return fieldSize;
	}

	public int getHeight() {
		return fieldSize;
	}

}
