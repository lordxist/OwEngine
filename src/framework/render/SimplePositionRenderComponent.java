package framework.render;

import framework.world.RenderComponent;

public abstract class SimplePositionRenderComponent extends RenderComponent {

	protected int fieldSize;
	protected int width;
	protected int height;

	public SimplePositionRenderComponent(int fieldSize) {
		this.fieldSize = fieldSize;
		this.width = fieldSize;
		this.height = fieldSize;
	}

	public int getX() {
		return (int)((entity.getX()+entity.getDeltaPos().x)*fieldSize);
	}

	public int getY() {
		return (int)((entity.getY()+entity.getDeltaPos().y)*fieldSize);
	}

}
