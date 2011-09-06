package owengine.model.story;

public abstract class RunnableStoryEvent implements StoryEvent, Runnable {

	@Override
	public void start() {
		new Thread(this).start();
	}

}
