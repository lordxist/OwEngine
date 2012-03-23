package owengine.core;

import owengine.core.world.ControlEntityEvent;
import owengine.core.world.Entity;
import owengine.core.world.GameMap;

public abstract class MapLoader {

	public class ModelException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5178619077903616573L;

	}

	private Package modelPackage;

	public abstract void load(String mapName, GameMap gameMap);

	public abstract String[] getMapNames();

	public Package getModelPackage() {
		return modelPackage;
	}

	public void setModelPackage(Package modelPackage) {
		this.modelPackage = modelPackage;
	}

	protected Entity loadModel(String className, int id) throws ModelException {
		Object entity;
		try {
			entity = Class.forName(className).getConstructor(int.class).newInstance(id);
		} catch (Exception e) {
			throw new ModelException();
		}
		if (!(entity instanceof Entity)) {
			throw new ModelException();
		}
		return (Entity) entity;
	}

	protected ControlEntityEvent loadControlEvent(String className, Entity entity)
			throws ModelException {
		try {
			String name = className.toLowerCase()+"_"+entity.getId();
			Object object =
				Class.forName(className).getConstructor(String.class).newInstance(name);
			if (!(object instanceof ControlEntityEvent)) {
				throw new ModelException();
			}
			ControlEntityEvent event = (ControlEntityEvent) object;
			event.setEntity(entity);
			return event;
		} catch (Exception e) {
			throw new ModelException();
		}
	}

}
