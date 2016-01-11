package synapticloop.b2;

/**
 * The action for an associated file, either 'hide' or 'upload'
 * 
 * @author synapticloop
 */
public enum Action {
	HIDE("hide"),
	UPLOAD("upload");

	private final String actionType;

	Action(String actionType) {
		this.actionType = actionType;
	}

	@Override
	public String toString() {
		return(actionType);
	}
}
