package owengine.model.story;

public interface StoryEvent extends Runnable {

	class Event {
	
		public static int TOLERANCE_INTERVAL = 20;
	
		public static void pause(int millis) {
			try {
				Thread.sleep(millis+TOLERANCE_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}

}
