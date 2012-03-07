package owengine.core.world;

public class MapRenderHelper {

	private int fieldSize;
	private int width;
	private int height;

	public MapRenderHelper(int fieldSize, int width, int height) {
		this.fieldSize = fieldSize;
		this.width = width;
		this.height = height;
	}

	public int getFieldSize() {
		return fieldSize;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}
