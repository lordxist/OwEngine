package owengine.core.util.timed;

class NullAction extends TimedAction {

	public NullAction() {
		super(0);
	}

	@Override
	public void updateAction(float delta) {}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void finish() {}

}
