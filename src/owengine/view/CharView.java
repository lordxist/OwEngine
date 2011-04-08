package owengine.view;

import owengine.model.movement.MovablePosition;

public class CharView {

	protected MovablePosition<?> character;
	private MovablePosition<?> pc;
	protected PcActionView actionView;
	protected PcStateView stateView;
	protected int fieldSize;

	public CharView(MovablePosition<?> character, PcActionView actionView, PcStateView stateView,
			int fieldSize, MovablePosition<?> pc) {
		this.character = character;
		this.pc = pc;
		this.actionView = actionView;
		this.stateView = stateView;
		this.fieldSize = fieldSize;
	}

	public void draw(int compWidth, int compHeight) {
		if (!pc.getMap().equals(character.getMap())) return;
		if (character.getAction().isFinished()) {
			actionView.finishCurrentView();
			stateView.draw("stand_"+character.getDir(),
					compWidth/2-(getScreenX(pc)-getScreenX()),
					compHeight/2-(getScreenY(pc)-getScreenY()));
		} else actionView.drawCurrentView(
				character.getAction().getType()+"_"+character.getDir(),
					compWidth/2-(getScreenX(pc)-getScreenX()),
					compHeight/2-(getScreenY(pc)-getScreenY()));
	}

	public int getScreenX() {
		return getScreenX(character);
	}

	public int getScreenY() {
		return getScreenY(character);
	}

	private int getScreenX(MovablePosition<?> character) {
		return character.x*fieldSize+deltaX(character);
	}

	private int getScreenY(MovablePosition<?> character) {
		return character.y*fieldSize+deltaY(character);
	}

	private int deltaX(MovablePosition<?> character) {
		return deltaPos(character)*character.getDir().getX();
	}

	private int deltaY(MovablePosition<?> character) {
		return deltaPos(character)*character.getDir().getY();
	}

	private int deltaPos(MovablePosition<?> character) {
		if (!character.isMoving()) return 0;
		float duration = character.getAction().getDuration();
		return (int) ((character.getAction().getDelta()/duration) * fieldSize);
	}

}
