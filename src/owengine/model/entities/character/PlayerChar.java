package owengine.model.entities.character;

import java.util.ArrayList;

import owengine.model.map.EntityMap;

public class PlayerChar extends Char  {

	private static PlayerChar instance;

	public static PlayerChar getInstance() {
		return instance;
	}

	private ArrayList<String> currentMessage;

	public PlayerChar(EntityMap map, int x, int y) {
		super(map, x, y);
		instance = this;
	}

	public void tap() {
		if (!isInactive()) {
			return;
		}
		ArrayList<String> result = getEntityNextTo().explore();
		if (result != null) {
			setCurrentMessage(result);
		}
	}

	public void setCurrentMessage(ArrayList<String> currentMessage) {
		this.currentMessage = currentMessage;
	}

	public ArrayList<String> removeCurrentMessage() {
		ArrayList<String> message = currentMessage;
		currentMessage = null;
		return message;
	}


}
