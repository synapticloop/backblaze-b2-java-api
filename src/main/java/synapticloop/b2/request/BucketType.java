package synapticloop.b2.request;

public enum BucketType {
	ALL_PUBLIC("allPublic"),
	ALL_PRIVATE("allPrivate");

	private final String apiName;
	BucketType(String apiName) {
		this.apiName = apiName;
	}

	@Override
	public String toString() {
		return(apiName);
	}
}
