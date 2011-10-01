package owengine.ext.slick;

import java.awt.Point;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import owengine.MapLoader;
import owengine.model.map.Entity;
import owengine.model.map.EntityMap;
import owengine.view.GameMapView;

import static owengine.ext.slick.Utilities.*;

public class TiledMapLoader implements MapLoader {

	private String modelPackage, viewPackage, mapsPackage;

	public void setModel(String modelPackage) {
		this.modelPackage = modelPackage;
	}

	public void setView(String viewPackage) {
		this.viewPackage = viewPackage;
	}

	public void setMapsPackage(String mapsPackage) {
		this.mapsPackage = mapsPackage;
	}

	public <T extends owengine.view.View> HashMap<String, EntityMap> loadMaps(ArrayList<T> views,
			HashMap<EntityMap, GameMapView> mapViews) {
		HashMap<String, EntityMap> maps = new HashMap<String, EntityMap>();
		HashMap<String, TiledMap> tiled = loadTiledFromPackage();
		
		for (String name : tiled.keySet()) {
			EntityMap map = new EntityMap();
			loadMap(tiled.get(name), map, views);
			maps.put(name, map);
			mapViews.put(map, new TiledGameMapView(tiled.get(name)));
		}
		return maps;
	}

	private HashMap<String, TiledMap> loadTiledFromPackage() {
		HashMap<String, TiledMap> tileds = new HashMap<String, TiledMap>();
		for (File file : new File(mapsPackage).listFiles()) {
			try {
				String[] name = file.getName().split("\\.");
				if (name[name.length-1].equals("tmx")) {
					tileds.put(name[0], new TiledMap(file.getPath()));
				}
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		return tileds;
	}

	public <T extends owengine.view.View> void loadMap(
			TiledMap tiledMap, EntityMap map, ArrayList<T> views) {
		for (int i = 0; i < tiledMap.getObjectGroupCount(); i++)
			for (int j = 0; j < tiledMap.getObjectCount(i); j++)
				loadObject(tiledMap, i, j, map, views);
	}

	@SuppressWarnings("unchecked")
	public <T extends owengine.view.View> void loadObject(
			TiledMap tiledMap, int i, int j, EntityMap map, ArrayList<T> views) {
		String type = tiledMap.getObjectType(i, j);
		if (type == "") {
			for (int x = 0; x < tiledMap.getObjectWidth(i, j)/16; x++) {
				for (int y = 0; y < tiledMap.getObjectHeight(i, j)/16; y++) {
					map.block(new Point(
							tiledMap.getObjectX(i, j)/16+x,
							tiledMap.getObjectY(i, j)/16+y));
				}
			}
			return;
		}
		try {
			Class<?> klass = Class.forName(modelPackage+"."+type);
			Constructor<?> constr = klass.getConstructors()[0];
			Class<?>[] paramTypes = constr.getParameterTypes();
			Object[] params = new Object[paramTypes.length];
			int k = 0;
			for (String name : constr.getAnnotation(Params.class).names()) {
				params[k] = tiledMap.getObjectProperty(i, j, name, null);
				if (paramTypes[k].isPrimitive() && paramTypes[k] != Void.TYPE) {
					params[k] = convertToWrapper(paramTypes[k])
						.getMethod("valueOf", new Class<?>[]{String.class})
						.invoke(null, params[k]);
				}
				k++;
			}
			Entity entity = (Entity) constr.newInstance(params);
			map.addEntity(entity);
			String viewType = viewPackage+".";
			if (klass.isAnnotationPresent(View.class)) {
				viewType += klass.getAnnotation(View.class).name();
			} else {
				viewType += type+"View";
			}
			T view = (T) Class.forName(viewType).getConstructors()[0].newInstance(entity);
			views.add(view);
		} catch (Exception e) {e.printStackTrace();}
	}

}
