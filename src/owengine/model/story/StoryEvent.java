package owengine.model.story;

public interface StoryEvent {

	StoryEvent NULL = new StoryEvent() {
		@Override
		public void start() {}
	};

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
	
	}

	void start();

}
