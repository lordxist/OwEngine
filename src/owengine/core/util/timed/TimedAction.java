package owengine.core.util.timed;

public class TimedAction {

	public static final TimedAction NULL_ACTION = new TimedAction(null, 0) {
		@Override
		public boolean isFinished() {
			return true;
		}
	};

	protected String name;
	protected int time;
	protected int duration;
	protected boolean finished;
	private boolean started;

	public TimedAction(String name, int duration) {
		this.name = name;
		this.duration = duration;
	}

	public void start() {
		started = true;
	}

	public void update(int delta) {
		if ((!started) || finished) {
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

	public void updateAction(float delta) {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTime() {
		return time;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public float getDelta() {
		return ((float)time)/duration;
	}

	public boolean isFinished() {
		return finished;
	}

	protected void finish() {}

	@Override
	public String toString() {
		return name;
	}

}
