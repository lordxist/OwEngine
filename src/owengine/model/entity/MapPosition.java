package owengine.model.entity;

import java.awt.Point;

public abstract class MapPosition<T extends Entity> extends Point{

	private EntityMap<T> map;
	protected T entity;

	public MapPosition(T entity, EntityMap<T> map, int x, int y) {
		super(x, y);
		this.entity = entity;
		this.map = map;
	}

	public void setMap(EntityMap<T> map) {
		if (this.map != null)
			this.map.friendEntities().remove(entity);
		this.map = map;
		map.friendEntities().add(entity);
	}

	public EntityMap<T> getMap() {
		return map;
	}

	protected T getEntityAt(Point p) {
		return map.getEntityAt(p);
	}

	protected boolean isPosBlocked(Point p) {
		return map.isPosBlocked(p);
	}

	/**/
	private boolean someProperty;
	public void setProperty(boolean property) {
		someProperty = property;
	}
	public boolean getProperty() {
		return someProperty;
	}
	/**/

}
