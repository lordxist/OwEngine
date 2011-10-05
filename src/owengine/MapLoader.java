package owengine;

import java.util.ArrayList;
import java.util.HashMap;

import owengine.model.map.EntityMap;
import owengine.model.story.EventMap;
import owengine.view.GameMapView;
import owengine.view.View;

public interface MapLoader {

	void setModel(String model);
	void setView(String view);
	void setMapsPackage(String mapsPackage);
	HashMap<String, EventMap> loadMaps(ArrayList<View> maps,
			HashMap<EntityMap, GameMapView> mapViews);

}
