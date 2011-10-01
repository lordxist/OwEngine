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

	public BasicOwGame(String title, String mapsFolder, String modelPackage,
			String viewPackage, String startMap, Point startPos) {
		super(title);
		this.mapsFolder = mapsFolder;
		this.modelPackage = modelPackage;
		this.viewPackage = viewPackage;
		this.startMap = startMap;
		this.startPos = startPos;
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
		
		CharView.setFieldSize(16);
		AnimationLoader.init();
		PcSpritesView.init();
		gc.setTargetFrameRate(100);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {		
		engine.update(delta);
		
		handleKeyboardInput(gc.getInput());
	}

	protected abstract void handleKeyboardInput(Input input);

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		engine.render();
		for (SlickGraphicsView view : engine.getViews()) {
			view.setGraphics(g);
			view.draw(160, 160);
		}
	}

}
