package owengine.core;

import owengine.core.world.GameMap;

public interface MapLoader {

	void load(String mapName, GameMap gameMap);

	String[] getMapNames();

}
