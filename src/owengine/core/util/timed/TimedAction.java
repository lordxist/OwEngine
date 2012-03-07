package owengine.core.util.timed;

public abstract class TimedAction {

	public static final TimedAction NULL_ACTION = new NullAction();

	protected int time;
	protected int duration;
	protected boolean finished;

	public TimedAction(int duration) {
		this.duration = duration;
	}

	public void update(int delta) {
		if (finished) {
			return;
		}
		time += delta;
		if (time >= duration) {
			time = duration;
			finished = true;
		}
		updateAction(((float)time)/duration);
		if (time >= duration) {
			finish();
		}
	}

	public abstract void updateAction(float delta);

	public boolean isFinished() {
		return finished;
	}

	protected abstract void finish();

}
