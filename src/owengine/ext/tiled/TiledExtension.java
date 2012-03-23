package owengine.ext.tiled;

import owengine.core.OwEngine;
import owengine.core.world.MapRenderer;
import owengine.ext.tiled.world.TiledGameMap;
import owengine.ext.tiled.world.impl.render.CenteredTiledMapRenderer;
import owengine.ext.tiled.world.impl.render.SimpleTiledMapRenderer;

public class TiledExtension {

	public static OwEngine createEngine() {
		return createEngine(true);
	}

	public static OwEngine createEngine(boolean centered) {
		Class<? extends MapRenderer> renderClass;
		if (centered) {
			renderClass = CenteredTiledMapRenderer.class;
		} else {
			renderClass = SimpleTiledMapRenderer.class;
		}
		
		OwEngine engine = new OwEngine();
		engine.setMapRenderClass(renderClass);
		engine.setMapClass(TiledGameMap.class);
		engine.setMapLoader(new TiledMapLoader());
		return engine;
	}

	private TiledExtension() {}

}
