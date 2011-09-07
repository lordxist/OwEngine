package owengine.view;

import owengine.model.entities.character.Char;
import owengine.view.PcActionView;
import owengine.view.PcStateView;

public class CharView {

	protected Char character;
	private Char pc;
	protected PcActionView actionView;
	protected PcStateView stateView;
	protected int fieldSize;

	public CharView(Char character, PcActionView actionView, PcStateView stateView,
			int fieldSize, Char pc) {
		this.character = character;
		this.pc = pc;
		this.actionView = actionView;
		this.stateView = stateView;
		this.fieldSize = fieldSize;
	}

	public void draw(int compWidth, int compHeight) {
		if (!pc.getPos().getMap().equals(character.getPos().getMap())) return;
		if (character.isInactive()) {
			actionView.finishCurrentView();
			stateView.draw("stand_"+character.getPos().getDir(),
					compWidth/2-(getScreenX(pc)-getScreenX()),
					compHeight/2-(getScreenY(pc)-getScreenY()));
		} else actionView.drawCurrentView(
				character.getActionType()+"_"+character.getPos().getDir(),
					compWidth/2-(getScreenX(pc)-getScreenX()),
					compHeight/2-(getScreenY(pc)-getScreenY()));
	}

	public int getScreenX() {
		return getScreenX(character);
	}

	public int getScreenY() {
		return getScreenY(character);
	}

	private int getScreenX(Char character) {
		return character.getPos().x*fieldSize+deltaX(character);
	}

	private int getScreenY(Char character) {
		return character.getPos().y*fieldSize+deltaY(character);
	}

	private int deltaX(Char character) {
		return deltaPos(character)*character.getPos().getDir().getX();
	}

	private int deltaY(Char character) {
		return deltaPos(character)*character.getPos().getDir().getY();
	}

	private int deltaPos(Char character) {
		if (!character.getPos().isMoving()) return 0;
		float duration = character.getActionDuration();
		return (int) ((character.getActionDelta()/duration) * fieldSize);
	}

}
