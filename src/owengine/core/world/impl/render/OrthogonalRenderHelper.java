package owengine.core.world.impl.render;

import owengine.core.world.RenderHelper;

public class OrthogonalRenderHelper extends RenderHelper {

	@Override
	public int getHeight() {
		return fieldSize;
	}

	@Override
	public int getWidth() {
		return fieldSize;
	}

	@Override
	public int getX(float x) {
		return (int) (x*fieldSize);
	}

	@Override
	public int getY(float y) {
		return (int) (y*fieldSize);
	}

}
