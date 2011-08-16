package owengine.model.util.action;

public class NullAction extends Action {

	public enum Types implements ActionType {
		none
	}

	public static final NullAction INSTANCE = new NullAction();

	private NullAction() {
		super(Types.none, Integer.MAX_VALUE);
	}

	@Override
	public void start() {}

}
