package owengine.core.world;

import java.awt.Graphics;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class World {

	private static World instance;

	public static World getInstance() {
		if (instance == null) {
			instance = new World();
		}
		return instance;
	}

	private Entity player;
	private HashMap<String, GameMap> maps = new HashMap<String, GameMap>();

	protected World() {}

	public Entity getPlayer() {
		return player;
	}

	public void setPlayer(Entity player) {
		this.player = player;
	}

	public void paint(Graphics g) {
		player.getMap().paint(g);
	}

	public void update(int delta) {
		player.getMap().update(delta);
	}

	public void enterStartMap() {
		player.getMap().enter();
		player.getMap().startMapEvent();
	}

	public void addMapWithName(String mapName, GameMap map) {
		maps.put(mapName, map);
	}

	public GameMap getMapByName(String mapName) {
		return maps.get(mapName);
	}

	public Set<GameMap> getMaps() {
		return Collections.unmodifiableSet(new HashSet<GameMap>(maps.values()));
	}

}
