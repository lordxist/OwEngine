package owengine.model.movement;

import owengine.model.util.action.ActionType;

public enum MovementActionType implements ActionType {

	/**
	 * When sth. is moving against a blocked tile.
	 */
	bump,

	/**
	 * When sth. is moving from one tile to another.
	 */
	move,

	/**
	 * When sth. is in the process of turning.
	 */
	turn,

}
