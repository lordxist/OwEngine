package owengine.core;

import owengine.core.world.GameMap;

public abstract class MapLoader {

	public abstract void load(String mapName, GameMap gameMap);

	public abstract String[] getMapNames();

	

}
