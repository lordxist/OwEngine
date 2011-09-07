package owengine.model.action;

public abstract class ActionUser {

	protected Action action = NullAction.INSTANCE;
	private boolean disabled;

	public void startAction(Action action) {
		if (isInactive() && !disabled) {
			this.action = action;
			this.action.init();
		}
	}

	public void update(int delta) {
		action.update(delta);
		if (action.isFinished()) {
			action = NullAction.INSTANCE;
		}
	}

	public ActionType getActionType() {
		return action.getType();
	}

	public int getActionDuration() {
		return action.getDuration();
	}

	public int getActionDelta() {
		return action.getDelta();
	}

	public boolean isInactive() {
		return (action instanceof NullAction);
	}

	public void disable() {
		disabled = true;
	}

	public void enable() {
		disabled = false;
	}

}
