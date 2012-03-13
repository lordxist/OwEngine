package owengine.core.world.impl.controller;

import owengine.core.world.InputController;

public class SimpleInputController implements InputController {

	protected boolean disabled;

	@Override
	public void disable() {
		this.disabled = true;
	}

	@Override
	public void enable() {
		this.disabled = false;
	}

}
