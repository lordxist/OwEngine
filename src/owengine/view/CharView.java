package owengine.view;

import owengine.model.entities.character.Char;
import owengine.model.entities.character.PlayerChar;
import owengine.view.PcActionView;
import owengine.view.PcStateView;

public class CharView implements View {

	protected static int fieldSize;

	public static void setFieldSize(int fieldSize) {
		CharView.fieldSize = fieldSize;
	}

	public static int getFieldSize() {
		return fieldSize;
	}

	protected Char character;
	private Char pc;
	protected PcActionView actionView;
	protected PcStateView stateView;

	public CharView(Char character, PcActionView actionView, PcStateView stateView) {
		this.character = character;
		this.pc = PlayerChar.getInstance();
		this.actionView = actionView;
		this.stateView = stateView;
	}

	public void draw(int compWidth, int compHeight) {
		if (!pc.getPos().getMap().equals(character.getPos().getMap())) return;
		if (character.isInactive()) {
			actionView.finishCurrentView();
			stateView.draw("stand_"+character.getPos().getDir(),
					compWidth/2-(getScreenX(pc)-getScreenX()),
					compHeight/2-(getScreenY(pc)-getScreenY()));
		} else {
			actionView.drawCurrentView(
				character.getActionType()+"_"+character.getPos().getDir(),
					compWidth/2-(getScreenX(pc)-getScreenX()),
					compHeight/2-(getScreenY(pc)-getScreenY()));
		}
	}

	public int getScreenX() {
		return getScreenX(character);
	}

	public int getScreenY() {
		return getScreenY(character);
	}

	public static int getScreenX(Char character) {
		return character.getPos().x*fieldSize+deltaX(character);
	}

	public static int getScreenY(Char character) {
		return character.getPos().y*fieldSize+deltaY(character);
	}

	private static int deltaX(Char character) {
		return deltaPos(character)*character.getPos().getDir().getX();
	}

	private static int deltaY(Char character) {
		return deltaPos(character)*character.getPos().getDir().getY();
	}

	private static int deltaPos(Char character) {
		if (!character.getPos().isMoving()) return 0;
		float duration = character.getActionDuration();
		return (int) ((character.getActionDelta()/duration) * fieldSize);
	}

}
