package owengine.examples.slickexample;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import owengine.model.map.MoveDir;
import owengine.view.PcStateView;

public class PcSpritesView implements PcStateView {

	private static HashMap<String, Image> pcSprites = new HashMap<String, Image>();

	public static void init() throws SlickException {
		for (MoveDir dir : MoveDir.values()) {
			pcSprites.put("stand_"+dir, new Image("owengine/examples/slickexample/res/pc/move/"+dir+"/0.png"));
		}
	}

	@Override
	public void draw(String state, int x, int y) {
		pcSprites.get(state).draw(x, y);
	}

}
