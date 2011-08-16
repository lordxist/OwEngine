package owengine.model.story;

import owengine.model.util.action.Action;
import owengine.model.util.action.ActionQueue;
import owengine.model.util.action.ActionType;
import owengine.model.util.action.ActionUser;

import java.lang.reflect.Method;

public class EventActionQueue extends ActionQueue {

	@SuppressWarnings("unchecked")
	public <T> T sendToEntity(Object entity, String method, final Object... args) {
		final Method sent = findMethod(entity, method, args);
		if (sent == null) {
			return null;
		}
		if (entity instanceof ActionUser &&
				sent.isAnnotationPresent(EventAction.class)) {
			final ActionUser actionUser = (ActionUser) entity;
			add(new Action(new ActionType(){}, 0) {
				private Action action;
				
				@Override
				public void start() {
					super.start();
					sendMethod(actionUser, sent, args);
					action = actionUser.getAction();
				}
				
				@Override
				public boolean isFinished() {
					return action.isFinished();
				}
			});
			return null;
		} else {
			return (T) sendMethod(entity, sent, args);
		}
	}

	private Method findMethod(Object o, String method, Object[] args) {
		Method search = null;
		for (Method m : o.getClass().getMethods()) {
			if (m.getName().equals(method)
					&& compareArrays(m.getParameterTypes(), getParameterTypes(args))) {
				search = m;
			}
		}
		return search;
	}

	private boolean compareArrays(Object[] array1, Object[] array2) {
		if (array1.length != array2.length) {
			return false;
		} else {
			for (int i = 0; i < array1.length; i++) {
				if (array1[i] != array2[i]) {
					return false;
				}
			}
			return true;
		}
	}

	private Class<?>[] getParameterTypes(Object[] args) {
		Class<?>[] parameterTypes = new Class<?>[args.length];
		int i = 0;
		for (Object o : args) {
			parameterTypes[i++] = o.getClass();
		}
		return parameterTypes;
	}

	private Object sendMethod(Object o, Method sent, Object[] args) {
		try {
			return sent.invoke(o, args);
		} catch (Exception e) {}
		return null;
	}

}
