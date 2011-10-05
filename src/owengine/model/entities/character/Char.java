package owengine.model.entities.character;

import java.awt.Point;
import java.util.ArrayList;

import owengine.model.entities.movement.MobileEntity;
import owengine.model.map.EntityMap;
import owengine.model.map.MoveDir;

public abstract class Char extends MobileEntity {

	public static final int STD_MOVE_SPEED = 450;
	public static final MoveDir STD_MOVE_DIR = MoveDir.down;

	public Char() {
		super(STD_MOVE_DIR, STD_MOVE_SPEED);
	}

	public Char(EntityMap map, int x, int y) {
		super(new Point(x, y), map, STD_MOVE_DIR, STD_MOVE_SPEED);
	}

	@Override
	public ArrayList<String> explore() {
		return null;
	}

}
