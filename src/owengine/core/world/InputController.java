package owengine.core.world;

public interface InputController {

	InputController NULL = new InputController() {
		@Override
		public void disable() {}
		
		@Override
		public void enable() {}
	};

	void disable();

	void enable();

}
