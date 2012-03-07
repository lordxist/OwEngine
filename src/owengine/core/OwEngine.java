package owengine.core;

import javax.vecmath.Vector2f;

import owengine.core.render.CenterPositionRenderHelper;
import owengine.core.render.CenteredPositionRenderHelper;
import owengine.core.world.Entity;
import owengine.core.world.GameMap;
import owengine.core.world.MapRenderComponent;
import owengine.core.world.MapRenderHelper;
import owengine.core.world.RenderComponent;
import owengine.core.world.World;

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
		RenderComponent renderComponent = renderFactory.newRenderComponent();
		renderComponent.setRenderHelper(
			new CenterPositionRenderHelper(fieldSize, center));
		player.setRenderComponent(renderComponent);
		World.getInstance().setPlayer(player);
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
			World.getInstance().addMapWithName(mapName, map);
		}
		
		GameMap startMap = World.getInstance().getMapByName(startMapName);
		startMap.addEntity(World.getInstance().getPlayer());
		MapRenderComponent renderComponent = mapRenderFactory.newMapRenderComponent();
		renderComponent.setRenderHelper(new MapRenderHelper(fieldSize, width, height));
		startMap.setRenderComponent(renderComponent);
		
		for (GameMap map : World.getInstance().getMaps()) {
			for (Entity entity : map.getEntities()) {
				setupEntityRenderComponent(entity);
			}
		}
	}

	private void setupEntityRenderComponent(Entity entity) {
		RenderComponent renderComponent = renderFactory.newRenderComponent();
		renderComponent.setRenderHelper(
			new CenteredPositionRenderHelper(fieldSize, World.getInstance().getPlayer(), center));
		entity.setRenderComponent(renderComponent);
	}

}
