package owengine.core.world;

public abstract class RenderHelper {

	protected int fieldSize;

	public int getFieldSize() {
		return fieldSize;
	}

	public void setFieldSize(int fieldSize) {
		this.fieldSize = fieldSize;
	}

	public abstract int getX(float x);

	public abstract int getY(float y);

	public abstract int getHeight();

	public abstract int getWidth();

}
