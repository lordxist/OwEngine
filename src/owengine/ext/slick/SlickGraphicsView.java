package owengine.ext.slick;

import org.newdawn.slick.Graphics;

import owengine.view.View;

public abstract class SlickGraphicsView implements View {

	protected static Graphics g;

	public static void setGraphics(Graphics g) {
		SlickGraphicsView.g = g;
	}

}
