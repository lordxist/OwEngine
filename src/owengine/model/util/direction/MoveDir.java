package owengine.model.util.direction;

public enum MoveDir {

	right(1, 0),
	left(right),
	down(0, 1),
	up(down);

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
