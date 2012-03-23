package owengine.ext.tiled.world.impl.render;

import java.awt.Graphics;
import java.awt.Graphics2D;

import owengine.core.world.MapRenderer;
import owengine.ext.tiled.world.TiledGameMap;
import tiled.core.Map;
import tiled.core.TileLayer;

public class SimpleTiledMapRenderer extends MapRenderer {

	static void render(MapRenderer renderer, Graphics g) {
		TiledGameMap tiledGameMap = (TiledGameMap) renderer.getMap();
		Map tiledMap = tiledGameMap.getTiledMap();
			
		tiled.view.MapRenderer tiledRenderer = new tiled.view.OrthogonalRenderer(tiledMap);
		g = g.create(renderer.getX(), renderer.getY(), renderer.getWidth(), renderer.getHeight());
		tiledRenderer.paintTileLayer((Graphics2D) g, (TileLayer) tiledMap.getLayer(0));
	}

	@Override
	public void paint(Graphics g) {
		render(this, g);
	}

}
