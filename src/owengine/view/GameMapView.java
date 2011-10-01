package owengine.view;

import owengine.model.entities.character.PlayerChar;


public abstract class GameMapView {

	public int getScreenX(int width) {
		return width/2-CharView.getScreenX(PlayerChar.getInstance());
	}

	public int getScreenY(int height) {
		return height/2-CharView.getScreenY(PlayerChar.getInstance());
	}

	public abstract void render();

}
