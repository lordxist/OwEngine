package owengine.ext.tiled.world.impl.render;

import java.awt.Graphics;
import java.awt.Graphics2D;

import owengine.core.world.MapRenderer;
import owengine.ext.tiled.world.TiledGameMap;
import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.TileLayer;

public class SimpleTiledMapRenderer extends MapRenderer {

	static void render(MapRenderer renderer, Graphics g) {
		TiledGameMap tiledGameMap = (TiledGameMap) renderer.getMap();
		Map tiledMap = tiledGameMap.getTiledMap();
			
		tiled.view.MapRenderer tiledRenderer = new tiled.view.OrthogonalRenderer(tiledMap);
		g = g.create(renderer.getX(), renderer.getY(), renderer.getWidth(), renderer.getHeight());
		for (MapLayer layer : tiledMap.getLayers()) {
			if (layer instanceof TileLayer &&
					!(layer.getProperties().containsKey("render") &&
						layer.getProperties().get("render").equals("false"))) {
				tiledRenderer.paintTileLayer((Graphics2D) g, (TileLayer) layer);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		render(this, g);
	}

}
