package owengine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import owengine.controller.MovableController;
import owengine.model.entities.character.NonPlayerChar;
import owengine.model.entities.character.PlayerChar;
import owengine.model.map.Entity;
import owengine.model.map.EntityMap;
import owengine.model.story.EventMap;
import owengine.view.GameMapView;
import owengine.view.PcActionView;
import owengine.view.PcStateView;
import owengine.view.PlayerCharView;
import owengine.view.View;

public class OwEngine<T extends View> {

	private MapLoader loader;
	private HashMap<String, EventMap> maps = new HashMap<String, EventMap>();
	private ArrayList<View> views = new ArrayList<View>();
	private HashMap<EntityMap, GameMapView> mapViews = new HashMap<EntityMap, GameMapView>();
	private String model;
	private String view;
	private String mapsPackage;
	private PlayerChar pc;
	private PlayerCharView pcView;
	private ArrayList<MovableController> controllers = new ArrayList<MovableController>();

	public void setMapsPackage(String mapsPackage) {
		this.mapsPackage = mapsPackage;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setView(String view) {
		this.view = view;
	}

	public void setMapLoader(MapLoader loader) {
		this.loader = loader;
	}

	public void setPlayerCharViews(PcActionView actionView, PcStateView stateView,
			int width, int height) {
		this.pc = new PlayerChar(null, 0, 0);
		pcView = new PlayerCharView(pc, actionView, stateView, width, height);
	}

	public void load() {
		loader.setModel(model);
		loader.setView(view);
		loader.setMapsPackage(mapsPackage);
		maps = loader.loadMaps(views, mapViews);
		for (EntityMap m : maps.values()) {
			for (Entity e : m.getEntities()) {
				if (e instanceof NonPlayerChar && ((NonPlayerChar) e).hasAIMovement()) {
					controllers.add(new MovableController((NonPlayerChar) e));
				}
			}
		}
	}

	public EntityMap getMap(String name) {
		return maps.get(name);
	}

	public ArrayList<View> getViews() {
		return views;
	}

	public GameMapView getMapView(EntityMap map) {
		return mapViews.get(map);
	}

	public void render(int width, int height) {
		mapViews.get(pc.getMap()).render();
		for (View view : views) {
			view.draw(width, height);
		}
		pcView.draw();
	}

	public void setStartPosition(Point pos, String mapName) {
		pc.setPos(pos);
		maps.get(mapName).addEntity(pc);
	}

	public void update(int delta) {
		pc.getMap().update(delta);
		for (MovableController controller : controllers) {
			controller.update(delta);
		}
	}

}
