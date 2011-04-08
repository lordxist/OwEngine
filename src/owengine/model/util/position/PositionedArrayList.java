package owengine.model.util.position;

import java.awt.Point;
import java.util.ArrayList;

public class PositionedArrayList<T extends Positioned> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;

	private T nullObject;

	public PositionedArrayList() {}

	public PositionedArrayList(T nullObject) {
		this.nullObject = nullObject;
	}

	public T at(Point p) {
		for (T t : this)
			if (t.isOnPos(p))
				return t;
		return nullObject;
	}

	public T at(Positioned p) {
		return at(p.getPos());
	}

}
