package owengine.ext.tiled;

import java.awt.Graphics;
import java.awt.Graphics2D;

import owengine.core.world.Entity;
import owengine.core.world.MapRenderComponent;
import owengine.core.world.World;
import tiled.core.Map;
import tiled.core.TileLayer;
import tiled.view.MapRenderer;
import tiled.view.OrthogonalRenderer;

public class TiledMapRenderComponent extends MapRenderComponent {

	@Override
	public void paint(Graphics g) {
		TiledGameMap tiledGameMap = (TiledGameMap) map;
		Map tiledMap = tiledGameMap.getTiledMap();
		
		MapRenderer renderer = new OrthogonalRenderer(tiledMap);
		Entity player = World.getInstance().getPlayer();
		g = g.create((int)(-player.getXf()*getFieldSize()),
				(int)(-player.getYf()*getFieldSize()),
				getWidth(), getHeight());
		renderer.paintTileLayer((Graphics2D) g, (TileLayer) tiledMap.getLayer(0));
	}

}
