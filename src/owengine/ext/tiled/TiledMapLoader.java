package owengine.ext.tiled;

import java.io.File;

import owengine.core.MapLoader;
import owengine.core.world.GameMap;
import owengine.ext.tiled.world.TiledGameMap;
import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.TileLayer;
import tiled.io.TMXMapReader;

public class TiledMapLoader extends MapLoader {

	@Override
	public void load(String mapName, GameMap map) {
		try {
			TMXMapReader reader = new TMXMapReader();
			Map tiledMap = reader.readMap("../res/maps/"+mapName+".tmx");
			((TiledGameMap) map).setTiledMap(tiledMap);
			for (MapLayer layer : tiledMap.getLayers()) {
				if (layer instanceof TileLayer) {
					TileLayer tileLayer = (TileLayer) layer;
					for (int x = 0; x < tileLayer.getWidth(); x++) {
						for (int y = 0; y < tileLayer.getHeight(); y++) {
							tileLayer.getTileAt(x, y).getImage();
						}
					} 
				}
			}
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
