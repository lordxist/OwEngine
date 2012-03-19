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

	public interface Factory<T> {
	
		T createNew();
	
	}

	public static class Producer<T> implements Factory<T> {
			
		public static class IllegalProducerClassException extends RuntimeException {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5633555190374897326L;

		}
		
		private Class<? extends T> clazz;
		
		public Producer(Class<? extends T> clazz) {
			this.clazz = clazz;
		}
		
		@Override
		public T createNew() {
			try {
				return clazz.newInstance();
			} catch (InstantiationException e) {
				throw new IllegalProducerClassException();
			} catch (IllegalAccessException e) {
				throw new IllegalProducerClassException();
			}
		}
	
	}

	private int fieldSize;
	private int width;
	private int height;
	private Vector2f center;
	private String startMapName;
	private MapLoader mapLoader;
	private Class<? extends RenderComponent> renderClass;
	private Class<? extends MapRenderComponent> mapRenderClass;
	private Class<? extends GameMap> mapClass = GameMap.class;
	private Factory<RenderComponent> renderFactory =
		new Producer<RenderComponent>(renderClass);
	private Factory<MapRenderComponent> mapRenderFactory =
		new Producer<MapRenderComponent>(mapRenderClass);
	private Factory<GameMap> mapFactory = new Producer<GameMap>(mapClass);
	private World world = World.getInstance();

	public void loadMaps() {
		String[] mapNames = mapLoader.getMapNames();
		for (String mapName : mapNames) {
			GameMap map = mapFactory.createNew();
			mapLoader.load(mapName, map);
			map.setRenderComponent(newMapRenderComponent());
			world.addMapWithName(mapName, map);
		}
		
		Entity player = world.getPlayer();
		GameMap startMap = world.getMapByName(startMapName);
		startMap.addEntity(player);
		
		for (GameMap map : world.getMaps()) {
			for (Entity entity : map.getEntities()) {
				entity.setRenderComponent(newRenderComponent());
			}
		}
	}

	private RenderComponent newRenderComponent() {
		RenderComponent renderComponent = renderFactory.createNew();
		if (renderComponent instanceof OrthogonalCenteredRenderComponent) {
			OrthogonalCenteredRenderComponent ocRenderComponent =
				(OrthogonalCenteredRenderComponent) renderComponent;
			ocRenderComponent.setCenter(center);
			ocRenderComponent.setFieldSize(fieldSize);
			ocRenderComponent.setCenterEntity(world.getPlayer());
		}
		return renderComponent;
	}

	private MapRenderComponent newMapRenderComponent() {
		MapRenderComponent renderComponent = mapRenderFactory.createNew();
		if (renderComponent instanceof OrthogonalCenteredMapRenderComponent) {
			OrthogonalCenteredMapRenderComponent ocRenderComponent =
				(OrthogonalCenteredMapRenderComponent) renderComponent;
			ocRenderComponent.setFieldSize(fieldSize);
			ocRenderComponent.setHeight(height);
			ocRenderComponent.setWidth(width);
		}
		return renderComponent;
	}

	public void addEntity(Entity entity, GameMap map) {
		map.addEntity(entity);
		entity.setRenderComponent(newRenderComponent());
	}

	public Class<? extends RenderComponent> getRenderClass() {
		return renderClass;
	}

	public void setRenderClass(Class<? extends RenderComponent> renderClass) {
		this.renderClass = renderClass;
	}

	public Class<? extends MapRenderComponent> getMapRenderClass() {
		return mapRenderClass;
	}

	public void setMapRenderClass(Class<? extends MapRenderComponent> mapRenderClass) {
		this.mapRenderClass = mapRenderClass;
	}

	public Class<? extends GameMap> getMapClass() {
		return mapClass;
	}

	public void setMapClass(Class<? extends GameMap> mapClass) {
		this.mapClass = mapClass;
	}

	public void setPlayer(Entity player) {
		world.setPlayer(player);
	}

	public void setStartMap(String startMapName) {
		this.startMapName = startMapName;
	}

	public int getFieldSize() {
		return fieldSize;
	}

	public void setFieldSize(int fieldSize) {
		this.fieldSize = fieldSize;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Vector2f getCenter() {
		return center;
	}

	public void setCenter(Vector2f center) {
		this.center = center;
	}

	public Factory<RenderComponent> getRenderFactory() {
		return renderFactory;
	}

	public void setRenderFactory(Factory<RenderComponent> renderFactory) {
		this.renderFactory = renderFactory;
	}

	public Factory<MapRenderComponent> getMapRenderFactory() {
		return mapRenderFactory;
	}

	public void setMapRenderFactory(Factory<MapRenderComponent> mapRenderFactory) {
		this.mapRenderFactory = mapRenderFactory;
	}

	public String getStartMapName() {
		return startMapName;
	}

	public void setStartMapName(String startMapName) {
		this.startMapName = startMapName;
	}

	public Factory<GameMap> getMapFactory() {
		return mapFactory;
	}

	public void setMapFactory(Factory<GameMap> mapFactory) {
		this.mapFactory = mapFactory;
	}

	public MapLoader getMapLoader() {
		return mapLoader;
	}

	public void setMapLoader(MapLoader mapLoader) {
		this.mapLoader = mapLoader;
	}

}
