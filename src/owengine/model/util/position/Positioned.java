package owengine.model.util.position;

import java.awt.Point;

public interface Positioned {

	Point getPos();

	boolean isOnPos(Point p);

}
