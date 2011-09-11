package owengine.examples.slickexample;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import owengine.model.action.ActionType;
import owengine.model.entities.movement.MovementActionType;
import owengine.model.entities.character.Char;
import owengine.model.map.MoveDir;

import static owengine.model.entities.movement.MovementActionType.*;
import static owengine.model.map.MoveDir.*;

public class AnimationLoader {

	private static HashMap<String, Animation> animations =
		new HashMap<String, Animation>();

	public static void init() throws SlickException {
		for (MoveDir dir : new MoveDir[]{left, right})
			for (ActionType type : MovementActionType.values())
				animations.put(type+"_"+dir, loadAnimation("move", dir, 2));
		for (MoveDir dir : new MoveDir[]{up, down}) {
			animations.put(turn+"_"+dir, loadAnimation("move", dir, 2));
			for (ActionType type : new ActionType[]{bump, move})
				animations.put(type+"_"+dir, loadAnimation("move", dir, 3));
		}
	}

	private static Animation loadAnimation(String name, MoveDir dir, int frames)
			throws SlickException {
		Image[] images = new Image[frames];
		for (int i = 0; i < frames; i++)
			images[i] = new Image("owengine/examples/slickexample/res/pc/"+name+"/"+dir+"/"+i+".png");
		return new Animation(images, 2*Char.STD_MOVE_SPEED/(frames+1));
	}

	public static Animation getAnimation(String key) {
		return animations.get(key);
	}

}
