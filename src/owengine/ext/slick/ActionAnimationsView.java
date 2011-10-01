package owengine.ext.slick;

import org.newdawn.slick.Animation;

import owengine.view.PcActionView;

public class ActionAnimationsView implements PcActionView {

	private Animation currentAnimation;

	public void finishCurrentView() {
		if (currentAnimation == null) return;
		currentAnimation.restart();
		currentAnimation.stop();
		currentAnimation = null;
	}

	public void drawCurrentView(String action, int x, int y) {
		updateCurrentAnimation(action);
		currentAnimation.start();
		currentAnimation.draw(x, y);
	}

	private void updateCurrentAnimation(String action) {
		currentAnimation = AnimationLoader.getAnimation(action);
	}

}
