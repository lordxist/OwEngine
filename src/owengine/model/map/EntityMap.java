package owengine.model.map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import owengine.model.util.NullObjectHashMap;

public class EntityMap {

	private HashMap<Point, Entity> entities = new HashMap<Point, Entity>();
	private ArrayList<Point> blockedTiles = new ArrayList<Point>();
	private NullObjectHashMap<Point, WarpTile> warpLayer = new NullObjectHashMap<Point, WarpTile>(WarpTile.NULL);
	private NullObjectHashMap<Point, DoorTile> doorLayer = new NullObjectHashMap<Point, DoorTile>(DoorTile.NULL);

	public void moveOnMap(Entity e, Point p) {
		entities.remove(e.getPos());
		entities.put(p, e);
		e.setPos(p);
		warpLayer.get(e.getPos()).warp(e);
	}

	public void prepareMove(Entity e, Point p) {
		if (!isPosBlocked(p)) {
			doorLayer.get(e.getPos()).warp(e, MoveDir.relativeDir(p, e.getPos()));
		}
	}

	public void block(Point p) {
		blockedTiles.add(p);
	}
	
	public void addEntity(Entity entity) {
		addEntity(entity.getPos(), entity);
	}
	
	public void addEntity(Point pos, Entity entity) {
		entities.put(pos, entity);
		entity.setPos(pos);
		entity.setMap(this);
	}

	public void removeEntity(Entity entity) {
		entities.remove(entity.getPos());
	}

	public void update(int delta) {
		for (Entity entity : new ArrayList<Entity>(entities.values()))
			entity.update(delta);
	}

	public Collection<Entity> getEntities() {
		return Collections.unmodifiableCollection(entities.values());
	}

	Collection<Entity> friendEntities() {
		return entities.values();
	}

	public boolean isPosBlocked(Point p) {
		return blockedTiles.contains(p) || getEntityAt(p) != null;
	}

	public Entity getEntityAt(Point p) {
		return entities.get(p);
	}

	public void putWarpTile(Point p, WarpTile w) {
		warpLayer.put(p, w);
	}

	public void putDoorTile(Point p, DoorTile d) {
		doorLayer.put(p, d);
	}

}
