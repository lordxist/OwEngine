package owengine.view;

import owengine.model.movement.MovablePosition;

public class PlayerCharView extends CharView {

	public PlayerCharView(MovablePosition<?> pc, PcActionView actionView, PcStateView stateView,
			int fieldSize) {
		super(pc, actionView, stateView, fieldSize, pc);
	}

	public void draw(int compWidth, int compHeight) {
		if (character.getAction().isFinished()) {
			actionView.finishCurrentView();
			stateView.draw("stand_"+character.getDir(),
					compWidth/2, compHeight/2);
		} else actionView.drawCurrentView(
				character.getAction().getType()+"_"+character.getDir(),
				compWidth/2, compHeight/2);
	}

}
