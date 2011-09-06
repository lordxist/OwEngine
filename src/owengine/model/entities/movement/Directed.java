package owengine.model.entities.movement;

import owengine.model.map.MoveDir;

public interface Directed {

	MoveDir getDir();

	void setDir(MoveDir dir);

}
