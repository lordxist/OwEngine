package owengine.ext.slick;

import java.awt.Point;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import owengine.MapLoader;
import owengine.meta.NoView;
import owengine.meta.Params;
import owengine.meta.View;
import owengine.model.entities.character.NonPlayerChar;
import owengine.model.map.DoorTile;
import owengine.model.map.Entity;
import owengine.model.map.EntityMap;
import owengine.model.map.MoveDir;
import owengine.model.map.WarpTile;
import owengine.model.story.EventMap;
import owengine.model.story.StoryEvent;
import owengine.view.CharView;
import owengine.view.GameMapView;

import static owengine.ext.slick.Utilities.*;

public class TiledMapLoader implements MapLoader {

	private String modelPackage, viewPackage, mapsPackage;
	private HashMap<String, EventMap> maps = new HashMap<String, EventMap>();

	public void setModel(String modelPackage) {
		this.modelPackage = modelPackage;
	}

	public void setView(String viewPackage) {
		this.viewPackage = viewPackage;
	}

	public void setMapsPackage(String mapsPackage) {
		this.mapsPackage = mapsPackage;
	}

	public HashMap<String, EventMap> loadMaps(ArrayList<owengine.view.View> views,
			HashMap<EntityMap, GameMapView> mapViews) {
		HashMap<String, TiledMap> tiled = loadTiledFromPackage();
		
		for (String name : tiled.keySet()) {
			EventMap map = new EventMap();
			maps.put(name, map);
			mapViews.put(map, new TiledGameMapView(tiled.get(name)));
		}
		for (Map.Entry<String, EventMap> entry : maps.entrySet()) {
			loadMap(tiled.get(entry.getKey()), entry.getValue(), views);
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

	public void loadMap(
			TiledMap tiledMap, EventMap map, ArrayList<owengine.view.View> views) {
		for (int i = 0; i < tiledMap.getObjectGroupCount(); i++)
			for (int j = 0; j < tiledMap.getObjectCount(i); j++)
				loadObject(tiledMap, i, j, map, views);
	}

	@SuppressWarnings("unchecked")
	public void loadObject(
			TiledMap tiledMap, int i, int j, EventMap map, ArrayList<owengine.view.View> views) {
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
		Point pos = new Point(tiledMap.getObjectX(i, j)/16,
				tiledMap.getObjectY(i, j)/16);
		if (type.equals("WarpTile")) {
			Point targetPos = new Point(
				Integer.parseInt(tiledMap.getObjectProperty(i, j, "target_x", null)),
				Integer.parseInt(tiledMap.getObjectProperty(i, j, "target_y", null)));
			WarpTile warp = new WarpTile(targetPos,
					maps.get(tiledMap.getObjectProperty(i, j, "target_map", null)));
			map.putWarpTile(pos, warp);
			return;
		}
		if (type.equals("DoorTile")) {
			Point targetPos = new Point(
				Integer.parseInt(tiledMap.getObjectProperty(i, j, "target_x", null)),
				Integer.parseInt(tiledMap.getObjectProperty(i, j, "target_y", null)));
			DoorTile warp = new DoorTile(targetPos,
					maps.get(tiledMap.getObjectProperty(i, j, "target_map", null)),
					MoveDir.valueOf(tiledMap.getObjectProperty(i, j, "dir", null)));
			map.putDoorTile(pos, warp);
			return;
		}
		if (type.equals("NonPlayerChar")) {
			int id = Integer.parseInt(tiledMap.getObjectProperty(i, j, "id", null));
			ArrayList<String> msg = new ArrayList<String>(
				Arrays.asList(tiledMap.getObjectProperty(i, j, "msg", "").split("\\\\n"))
			);
			NonPlayerChar character;
			if (tiledMap.getObjectProperty(i, j, "path", null) == null)
				character = new NonPlayerChar(id, msg);
			else character = new NonPlayerChar(id, msg, loadMoves(tiledMap, i, j));
			map.addEntity(pos, character);
			views.add(new CharView(character, new ActionAnimationsView(),
					new PcSpritesView()));
			return;
		}
		try {
			Class<?> klass;
			try {
				klass = Class.forName(modelPackage+"."+type);
			} catch (Exception e) {
				klass = Class.forName("owengine.model.entities."+type);
			}
			Constructor<?> constr = klass.getConstructors()[0];
			if (Arrays.asList(klass.getInterfaces()).contains(StoryEvent.class)) {
				StoryEvent event = (StoryEvent) constr.newInstance(map);
				map.addEventTile(pos.x, pos.y, event);
				return;
			}
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
			map.addEntity(pos, entity);
			if (klass.isAnnotationPresent(NoView.class)) {
				return;
			}
			String viewType = viewPackage+".";
			if (klass.isAnnotationPresent(View.class)) {
				viewType += klass.getAnnotation(View.class).name();
			} else {
				viewType += type+"View";
			}
			owengine.view.View view = (owengine.view.View) Class.forName(viewType).getConstructors()[0].newInstance(entity);
			views.add(view);
		} catch (Exception e) {e.printStackTrace();}
	}

	private static ArrayList<Point> loadMoves(TiledMap tiledMap, int i, int j) {
		ArrayList<Point> moves = new ArrayList<Point>();
		for (String p : tiledMap.getObjectProperty(i, j, "path", null).split(",")) {
			String[] coords = p.split(":");
			moves.add(new Point(Integer.parseInt(coords[0]),
					Integer.parseInt(coords[1])));
		}
		return moves;
	}

}
