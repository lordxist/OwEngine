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
					&& layer.getProperties().getProperty("blocking", "false").equals(true)) {
				return true;
			}
		}
		return super.isBlocked(position);
	}

	@Override
	public int getTileId(String layer, Point pos) {
		return getTileLayer(layer).getTileAt(pos.x, pos.y).getId();
	}

	@Override
	public void setTileId(String layer, Point pos, int id) {
		getTileLayer(layer).getTileAt(pos.x, pos.y).setId(id);
	}

	public TileLayer getTileLayer(String name) {
		for (MapLayer layer : tiledMap.getLayers()) {
			if (layer instanceof TileLayer && layer.getName().equals(name)) {
				return (TileLayer) layer;
			}
		}
		return null;
	}

}
