package owengine.model.entity;

import owengine.model.util.position.Positioned;

public interface Entity extends Positioned {

	void update(int delta);

}
