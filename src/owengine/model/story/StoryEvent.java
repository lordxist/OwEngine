package owengine.model.story;

import java.util.ArrayList;

public interface StoryEvent {

	class Event {
	
		public static int TOLERANCE_INTERVAL = 20;
	
		private Event() {}
	
		public static void pauseWithException(int millis) throws InterruptedException {
			Thread.sleep(millis+TOLERANCE_INTERVAL);
		}

		public static void pause(int millis) {
			try {
				pauseWithException(millis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * In implementations, this must be called at the end of
		 * the event's lifetime with the event's map as the argument.
		 */
		public static void finish(EventMap map) {
			map.eventFinished();
		}
	
	}

	/**
	 * Starts the event.
	 * @see StoryEvent.Event.finish(EventMap)
	 */
	void start();

	void setPaused(boolean paused);

	/**
	 * <p>Messages the player character by calling</p>
	 * <code>PlayerChar.getInstance().setCurrentMessage(msg)</code>
	 * <p>in implementations, then pauses the event.</p>
	 * @see PlayerChar.removeCurrentMessage()
	 */
	void message(ArrayList<String> msg);

}
