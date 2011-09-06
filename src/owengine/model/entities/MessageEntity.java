package owengine.model.entities;

import java.awt.Point;
import java.util.ArrayList;

import owengine.model.map.BasicEntity;

public class MessageEntity extends BasicEntity {

	private Point pos;

	public MessageEntity(int x, int y, ArrayList<String> msg) {
		super(msg);
		pos = new Point(x, y);
	}

	@Override
	public Point getPos() {
		return pos;
	}

	@Override
	public void setPos(Point pos) {
		this.pos = pos;
	}

	@Override
	public ArrayList<String> explore() {
		return msg;
	}

}
