package owengine.ext.slick;

import java.util.HashMap;
import java.util.Map;

class Utilities {

	private static final Map<Class<?>, Class<?>> wrapperMap = new HashMap<Class<?>, Class<?>>();
	static {
		wrapperMap.put(boolean.class, Boolean.class);
		wrapperMap.put(byte.class, Byte.class);
		wrapperMap.put(short.class, Short.class);
		wrapperMap.put(char.class, Character.class);
		wrapperMap.put(int.class, Integer.class);
		wrapperMap.put(long.class, Long.class);
		wrapperMap.put(float.class, Float.class);
		wrapperMap.put(double.class, Double.class);
	}

	static Class<?> convertToWrapper(Class<?> primitive) {
		return wrapperMap.get(primitive);
	}

}
