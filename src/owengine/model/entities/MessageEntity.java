package owengine.model.entities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import owengine.meta.NoView;
import owengine.meta.Params;
import owengine.model.map.BasicEntity;
import owengine.model.map.EntityMap;

@NoView
public class MessageEntity extends BasicEntity {

	private Point pos;

	@Params(names = {"msg"})
	public MessageEntity(EntityMap map, String msg) {
		super(map, new ArrayList<String>(Arrays.asList(msg.split("\\\\n"))));
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
