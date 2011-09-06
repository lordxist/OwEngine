package owengine.model.map;

import java.awt.Point;
import java.util.ArrayList;

import owengine.model.map.Entity;
import owengine.model.map.EntityMap;

public abstract class BasicEntity implements Entity {

	protected ArrayList<String> msg;

	public BasicEntity() {}

	public BasicEntity(ArrayList<String> msg) {
		this.msg = msg;
	}

	@Override
	public void update(int delta) {}

	@Override
	public EntityMap getMap() {
		return null;
	}

	@Override
	public void setMap(EntityMap map) {}

	@Override
	public void setPos(Point p) {}

}
