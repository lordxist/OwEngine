package owengine.ext.tiled;

import owengine.core.OwEngine;

public class TiledExtension {

	public static OwEngine createEngine() {
		OwEngine engine = new OwEngine();
		engine.setMapRenderClass(TiledMapRenderComponent.class);
		engine.setMapClass(TiledGameMap.class);
		engine.setMapLoader(new TiledMapLoader());
		return engine;
	}

	private TiledExtension() {}

}
