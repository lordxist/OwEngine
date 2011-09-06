package owengine.model.action;

import java.util.ArrayList;

public class ActionQueue {

	private ArrayList<Action> actions = new ArrayList<Action>();

	public void add(Action a) {
		if (a != null) {
			actions.add(a);
		}
	}

	public Action currentAction() {
		return actions.size() > 0 ? actions.get(0) : NullAction.INSTANCE;
	}

	public boolean isEmpty() {
		return actions.isEmpty();
	}

	public void update(int delta) {
		Action currentAction = currentAction();
		if (!currentAction.isStarted()) {
			currentAction.start();
		}
		currentAction.update(delta);
		if (currentAction.isFinished()) {
			actions.remove(0);
		}
	}

}
