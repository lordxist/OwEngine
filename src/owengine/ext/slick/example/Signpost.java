package owengine.ext.slick.example;

import owengine.meta.Params;
import owengine.model.entities.MessageEntity;

public class Signpost extends MessageEntity {

	@Params(names = {"msg"})
	public Signpost(String msg) {
		super(msg);
	}

}
