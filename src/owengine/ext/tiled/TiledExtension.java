package owengine.ext.tiled;

import javax.vecmath.Vector2f;

import owengine.core.OwEngine;
import owengine.core.OwEngine.MapFactory;
import owengine.core.OwEngine.MapRenderFactory;
import owengine.core.OwEngine.RenderFactory;
import owengine.core.world.Entity;
import owengine.core.world.GameMap;
import owengine.core.world.MapRenderComponent;

public class TiledExtension {

	public static OwEngine createEngine(int fieldSize, int width, int height,
			Vector2f center, Entity player, RenderFactory renderFactory) {
		MapRenderFactory mapRenderFactory = new MapRenderFactory() {
			@Override
			public MapRenderComponent newMapRenderComponent() {
				return new TiledMapRenderComponent();
			}
		};
		MapFactory mapFactory = new MapFactory() {
			@Override
			public GameMap newGameMap() {
				return new TiledGameMap();
			}
		};
		OwEngine engine = new OwEngine(fieldSize, width, height, center, player, renderFactory, mapRenderFactory, mapFactory);
		engine.setMapLoader(new TiledMapLoader());
		return engine;
	}

	private TiledExtension() {}

}
