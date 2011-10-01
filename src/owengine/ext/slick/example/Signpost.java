package owengine.ext.slick.example;

import java.util.ArrayList;
import java.util.Arrays;

import owengine.MapLoader.Params;
import owengine.model.entities.MessageEntity;

public class Signpost extends MessageEntity {

	@Params(names = {"msg"})
	public Signpost(String msg) {
		super(10, 6, new ArrayList<String>(Arrays.asList(new String[]{msg})));
	}

}
