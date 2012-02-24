package framework.world;

import java.util.ArrayList;

public class World {

	private ArrayList<Entity> entities = new ArrayList<Entity>();

	public void addEntity(Entity e) {
		e.setWorld(this);
		entities.add(e);
	}

	public void paint() {
		for (Entity e : entities) {
			e.paint();
		}
	}

	public void update(int delta) {
		for (Entity e : entities) {
			e.update(delta);
		}
	}

	public boolean isBlocked(int x, int y) {
		for (Entity e : entities) {
			if (e.getX() == x && e.getY() == y) {
				return true;
			}
		}
		return false;
	}

}
