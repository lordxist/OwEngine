package owengine.model.entities.character;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import owengine.controller.ControlledMovable;
import owengine.model.map.EntityMap;

public class NonPlayerChar extends Char implements ControlledMovable {

	private static HashMap<Integer, NonPlayerChar> instances =
		new HashMap<Integer, NonPlayerChar>();

	public static NonPlayerChar getById(int id) {
		return instances.get(id);
	}

	private ArrayList<Point> moves;
	private ArrayList<String> msg;

	public NonPlayerChar(int id, EntityMap map, int x, int y,
			ArrayList<String> msg) {
		super(map, x, y);
		this.msg = msg;
		setTurning(false);
		setBumping(false);
		instances.put(id, this);
	}

	public NonPlayerChar(int id, EntityMap map, int x, int y,
			ArrayList<String> msg, ArrayList<Point> moves) {
		this(id, map, x, y, msg);
		this.moves = moves;
	}

	@Override
	public ArrayList<Point> getMoves() {
		return moves;
	}

	public boolean hasAIMovement() {
		return moves != null;
	}

	@Override
	public ArrayList<String> explore() {
		if (isMoving()) return null;
		face(PlayerChar.getInstance());
		return msg;
	}

}
