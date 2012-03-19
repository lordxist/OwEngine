package owengine.core.util.direction;

import java.awt.Point;

public enum Direction {

	west(-1, 0),
	east(1, 0),
	north(0, -1),
	south(0, 1);

	private Point vector;

	Direction(int x, int y) {
		vector = new Point(x, y);
	}

	public Point getVector() {
		return vector;
	}

}
