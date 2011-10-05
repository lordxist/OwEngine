package owengine.ext.slick;

import java.awt.Point;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import owengine.OwEngine;
import owengine.model.entities.character.PlayerChar;
import owengine.model.story.EventMap;
import owengine.view.CharView;

public abstract class BasicOwGameState extends BasicGameState {

	protected OwEngine<SlickGraphicsView> engine = new OwEngine<SlickGraphicsView>();
	protected PlayerChar pc;
	protected String mapsFolder, modelPackage, viewPackage, startMap;
	protected Point startPos;
	protected int stateId = -1;

	public BasicOwGameState(int stateId, String mapsFolder, String modelPackage,
			String viewPackage, String startMap, Point startPos) {
		this.stateId = stateId;
		this.mapsFolder = mapsFolder;
		this.modelPackage = modelPackage;
		this.viewPackage = viewPackage;
		this.startMap = startMap;
		this.startPos = startPos;
	}

	@Override
	public int getID() {
		return stateId;
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		((EventMap) pc.getMap()).unpauseEvents();
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) {
		((EventMap) pc.getMap()).pauseEvents();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		engine.setMapsPackage(mapsFolder);
		engine.setModel(modelPackage);
		engine.setView(viewPackage);
		engine.setPlayerCharViews(new ActionAnimationsView(),
				new PcSpritesView(), 160, 160);
		engine.setMapLoader(new owengine.ext.slick.TiledMapLoader());
		engine.load();
		engine.setStartPosition(startPos, startMap);
		pc = PlayerChar.getInstance();
		
		CharView.setFieldSize(16);
		AnimationLoader.init();
		PcSpritesView.init();
		gc.setTargetFrameRate(100);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		engine.render(160, 160);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		engine.update(delta);
		
		handleKeyboardInput(gc.getInput(), sbg);
	}

	protected abstract void handleKeyboardInput(Input input, StateBasedGame sbg);

}
