package owengine.core.world;

import java.awt.Graphics;
import java.awt.Point;

import javax.vecmath.Vector2f;

import owengine.core.util.timed.TimedAction;

public class Tile implements PositionedRenderer.Positioned {

	private PositionedRenderer<Tile> renderer;
	private Point pos;
	private String name;
	private TimedAction touchAction;
	private String entityState;

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

	public TimedAction getTouchAction() {
		return touchAction;
	}

	public void setTouchAction(final TimedAction action) {
		touchAction = new TimedAction(null, action.getDuration()) {
			@Override
			public void start() {
				name = action.getName() + "_" + Tile.this.name;
				super.start();
			}
			
			@Override
			public void updateAction(float delta) {
				action.updateAction(delta);
			}
		};
	}

	public void setTouchAction(String name, int duration) {
		setTouchAction(new TimedAction(name, duration) {
			@Override public void updateAction(float delta) {}
		});
	}

	public void touch(Entity e) {
		touchAction.start();
		e.setState(entityState);
	}

}
