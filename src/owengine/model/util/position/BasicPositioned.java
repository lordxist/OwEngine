package owengine.model.util.position;

import java.awt.Point;


public abstract class BasicPositioned implements Positioned {

	public boolean isOnPos(Point p) {
		return getPos().equals(p);
	}

}
