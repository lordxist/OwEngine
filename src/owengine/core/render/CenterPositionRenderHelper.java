package owengine.core.render;

import javax.vecmath.Vector2f;

import owengine.core.world.SimplePositionRenderHelper;

public class CenterPositionRenderHelper extends SimplePositionRenderHelper {

	private Vector2f center;

	public CenterPositionRenderHelper(int fieldSize, Vector2f center) {
		super(fieldSize);
		this.center = center;
	}

	@Override
	public int getX(float x) {
		return (int)(center.x*fieldSize);
	}

	@Override
	public int getY(float y) {
		return (int)(center.y*fieldSize);
	}

}
