package owengine.ext.tiled;

import java.awt.Graphics;
import java.awt.Graphics2D;

import owengine.core.world.impl.render.OrthogonalCenteredMapRenderComponent;
import tiled.core.Map;
import tiled.core.TileLayer;
import tiled.view.MapRenderer;
import tiled.view.OrthogonalRenderer;

public class TiledMapRenderComponent extends OrthogonalCenteredMapRenderComponent {

	@Override
	public void paint(Graphics g) {
		TiledGameMap tiledGameMap = (TiledGameMap) map;
		Map tiledMap = tiledGameMap.getTiledMap();
		
		MapRenderer renderer = new OrthogonalRenderer(tiledMap);
		g = g.create(getX(), getY(), getWidth(), getHeight());
		renderer.paintTileLayer((Graphics2D) g, (TileLayer) tiledMap.getLayer(0));
	}

}
