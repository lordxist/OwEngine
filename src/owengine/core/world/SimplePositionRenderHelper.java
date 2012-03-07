package owengine.core.world;

public class SimplePositionRenderHelper implements RenderHelper {

	protected int fieldSize;

	public SimplePositionRenderHelper(int fieldSize) {
		this.fieldSize = fieldSize;
	}

	@Override
	public int getX(float x) {
		return (int)(x*fieldSize);
	}

	@Override
	public int getY(float y) {
		return (int)(y*fieldSize);
	}

	@Override
	public int getHeight() {
		return fieldSize;
	}

	@Override
	public int getWidth() {
		return fieldSize;
	}

	@Override
	public int getFieldSize() {
		return fieldSize;
	}

}
