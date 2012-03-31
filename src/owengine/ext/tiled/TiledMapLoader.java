package owengine.ext.tiled;

import java.awt.Point;
import java.io.File;
import java.util.Iterator;

import owengine.core.MapLoader;
import owengine.core.world.Entity;
import owengine.core.world.GameMap;
import owengine.core.world.Warp;
import owengine.core.world.World;
import owengine.ext.tiled.world.TiledGameMap;
import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.MapObject;
import tiled.core.ObjectGroup;
import tiled.io.TMXMapReader;

public class TiledMapLoader implements MapLoader {

	@Override
	public void load(String mapName, GameMap map) {
		try {
			TMXMapReader reader = new TMXMapReader();
			Map tiledMap = reader.readMap("../res/maps/"+mapName+".tmx");
			((TiledGameMap) map).setTiledMap(tiledMap);
			for (MapLayer layer : tiledMap.getLayers()) {
				if (layer instanceof ObjectGroup) {
					final ObjectGroup objectGroup = (ObjectGroup) layer;
					for (MapObject object : new Iterable<MapObject>() {
						@Override
						public Iterator<MapObject> iterator() {
							return objectGroup.getObjects();
						}
					}) {
						Point pos = new Point(
							object.getX()/tiledMap.getTileWidth(),
							object.getY()/tiledMap.getTileHeight()
						);
						if (object.getType().equals("Entity")) {
							map.addEntity(loadEntity(object));
						} else if (object.getType().equals("Warp")) {
							GameMap targetMap = World.getInstance().getMapByName(
									object.getProperties().getProperty("targetMap"));
							Point targetPos = new Point(
								Integer.parseInt(
										object.getProperties().getProperty("targetX")),
								Integer.parseInt(
										object.getProperties().getProperty("targetY"))
							);
							map.addWarp(pos, new Warp(targetMap, targetPos));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Entity loadEntity(MapObject object) {
		return new Entity(
				Integer.parseInt(object.getProperties().getProperty("id", "-1")));
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
