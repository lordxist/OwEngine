package owengine.model.action;

public class Action {

	private ActionType type;
	private int delta, duration;
	private boolean finished;
	private boolean started;

	public Action(ActionType type, int duration) {
		this.type = type;
		this.duration = duration;
	}

	public void init() {
		started = true;
		start();
	}

	protected void start() {}

	public void update(int delta) {
		if (isFinished()) return;
		this.delta += delta;
		if (this.delta >= duration) {
			this.delta = 0;
			this.finished = true;
			finish();
		}
	}

	public int getDelta() {
		return delta;
	}

	public int getDuration() {
		return duration;
	}

	public boolean isFinished() {
		return finished;
	}

	public ActionType getType() {
		return type;
	}

	public boolean hasType(ActionType type) {
		return this.type.equals(type);
	}

	protected void finish() {}

	public boolean isStarted() {
		return started;
	}

}
