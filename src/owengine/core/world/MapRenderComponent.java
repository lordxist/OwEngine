package owengine.core.world;

import java.awt.Graphics;

public abstract class MapRenderComponent {

	protected GameMap map;


	void setMap(GameMap map) {
		this.map = map;
	}

	public abstract void paint(Graphics g);

}
