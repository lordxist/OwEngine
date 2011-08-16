package owengine.model.story;

public abstract class QueueEvent implements StoryEvent {

	private EventActionQueue queue;

	public QueueEvent(int id, boolean active) {}

	public <T> T sendToEntity(Object entity, String method, Object... args) {
		return queue.sendToEntity(entity, method, args);
	}

	@Override
	public void activate() {}

	@Override
	public void setQueue(EventActionQueue queue) {
		this.queue = queue;
	}

	@Override
	public void update(int delta) {
		queue.update(delta);
	}

}
