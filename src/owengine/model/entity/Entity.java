package owengine.model.entity;

import owengine.model.util.position.Positioned;

public interface Entity<T extends Entity<T>> extends Positioned {

	void update(int delta);

}
