package owengine.core.world;

import owengine.core.util.timed.ActionUser;

public abstract class StoryEvent implements Runnable {

	public static final StoryEvent NULL_EVENT = new StoryEvent() {
		@Override
		public void runEvent() {}
		
		@Override
		public boolean isFinished() {
			return true;
		}
	};

	private boolean finished;

	@Override
	public void run() {
		World.getInstance().getInputController().disable();
		finished = false;
		runEvent();
		finished = true;
		World.getInstance().getInputController().enable();
	}

	/**
	 * Here goes what happens during the event.
	 */
	public abstract void runEvent();

	public boolean isFinished() {
		return finished;
	}

	protected void pauseForAction(ActionUser actionUser) {
		while (!actionUser.isActionFinished()) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
		}
	}

	/**
	 * Convenience method to wait for player's action. Calls
	 * <p>
	 * <code>
	 * 	pauseForAction(World.getInstance().getPlayer());
	 * </code>
	 * </p>
	 */
	protected void pauseForAction() {
		pauseForAction(World.getInstance().getPlayer());
	}

}
