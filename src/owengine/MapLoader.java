package owengine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;

import owengine.model.map.EntityMap;
import owengine.view.GameMapView;

public interface MapLoader {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.CONSTRUCTOR)
	public @interface Params {
		String[] names();
	}
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface View {
		String name();
	}
	void setModel(String model);
	void setView(String view);
	void setMapsPackage(String mapsPackage);
	<T extends owengine.view.View> HashMap<String, EntityMap> loadMaps(ArrayList<T> maps,
			HashMap<EntityMap, GameMapView> mapViews);

}
