package owengine.examples.slickexample;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import owengine.model.entities.character.PlayerChar;
import owengine.model.entities.movement.MovablePosition;
import owengine.model.map.EntityMap;
import owengine.model.map.MoveDir;
import owengine.model.story.EventMap;
import owengine.view.CharView;
import owengine.view.PlayerCharView;

public class SlickExample extends BasicGame {

	private PlayerChar character;
	private PlayerCharView pcView;
	private ArrayList<CharView> charViews = new ArrayList<CharView>();
	private HashMap<EntityMap, TiledGameMapView> mapsViews =
		new HashMap<EntityMap, TiledGameMapView>();

	public SlickExample() {
		super("Slick Example");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		TiledMap map = new TiledMap("owengine/examples/slickexample/res/map.tmx");
		character = new PlayerChar(null, 5, 5);
		EventMap gameMap = new EventMap(character);
		gameMap.addEntity(character);
		pcView = new PlayerCharView(character, new ActionAnimationsView(),
				new PcSpritesView(), 16);
		TiledGameMapView mapView = new TiledGameMapView(pcView, map);
		mapsViews.put(gameMap, mapView);
		TiledMapLoader.loadMap(map, gameMap, charViews, character);
		
		AnimationLoader.init();
		PcSpritesView.init();
		gc.setTargetFrameRate(100);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {		
		EntityMap gameMap = character.getPos().getMap();
		gameMap.update(delta);
		
		handleKeyboardInput(gc.getInput());
	}

	private void handleKeyboardInput(Input input) {
		MovablePosition pos = character.getPos();
		if (input.isKeyDown(Input.KEY_LEFT))
			pos.activateMovement(MoveDir.left);
		else if (input.isKeyDown(Input.KEY_RIGHT))
			pos.activateMovement(MoveDir.right);
		else if (input.isKeyDown(Input.KEY_UP))
			pos.activateMovement(MoveDir.up);
		else if (input.isKeyDown(Input.KEY_DOWN))
			pos.activateMovement(MoveDir.down);
		else if (input.isKeyPressed(Input.KEY_LALT))
			character.tap();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		mapsViews.get(character.getPos().getMap()).renderTiled();
		pcView.draw(160, 160);
		for (CharView view : charViews)
			view.draw(160, 160);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SlickExample());
		app.setDisplayMode(160, 160, false);
		app.setShowFPS(false);
		app.start();
	}

}
