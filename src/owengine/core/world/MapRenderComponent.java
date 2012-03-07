package owengine.core.world;

import java.awt.Graphics;

public abstract class MapRenderComponent {

	protected GameMap map;
	protected MapRenderHelper helper;

	void setMap(GameMap map) {
		this.map = map;
	}

	public void setRenderHelper(MapRenderHelper helper) {
		this.helper = helper;
	}

	public int getFieldSize() {
		return helper.getFieldSize();
	}

	public abstract void paint(Graphics g);

	protected int getHeight() {
		return helper.getHeight();
	}

	protected int getWidth() {
		return helper.getWidth();
	}

}
