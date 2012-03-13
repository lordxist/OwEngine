package owengine.core.util.direction;

import javax.vecmath.Vector2f;

public enum Direction {

	west(-1, 0),
	east(1, 0),
	north(0, -1),
	south(0, 1);

	private Vector2f vector;

	Direction(int x, int y) {
		vector = new Vector2f(x, y);
	}

	public Vector2f getVector() {
		return vector;
	}

}
