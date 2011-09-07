package owengine.view;

import owengine.model.entities.character.Char;
import owengine.view.PcActionView;
import owengine.view.PcStateView;

public class PlayerCharView extends CharView {

	public PlayerCharView(Char pc, PcActionView actionView, PcStateView stateView,
			int fieldSize) {
		super(pc, actionView, stateView, fieldSize, pc);
	}

	public void draw(int compWidth, int compHeight) {
		if (character.isInactive()) {
			actionView.finishCurrentView();
			stateView.draw("stand_"+character.getPos().getDir(),
					compWidth/2, compHeight/2);
		} else actionView.drawCurrentView(
				character.getActionType()+"_"+character.getPos().getDir(),
				compWidth/2, compHeight/2);
	}

}
