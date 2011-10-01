package owengine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import owengine.model.entities.character.PlayerChar;
import owengine.model.map.EntityMap;
import owengine.view.GameMapView;
import owengine.view.PcActionView;
import owengine.view.PcStateView;
import owengine.view.PlayerCharView;
import owengine.view.View;

public class OwEngine<T extends View> {

	private MapLoader loader;
	private HashMap<String, EntityMap> maps = new HashMap<String, EntityMap>();
	private ArrayList<T> views = new ArrayList<T>();
	private HashMap<EntityMap, GameMapView> mapViews = new HashMap<EntityMap, GameMapView>();
	private String model;
	private String view;
	private String mapsPackage;
	private PlayerChar pc;
	private PlayerCharView pcView;

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
	}

	public EntityMap getMap(String name) {
		return maps.get(name);
	}

	public ArrayList<T> getViews() {
		return views;
	}

	public GameMapView getMapView(EntityMap map) {
		return mapViews.get(map);
	}

	public void render() {
		mapViews.get(pc.getMap()).render();
		pcView.draw();
	}

	public void setStartPosition(Point pos, String mapName) {
		pc.setPos(pos);
		maps.get(mapName).addEntity(pc);
	}

	public void update(int delta) {
		pc.getMap().update(delta);
	}

}
