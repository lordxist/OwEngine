package owengine.ext.tiled;

import java.io.File;

import owengine.core.MapLoader;
import owengine.core.world.GameMap;
import tiled.core.Map;
import tiled.io.TMXMapReader;

public class TiledMapLoader extends MapLoader {

	@Override
	public void load(String mapName, GameMap map) {
		try {
			TMXMapReader reader = new TMXMapReader();
			Map tiledMap = reader.readMap("../res/maps/"+mapName+".tmx");
			((TiledGameMap) map).setTiledMap(tiledMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String[] getMapNames() {
		File[] files = new File("../res/maps").listFiles();
		String[] result = new String[files.length];
		int i = 0;
		for (File file : files) {
			String name = file.getName();
			result[i++] = name.split("\\.")[0];
		}
		return result;
	}

}
