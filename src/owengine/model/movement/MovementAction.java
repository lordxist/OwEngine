package owengine.model.movement;

import owengine.model.util.action.Action;
import owengine.model.util.action.ActionType;

class MovementAction extends Action {

	interface Movable {
	
		void finishMove();
	
	}

	static final MovementAction NULL = new MovementAction(null, 0) {
		@Override
		public boolean isFinished() {
			return true;
		}
		
		@Override
		public boolean hasType(ActionType type) {
			return false;
		}
	};

	static MovementAction createBumpAction(int moveSpeed) {
		return new MovementAction(MovementActionType.bump, moveSpeed);
	}

	static MovementAction createTurnAction(int moveSpeed) {
		return new MovementAction(MovementActionType.turn, moveSpeed/2);
	}

	static MovementAction createMoveAction(int moveSpeed, Movable mov) {
		final class MoveAction extends MovementAction {
			
			private final Movable mov;

			private MoveAction(int moveSpeed, Movable mov) {
				super(MovementActionType.move, moveSpeed);
				this.mov = mov;
			}

			@Override
			protected void finish() {
				mov.finishMove();
			}

		}
		
		return new MoveAction(moveSpeed, mov);
	}

	private MovementAction(MovementActionType type, int duration) {
		super(type, duration);
	}

}
