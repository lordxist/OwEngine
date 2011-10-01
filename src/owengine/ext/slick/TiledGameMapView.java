package owengine.ext.slick;

import org.newdawn.slick.tiled.TiledMap;

import owengine.view.GameMapView;

public class TiledGameMapView extends GameMapView {

	private TiledMap tiledMap;

	public TiledGameMapView(TiledMap tiledMap) {
		this.tiledMap = tiledMap;
	}

	public void render() {
		tiledMap.render(getScreenX(160),
				getScreenY(160), 0, 0,
				160, 160);
	}

	public TiledMap getTiled() {
		return tiledMap;
	}

}
