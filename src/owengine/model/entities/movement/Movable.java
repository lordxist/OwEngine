package owengine.model.entities.movement;

import java.awt.Point;

import owengine.model.map.MoveDir;

public interface Movable {

	boolean isInactive();
	Point getPos();
	void activateMovement(MoveDir dir);

}
