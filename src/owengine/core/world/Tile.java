package owengine.core.world;

import java.awt.Graphics;
import java.awt.Point;

import javax.vecmath.Vector2f;

import owengine.core.util.timed.TimedAction;

public class Tile implements PositionedRenderer.Positioned {

	private PositionedRenderer<Tile> renderer;
	private Point pos;
	private String name;
	private String touchAction;
	private int touchActionDuration;
	private String entityTouchAction;
	private int entityTouchActionDuration;

	public Tile(Point pos, String name) {
		this.pos = pos;
	}

	public void paint(Graphics g) {
		renderer.paint(g);
	}

	public void setRenderer(PositionedRenderer<Tile> renderer) {
		this.renderer = renderer;
		renderer.setRendered(this);
	}

	public Point getPos() {
		return pos;
	}

	@Override
	public Vector2f getExactPos() {
		return new Vector2f(pos.x, pos.y);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTouchAction() {
		return touchAction;
	}

	public int getTouchActionDuration() {
		return touchActionDuration;
	}

	public void setTouchAction(String name, int duration) {
		touchAction = name;
		touchActionDuration = duration;
	}

	public String getEntityTouchAction() {
		return entityTouchAction;
	}

	public int getEntityTouchActionDuration() {
		return entityTouchActionDuration;
	}

	public void setEntityTouchAction(String name, int duration) {
		entityTouchAction = name;
		entityTouchActionDuration = duration;
	}

	public void touch(Entity e) {
		new TimedAction(touchAction, touchActionDuration).start();
		if (entityTouchAction != null) {
			e.setActionName(entityTouchAction);
			e.setActionDuration(entityTouchActionDuration);
		}
	}

}
