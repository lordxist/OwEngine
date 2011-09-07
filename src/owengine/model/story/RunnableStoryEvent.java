package owengine.model.story;

public abstract class RunnableStoryEvent implements StoryEvent, Runnable {

	private EventMap map;

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

}
