package owengine.ext.slick.example;

import owengine.ext.slick.SlickGraphicsView;
import owengine.model.entities.character.PlayerChar;
import owengine.view.CharView;

public class SignpostView extends SlickGraphicsView {

	private Signpost signpost;

	public SignpostView(Signpost signpost) {
		this.signpost = signpost;
	}

	@Override
	public void draw(int width, int height) {
		PlayerChar pc = PlayerChar.getInstance();
		int x = width/2-(CharView.getScreenX(pc)-signpost.getPos().x*CharView.getFieldSize());
		int y = height/2-(CharView.getScreenY(pc)-signpost.getPos().y*CharView.getFieldSize());
		g.drawRect(x, y, CharView.getFieldSize(), CharView.getFieldSize());
	}

}
