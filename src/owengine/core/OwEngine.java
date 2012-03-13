package owengine.core;

import javax.vecmath.Vector2f;

import owengine.core.world.Entity;
import owengine.core.world.GameMap;
import owengine.core.world.MapRenderComponent;
import owengine.core.world.RenderComponent;
import owengine.core.world.World;
import owengine.core.world.impl.render.OrthogonalCenteredMapRenderComponent;
import owengine.core.world.impl.render.OrthogonalCenteredRenderComponent;

public class OwEngine {

	public interface RenderFactory {
		RenderComponent newRenderComponent();
	}

	public interface MapRenderFactory {
		MapRenderComponent newMapRenderComponent();
	}

	public interface MapFactory {
		GameMap newGameMap();
	}

	private int fieldSize;
	private int width;
	private int height;
	private Vector2f center;
	private RenderFactory renderFactory;
	private MapRenderFactory mapRenderFactory;
	private String startMapName;
	private MapFactory mapFactory;
	private MapLoader mapLoader;

	public OwEngine(int fieldSize, int width, int height, Vector2f center, Entity player, RenderFactory renderFactory, MapRenderFactory mapRenderFactory, MapFactory mapFactory) {
		this.fieldSize = fieldSize;
		this.width = width;
		this.height = height;
		this.center = center;
		this.renderFactory = renderFactory;
		this.mapRenderFactory = mapRenderFactory;
		this.mapFactory = mapFactory;
		addPlayer(player);
	}

	private void addPlayer(Entity player) {
		RenderComponent renderComponent = newRenderComponent();
		player.setRenderComponent(renderComponent);
		World.getInstance().setPlayer(player);
	}

	private RenderComponent newRenderComponent() {
		RenderComponent renderComponent = renderFactory.newRenderComponent();
		if (renderComponent instanceof OrthogonalCenteredRenderComponent) {
			OrthogonalCenteredRenderComponent ocRenderComponent =
				(OrthogonalCenteredRenderComponent) renderComponent;
			ocRenderComponent.setCenter(center);
			ocRenderComponent.setFieldSize(fieldSize);
			ocRenderComponent.setCenterEntity(World.getInstance().getPlayer());
		}
		return renderComponent;
	}

	public void setMapLoader(MapLoader mapLoader) {
		this.mapLoader = mapLoader;
	}

	public void setStartMap(String startMapName) {
		this.startMapName = startMapName;
	}

	public void load() {
		String[] mapNames = mapLoader.getMapNames();
		for (String mapName : mapNames) {
			GameMap map = mapFactory.newGameMap();
			mapLoader.load(mapName, map);
			map.setRenderComponent(newMapRenderComponent());
			World.getInstance().addMapWithName(mapName, map);
		}
		
		Entity player = World.getInstance().getPlayer();
		GameMap startMap = World.getInstance().getMapByName(startMapName);
		startMap.addEntity(player);
		
		for (GameMap map : World.getInstance().getMaps()) {
			for (Entity entity : map.getEntities()) {
				entity.setRenderComponent(newRenderComponent());
			}
		}
	}

	private MapRenderComponent newMapRenderComponent() {
		MapRenderComponent renderComponent = mapRenderFactory.newMapRenderComponent();
		if (renderComponent instanceof OrthogonalCenteredMapRenderComponent) {
			OrthogonalCenteredMapRenderComponent ocRenderComponent =
				(OrthogonalCenteredMapRenderComponent) renderComponent;
			ocRenderComponent.setFieldSize(fieldSize);
			ocRenderComponent.setHeight(height);
			ocRenderComponent.setWidth(width);
		}
		return renderComponent;
	}

}
