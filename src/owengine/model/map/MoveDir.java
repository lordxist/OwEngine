package owengine.model.map;

import java.awt.Point;
import java.util.HashMap;

public enum MoveDir {

	right(1, 0),
	left(right),
	down(0, 1),
	up(down);

	private static HashMap<Point, MoveDir> instances = new HashMap<Point, MoveDir>();

	static {
		instances.put(new Point(-1, 0), left);
		instances.put(new Point(1, 0), right);
		instances.put(new Point(0, -1), up);
		instances.put(new Point(0, 1), down);
	}

	public static MoveDir relativeDir(Point p1, Point p2) {
		return instances.get(new Point(p1.x - p2.x, p1.y - p2.y));
	}

	private final ImmuVector2d vector;
	private MoveDir opposite;

	private MoveDir(int x, int y) {
		vector = new ImmuVector2d(x, y);
	}

	private MoveDir(MoveDir opposite) {
		this.opposite = opposite;
		opposite.opposite = this;
		vector = opposite.vector.getReverse();
	}

	public int getX() {
		return vector.x;
	}

	public int getY() {
		return vector.y;
	}

	public MoveDir getOpposite() {
		return opposite;
	}

	private class ImmuVector2d {
	
		private final int x, y;
	
		private ImmuVector2d(int x, int y) {
			this.x = x;
			this.y = y;
		}
	
		private ImmuVector2d getReverse() {
			return new ImmuVector2d(-x, -y);
		}
	
	}

}
