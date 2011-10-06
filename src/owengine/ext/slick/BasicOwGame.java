package owengine.ext.slick;

import java.awt.Point;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import owengine.OwEngine;
import owengine.model.entities.character.PlayerChar;
import owengine.view.CharView;

public abstract class BasicOwGame extends BasicGame {

	protected OwEngine<SlickGraphicsView> engine = new OwEngine<SlickGraphicsView>();
	protected PlayerChar pc;
	protected String mapsFolder, modelPackage, viewPackage, startMap;
	protected Point startPos;
	protected String resDir;

	public BasicOwGame(String title, String mapsFolder, String modelPackage,
			String viewPackage, String startMap, Point startPos, String resDir) {
		super(title);
		this.mapsFolder = mapsFolder;
		this.modelPackage = modelPackage;
		this.viewPackage = viewPackage;
		this.startMap = startMap;
		this.startPos = startPos;
		this.resDir = resDir;
	}

	@Override
	public void init(GameContainer gc) throws SlickException {		
		engine.setMapsPackage(mapsFolder);
		engine.setModel(modelPackage);
		engine.setView(viewPackage);
		engine.setPlayerCharViews(new ActionAnimationsView(),
				new PcSpritesView(), 160, 160);
		engine.setMapLoader(new owengine.ext.slick.TiledMapLoader());
		engine.load();
		engine.setStartPosition(startPos, startMap);
		pc = PlayerChar.getInstance();
		
		SlickGraphicsView.setGraphics(gc.getGraphics());
		CharView.setFieldSize(16);
		AnimationLoader.init(resDir);
		PcSpritesView.init(resDir);
		gc.setTargetFrameRate(100);
	}

	@Override
	public void update(GameContainer gc, int delta) {		
		engine.update(delta);
		
		if (!InputBlockingEvent.isInputDisabled()) {
			handleKeyboardInput(gc.getInput());
		}
	}

	protected abstract void handleKeyboardInput(Input input);

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		engine.render(160, 160);
	}

}
