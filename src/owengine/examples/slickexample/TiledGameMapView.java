package owengine.examples.slickexample;

import org.newdawn.slick.tiled.TiledMap;

import owengine.view.GameMapView;
import owengine.view.PlayerCharView;

public class TiledGameMapView extends GameMapView {

	private TiledMap tiledMap;

	public TiledGameMapView(PlayerCharView pcView, TiledMap tiledMap) {
		super(pcView);
		this.tiledMap = tiledMap;
	}

	public void renderTiled() {
		tiledMap.render(getScreenX(160),
				getScreenY(160), 0, 0,
				160, 160);
	}

	public TiledMap getTiled() {
		return tiledMap;
	}

}
