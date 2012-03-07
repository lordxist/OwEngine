package owengine.ext.tiled;

import owengine.core.MapLoader;
import owengine.core.world.GameMap;
import tiled.core.Map;
import tiled.io.TMXMapReader;

public class TiledMapLoader implements MapLoader {

	@Override
	public void load(String mapName, GameMap map) {
		try {
			TMXMapReader reader = new TMXMapReader();
			Map tiledMap = reader.readMap("../res/"+mapName+".tmx");
			((TiledGameMap) map).setTiledMap(tiledMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String[] getMapNames() {
		return new String[]{"ashs_house_1"};
	}

}
