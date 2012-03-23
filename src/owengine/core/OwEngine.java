package owengine.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.vecmath.Vector2f;

import owengine.core.world.Entity;
import owengine.core.world.GameMap;
import owengine.core.world.MapRenderer;
import owengine.core.world.Renderer;
import owengine.core.world.World;

/**
 * Provides a mechanism to load maps/entities with their render components.
 */
public class OwEngine {

	/**
	 * Generic factory to produce objects without passing arguments.
	 */
	public interface Factory<T> {
	
		/**
		 * Produces a new instance without passing arguments.
		 */
		T createNew();
	
	}

	/**
	 * Simple factory implementation that produces new objects
	 * for a given class.
	 */
	public static class Producer<T> implements Factory<T> {
		
		/**
		 * Thrown when a class used in the producer doesn't have a
		 * default constructor or is inaccessible.
		 */
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
		
		/*
		 * (non-Javadoc)
		 * @see owengine.core.OwEngine.Factory#createNew()
		 */
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
		
		/**
		 * The class used to produce objects.
		 */
		public Class<? extends T> getProducerClass() {
			return clazz;
		}
		
		/**
		 * Set the class used to produce objects.
		 */
		public void setProducerClass(Class<? extends T> clazz) {
			this.clazz = clazz;
		}
	}

	public static final int DEFAULT_FIELD_SIZE = 16;

	private int fieldSize = DEFAULT_FIELD_SIZE;
	private int width;
	private int height;
	private Vector2f center;
	private String startMapName;
	private MapLoader mapLoader;
	private Class<? extends Renderer> renderClass;
	private Class<? extends MapRenderer> mapRenderClass;
	private Class<? extends GameMap> mapClass = GameMap.class;
	private Factory<Renderer> renderFactory =
		new Producer<Renderer>(renderClass);
	private Factory<MapRenderer> mapRenderFactory =
		new Producer<MapRenderer>(mapRenderClass);
	private Factory<GameMap> mapFactory = new Producer<GameMap>(mapClass);
	private World world = World.getInstance();

	/**
	 * Loads maps via the specified map loader. Fits all maps and
	 * entities with the specified render components.
	 * Also adds the player (if any) to the start map (if any).
	 * 
	 * @throws IllegalProducerClassException when the render or map classes
	 * have no default constructor or are inaccessible.
	 */
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
		if (startMap != null && player != null) {
			startMap.addEntity(player);
		}
		
		for (GameMap map : world.getMaps()) {
			for (Entity entity : map.getEntities()) {
				entity.setRenderComponent(newRenderComponent());
			}
		}
	}

	private Renderer newRenderComponent() {
		Renderer renderer = renderFactory.createNew();
		renderer.getHelper().setFieldSize(fieldSize);
		if (renderer instanceof Renderer.CenteredRenderer) {
			Renderer.CenteredRenderer centeredRenderer =
				(Renderer.CenteredRenderer) renderer;
			centeredRenderer.setCenter(getCenter());
			centeredRenderer.setCenterEntity(world.getPlayer());
		}
		return renderer;
	}

	private MapRenderer newMapRenderComponent() {
		MapRenderer renderer = mapRenderFactory.createNew();
		renderer.setFieldSize(fieldSize);
		renderer.setHeight(height);
		renderer.setWidth(width);
		if (renderer instanceof MapRenderer.CenteredRenderer) {
			MapRenderer.CenteredRenderer centeredRenderer =
				(MapRenderer.CenteredRenderer) renderer;
			centeredRenderer.setCenter(getCenter());
			centeredRenderer.setCenterEntity(world.getPlayer());
		}
		return renderer;
	}

	/**
	 * Add the given entity to the given map and set the entity's
	 * render component.
	 */
	public void addEntity(Entity entity, GameMap map) {
		map.addEntity(entity);
		entity.setRenderComponent(newRenderComponent());
	}

	/**
	 * Load the properties from the given file.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public void loadProperties(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(filename));
		String property, property2;
		if ((property = properties.getProperty("modelPackage")) != null) {
			setModelPackage(Package.getPackage(property));
		}
		if ((property = properties.getProperty("renderClass")) != null) {
			setRenderClass((Class<? extends Renderer>) Class.forName(property));
		}
		if ((property = properties.getProperty("mapRenderClass")) != null) {
			setMapRenderClass((Class<? extends MapRenderer>) Class.forName(property));
		}
		if ((property = properties.getProperty("mapClass")) != null) {
			setMapClass((Class<? extends GameMap>) Class.forName(property));
		}
		if ((property = properties.getProperty("width")) != null) {
			width = Integer.parseInt(property);
		}
		if ((property = properties.getProperty("height")) != null) {
			height = Integer.parseInt(property);
		}
		if ((property = properties.getProperty("fieldSize")) != null) {
			fieldSize = Integer.parseInt(property);
		}
		if ((property = properties.getProperty("centerX")) != null &&
				(property2 = properties.getProperty("centerY")) != null) {
			center = new Vector2f(Integer.parseInt(property), Integer.parseInt(property2));
		}
		if ((property = properties.getProperty("startMapName")) != null) {
			startMapName = property;
		}
	}

	/**
	 * Load the properties using the default filename.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public void loadProperties() throws FileNotFoundException, IOException, ClassNotFoundException {
		loadProperties("../owengine.properties");
	}

	/**
	 * The package from which models are to be loaded.
	 */
	public Package getModelPackage() {
		return mapLoader.getModelPackage();
	}

	/**
	 * Set the model package
	 */
	public void setModelPackage(Package modelPackage) {
		mapLoader.setModelPackage(modelPackage);
	}

	/**
	 * The class used as default for render components.
	 */
	public Class<? extends Renderer> getRenderClass() {
		return renderClass;
	}

	/**
	 * Set the default render component class.
	 * Must have a default constructor and be accessible.
	 */
	public void setRenderClass(Class<? extends Renderer> renderClass) {
		this.renderClass = renderClass;
		renderFactory = new Producer<Renderer>(renderClass);
	}

	/**
	 * The class used as default for map render components.
	 */
	public Class<? extends MapRenderer> getMapRenderClass() {
		return mapRenderClass;
	}

	/**
	 * Set the default map render component class.
	 * Must have a default constructor and be accessible.
	 */
	public void setMapRenderClass(Class<? extends MapRenderer> mapRenderClass) {
		this.mapRenderClass = mapRenderClass;
		mapRenderFactory = new Producer<MapRenderer>(mapRenderClass);
	}

	/**
	 * The class used as default for maps.
	 */
	public Class<? extends GameMap> getMapClass() {
		return mapClass;
	}

	/**
	 * Set the default map class.
	 * Must have a default constructor and be accessible.
	 */
	public void setMapClass(Class<? extends GameMap> mapClass) {
		this.mapClass = mapClass;
		mapFactory = new Producer<GameMap>(mapClass);
	}

	/**
	 * The factory used to produce render components.
	 */
	public Factory<Renderer> getRenderFactory() {
		return renderFactory;
	}

	/**
	 * Set the factory used to produce render components.
	 */
	public void setRenderFactory(Factory<Renderer> renderFactory) {
		this.renderFactory = renderFactory;
	}

	/**
	 * The factory used to produce map render components.
	 */
	public Factory<MapRenderer> getMapRenderFactory() {
		return mapRenderFactory;
	}

	/**
	 * Set the factory used to produce map render components.
	 */
	public void setMapRenderFactory(Factory<MapRenderer> mapRenderFactory) {
		this.mapRenderFactory = mapRenderFactory;
	}

	/**
	 * The factory used to produce maps.
	 */
	public Factory<GameMap> getMapFactory() {
		return mapFactory;
	}

	/**
	 * Set the factory used to produce maps.
	 */
	public void setMapFactory(Factory<GameMap> mapFactory) {
		this.mapFactory = mapFactory;
	}

	/**
	 * The map loader.
	 */
	public MapLoader getMapLoader() {
		return mapLoader;
	}

	/**
	 * Set the map loader.
	 */
	public void setMapLoader(MapLoader mapLoader) {
		this.mapLoader = mapLoader;
	}

	/**
	 * The name of the start map.
	 */
	public String getStartMapName() {
		return startMapName;
	}

	/**
	 * Set the start map name.
	 */
	public void setStartMapName(String startMapName) {
		this.startMapName = startMapName;
	}

	/**
	 * The field size used for the render components.
	 */
	public int getFieldSize() {
		return fieldSize;
	}

	/**
	 * Set the field size used for the render components.
	 * Will only be used when the render components are
	 * subclasses of OrthogonalCenteredMapRenderComponent
	 * or OrthogonalCenteredRenderComponent, respectively.
	 */
	public void setFieldSize(int fieldSize) {
		this.fieldSize = fieldSize;
	}

	/**
	 * The width used for the render components.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Set the width used for the render components.
	 * Will only be used when the render components are
	 * subclasses of OrthogonalCenteredMapRenderComponent
	 * or OrthogonalCenteredRenderComponent, respectively.
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * The height used for the render components.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Set the height used for the render components.
	 * Will only be used when the render components are
	 * subclasses of OrthogonalCenteredMapRenderComponent
	 * or OrthogonalCenteredRenderComponent, respectively.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * The center used for the render components.
	 * Default coordinates are: (size/fieldSize)/2,
	 * where size is width or height, respectively.
	 */
	public Vector2f getCenter() {
		if (center == null) {
			return new Vector2f((width/fieldSize)/2, (height/fieldSize)/2);
		}
		return center;
	}

	/**
	 * Set the center used for the render components.
	 * Will only be used when the render components are
	 * subclasses of OrthogonalCenteredMapRenderComponent
	 * or OrthogonalCenteredRenderComponent, respectively.
	 */
	public void setCenter(Vector2f center) {
		this.center = center;
	}

}
