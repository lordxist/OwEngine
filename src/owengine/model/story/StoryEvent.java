package owengine.model.story;

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

}
