package owengine.model.movement;

import owengine.model.map.MoveDir;

public interface Directed {

	MoveDir getDir();

	void setDir(MoveDir dir);

}
