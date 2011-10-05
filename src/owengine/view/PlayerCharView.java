package owengine.view;

import owengine.model.entities.character.Char;
import owengine.view.PcActionView;
import owengine.view.PcStateView;

public class PlayerCharView extends CharView {

	private int compWidth, compHeight;

	public PlayerCharView(Char pc, PcActionView actionView, PcStateView stateView,
			int compWidth, int compHeight) {
		super(pc, actionView, stateView);
		this.compWidth = compWidth;
		this.compHeight = compHeight;
	}

	public void draw() {
		if (character.isInactive()) {
			actionView.finishCurrentView();
			stateView.draw("stand_"+character.getPos().getDir(),
					compWidth/2, compHeight/2);
		} else {
			actionView.drawCurrentView(
				character.getActionType()+"_"+character.getPos().getDir(),
				compWidth/2, compHeight/2);
		}
	}

}
