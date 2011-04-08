package owengine.view;

public class GameMapView {

	private PlayerCharView pcView;

	public GameMapView(PlayerCharView pcView) {
		this.pcView = pcView;
	}

	public int getScreenX(int width) {
		return width/2-pcView.getScreenX();
	}

	public int getScreenY(int height) {
		return height/2-pcView.getScreenY();
	}

}
