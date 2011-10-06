package owengine.ext.slick;

import owengine.model.story.EventMap;
import owengine.model.story.RunnableStoryEvent;

public abstract class InputBlockingEvent extends RunnableStoryEvent {

	private static boolean inputDisabled;

	public InputBlockingEvent(EventMap map) {
		super(map);
	}

	@Override
	public void start() {
		inputDisabled = true;
		super.start();
	}

	@Override
	public void run() {
		super.run();
		inputDisabled = false;
	}

	public static boolean isInputDisabled() {
		return inputDisabled;
	}

}
