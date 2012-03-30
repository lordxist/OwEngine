package owengine.core.world;

import java.util.HashMap;

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

	private static HashMap<String, StoryEvent> events = new HashMap<String, StoryEvent>();

	public static StoryEvent getEvent(String name) {
		return events.get(name);
	}

	private StoryEvent() {}

	public StoryEvent(String name) {
		events.put(name, this);
		this.name = name;
	}

	private String name;
	private boolean finished;
	private GameMap map;

	void setMap(GameMap map) {
		this.map = map;
	}

	@Override
	public synchronized void run() {
		finished = false;
		runEvent();
		finished = true;
		World.getInstance().getPlayer().getController().enable();
	}

	/**
	 * Here goes what happens during the event.
	 */
	public abstract void runEvent();

	public String getName() {
		return name;
	}

	public boolean isFinished() {
		return finished;
	}

	public GameMap getMap() {
		return map;
	}

	protected void pauseForAction(ActionUser actionUser) {
		while (!actionUser.isActionFinished()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
