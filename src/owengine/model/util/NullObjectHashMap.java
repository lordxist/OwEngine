package owengine.model.util;

import java.util.HashMap;

public class NullObjectHashMap<K,V> extends HashMap<K,V> {

	private static final long serialVersionUID = -7120396293538099054L;

	private V nullObject;

	public NullObjectHashMap(V nullObject) {
		this.nullObject = nullObject;
	}

	@Override
	public V get(Object key) {
		V value = super.get(key);
		if (value == null) return nullObject;
		else return value;
	}

}
