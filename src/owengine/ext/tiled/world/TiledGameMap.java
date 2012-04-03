package owengine.ext.tiled.world;

import java.awt.Point;

import owengine.core.world.GameMap;
import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.TileLayer;

public class TiledGameMap extends GameMap {

	private Map tiledMap;

	public Map getTiledMap() {
		return tiledMap;
	}

	public void setTiledMap(Map tiledMap) {
		this.tiledMap = tiledMap;
	}

	@Override
	public boolean isBlocked(Point position) {
		for (MapLayer layer : tiledMap.getLayers()) {
			if (layer instanceof TileLayer
					&& layer.getProperties().getProperty("blocking", "false").equals("true")
					&& ((TileLayer) layer).getTileAt(position.x, position.y) != null
					&& ((TileLayer) layer).getTileAt(position.x, position.y).getId() != -1) {
				return true;
			}
		}
		return super.isBlocked(position);
	}

}
