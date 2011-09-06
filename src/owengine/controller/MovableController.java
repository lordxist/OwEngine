package owengine.controller;

import java.awt.Point;

import owengine.model.map.MoveDir;

public class MovableController {

	protected ControlledMovable mov;
	protected int step, delta;
	protected int delay = 2000;

	public MovableController(ControlledMovable mov) {
		this.mov = mov;
	}

	public void update(int delta) {
		this.delta += delta;
		if (this.delta >= delay &&
				mov.isInactive()) {
			if (mov.getPos().equals(mov.getMoves().get(step)))
				step++;
			step %= mov.getMoves().size();
			mov.activateMovement(calcMoveDir());
			this.delta = 0;
		}
	}

	private MoveDir calcMoveDir() {
		Point vector = new Point(mov.getMoves().get(step));
		vector.translate(-mov.getPos().x, -mov.getPos().y);
		MoveDir moveDir = null;
		for (MoveDir dir : MoveDir.values())
			if (dir.getX()==vector.x && dir.getY()==vector.y)
				moveDir = dir;
		return moveDir;
	}

}
