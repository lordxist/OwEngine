/**
 * 
 */
package owengine.model.util.action;

public interface ActionUser {
	Action getAction();
	
	public static final ActionUser NULL = new ActionUser() {
		public Action getAction() {
			return new Action(null, 0) {
				public boolean isFinished() {
					return true;
				}
			};
		}
	};
}