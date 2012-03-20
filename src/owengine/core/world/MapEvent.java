package owengine.core.world;

public abstract class MapEvent extends StoryEvent {

	private GameMap map;

	public MapEvent(String name) {
		super(name);
	}

	void setMap(GameMap map) {
		this.map = map;
	}

	@Override
	public void runEvent() {
		while (map.getEntities().contains(World.getInstance().getPlayer())) {
			runRepetitiveEvent();
		}
	}

	protected abstract void runRepetitiveEvent();

}
