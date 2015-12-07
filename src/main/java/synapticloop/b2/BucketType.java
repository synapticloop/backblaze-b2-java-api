package synapticloop.b2;

public enum BucketType {
	ALL_PUBLIC("allPublic"),
	ALL_PRIVATE("allPrivate");

	private final String bucketType;

	BucketType(String bucketType) {
		this.bucketType = bucketType;
	}

	@Override
	public String toString() {
		return(bucketType);
	}
}
