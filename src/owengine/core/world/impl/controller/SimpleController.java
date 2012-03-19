package owengine.core.world.impl.controller;

import owengine.core.world.Controller;

public abstract class SimpleController implements Controller {

	private boolean disabled;

	@Override
	public void disable() {
		this.disabled = true;
	}

	@Override
	public void enable() {
		this.disabled = false;
	}

	protected boolean isDisabled() {
		return disabled;
	}

}
