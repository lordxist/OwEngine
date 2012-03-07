package owengine.core.render;

import javax.vecmath.Vector2f;

import owengine.core.world.Entity;
import owengine.core.world.SimplePositionRenderHelper;

public class CenteredPositionRenderHelper extends SimplePositionRenderHelper {

	protected Entity centerEntity;
	protected Vector2f center;

	public CenteredPositionRenderHelper(int fieldSize,
			Entity centerEntity, Vector2f center) {
		super(fieldSize);
		this.centerEntity = centerEntity;
		this.center = center;
	}

	@Override
	public int getX(float x) {
		x -= (centerEntity.getX()+centerEntity.getDeltaPos().x);
		return (int)((center.x+x)*fieldSize);	
	}

	@Override
	public int getY(float y) {
		y -= (centerEntity.getY()+centerEntity.getDeltaPos().y);
		return (int)((center.y+y)*fieldSize);
	}

}
