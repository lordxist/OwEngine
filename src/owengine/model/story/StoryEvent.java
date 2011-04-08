package owengine.model.story;

public interface StoryEvent {

	void activate();

	void update(int delta);

	void run();

}
