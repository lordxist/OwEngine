package owengine.core.util.timed;

public abstract class TimedAction {

	public static final TimedAction NULL_ACTION = new NullAction();

	protected String name;
	protected int time;
	protected int duration;
	protected boolean finished;

	public TimedAction(String name, int duration) {
		this.name = name;
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

	public String getName() {
		return name;
	}

	public int getTime() {
		return time;
	}

	public int getDuration() {
		return duration;
	}

	public float getDelta() {
		return ((float)time)/duration;
	}

	public boolean isFinished() {
		return finished;
	}

	protected abstract void finish();

}
