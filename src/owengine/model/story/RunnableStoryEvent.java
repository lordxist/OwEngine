package owengine.model.story;

public abstract class RunnableStoryEvent implements StoryEvent, Runnable {

	private EventMap map;
	private boolean paused;

	public RunnableStoryEvent(EventMap map) {
		this.map = map;
	}

	@Override
	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		runEvent();
		Event.finish(map);
	}

	public abstract void runEvent();

	/**
	 * Use this instead of StoryEvent.Event.pause(int)
	 */
	protected void pause(int millis) {
		synchronized (this) {
			while (paused) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Event.pause(millis);
	}

	@Override
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

}
