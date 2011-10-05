package owengine.model.entities.character;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import owengine.controller.ControlledMovable;

public class NonPlayerChar extends Char implements ControlledMovable {

	private static HashMap<Integer, NonPlayerChar> instances =
		new HashMap<Integer, NonPlayerChar>();

	public static NonPlayerChar getById(int id) {
		return instances.get(id);
	}

	private ArrayList<Point> moves;
	private ArrayList<String> msg;

	public NonPlayerChar(int id, ArrayList<String> msg, ArrayList<Point> moves) {
		this(id, msg);
		this.moves = moves;
	}

	public NonPlayerChar(int id, ArrayList<String> msg) {
		super();
		this.msg = msg;
		setTurning(false);
		setBumping(false);
		instances.put(id, this);
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
