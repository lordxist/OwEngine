package owengine.model.map;

import java.awt.Point;
import java.util.ArrayList;

public interface Entity {

	Entity NULL = new Entity() {
		@Override
		public void update(int delta) {}
		
		@Override
		public EntityMap getMap() {
			return null;
		}
		
		@Override
		public void setMap(EntityMap map) {}
		
		@Override
		public Point getPos() {
			return null;
		}
		
		@Override
		public void setPos(Point pos) {}
		
		@Override
		public ArrayList<String> explore() {
			return null;
		}
	};

	void update(int delta);
	EntityMap getMap();
	void setMap(EntityMap map);
	Point getPos();
	void setPos(Point pos);
	ArrayList<String> explore();

}
