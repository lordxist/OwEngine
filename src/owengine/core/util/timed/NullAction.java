package owengine.core.util.timed;

public class NullAction extends TimedAction {

	public NullAction() {
		super(null, 0);
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
