package owengine.ext.tiled.world;

import owengine.core.world.GameMap;
import tiled.core.Map;

public class TiledGameMap extends GameMap {

	private Map tiledMap;

	public Map getTiledMap() {
		return tiledMap;
	}

	public void setTiledMap(Map tiledMap) {
		this.tiledMap = tiledMap;
	}

}
