package synapticloop.b2;

public enum ActionType {
	HIDE("hide"),
	UPLOAD("upload");

	private final String actionType;

	ActionType(String actionType) {
		this.actionType = actionType;
	}

	@Override
	public String toString() {
		return(actionType);
	}
}
