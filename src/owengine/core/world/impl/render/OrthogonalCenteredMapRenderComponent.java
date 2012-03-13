package owengine.core.world.impl.render;

import owengine.core.world.MapRenderComponent;
import owengine.core.world.World;

public abstract class OrthogonalCenteredMapRenderComponent extends MapRenderComponent {

	protected World world = World.getInstance();
	protected int fieldSize;
	protected int width;
	protected int height;

	public void setFieldSize(int fieldSize) {
		this.fieldSize = fieldSize;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getFieldSize() {
		return fieldSize;
	}

	protected int getX() {
		return (int)(-world.getPlayer().getXf()*getFieldSize());
	}

	protected int getY() {
		return (int)(-world.getPlayer().getYf()*getFieldSize());
	}

	protected int getHeight() {
		return height;
	}

	protected int getWidth() {
		return width;
	}

}
