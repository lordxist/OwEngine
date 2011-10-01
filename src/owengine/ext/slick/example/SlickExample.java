package owengine.ext.slick.example;

import java.awt.Point;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import owengine.ext.slick.BasicOwGame;
import owengine.model.map.MoveDir;

public class SlickExample extends BasicOwGame {

	public static final String TITLE = "Slick Example";
	public static final String MAPS_FOLDER = "/Users/julian/gotnr/OwEngine/src/owengine/ext/slick/example/res/maps";
	public static final String MODEL_PACKAGE = "owengine.ext.slick.example";
	public static final String VIEW_PACKAGE = "owengine.ext.slick.example";
	public static final String START_MAP = "map";
	public static final Point START_POS = new Point(5, 5);

	public SlickExample() {
		super(TITLE, MAPS_FOLDER, MODEL_PACKAGE, VIEW_PACKAGE, START_MAP, START_POS);
	}

	@Override
	protected void handleKeyboardInput(Input input) {
		if (input.isKeyDown(Input.KEY_LEFT))
			pc.activateMovement(MoveDir.left);
		else if (input.isKeyDown(Input.KEY_RIGHT))
			pc.activateMovement(MoveDir.right);
		else if (input.isKeyDown(Input.KEY_UP))
			pc.activateMovement(MoveDir.up);
		else if (input.isKeyDown(Input.KEY_DOWN))
			pc.activateMovement(MoveDir.down);
		else if (input.isKeyPressed(Input.KEY_LALT)) {
			if (pc.getEntityNextTo() != null
					&& pc.getEntityNextTo().explore() != null)
				System.out.println(pc.getEntityNextTo().explore().get(0));}
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SlickExample());
		app.setDisplayMode(160, 160, false);
		app.setShowFPS(false);
		app.start();
	}

}
