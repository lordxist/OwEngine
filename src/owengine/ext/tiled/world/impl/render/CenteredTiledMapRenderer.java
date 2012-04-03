package owengine.ext.tiled.world.impl.render;

import java.awt.Graphics;

import owengine.core.world.MapRenderer;

public class CenteredTiledMapRenderer extends MapRenderer.CenteredRenderer {

	@Override
	protected void paintTiles(Graphics g) {
		SimpleTiledMapRenderer.render(this, g);
	}

}
