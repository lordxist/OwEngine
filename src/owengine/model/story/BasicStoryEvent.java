package owengine.model.story;

import java.util.ArrayList;

import owengine.model.entities.character.PlayerChar;

public abstract class BasicStoryEvent implements StoryEvent {

	@Override
	public void message(ArrayList<String> msg) {
		PlayerChar.getInstance().setCurrentMessage(msg);
		setPaused(true);
	}

}
