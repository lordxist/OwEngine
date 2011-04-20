package owengine.model.entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import owengine.model.util.position.PositionedArrayList;

public class EntityMap<T extends Entity> {

	private PositionedArrayList<T> entities = new PositionedArrayList<T>();
	private ArrayList<Point> blockedTiles = new ArrayList<Point>();

	public void block(Point p) {
		blockedTiles.add(p);
	}
	
	public void addEntity(T entity) {
		entities.add(entity);
	}

	public void update(int delta) {
		for (Entity entity : new ArrayList<Entity>(entities))
			entity.update(delta);
	}

	public Collection<T> getEntities() {
		return Collections.unmodifiableCollection(entities);
	}

	public Collection<T> friendEntities() {
		return entities;
	}

	boolean isPosBlocked(Point p) {
		return blockedTiles.contains(p) || getEntityAt(p) != null;
	}

	T getEntityAt(Point p) {
		return entities.at(p);
	}

}
