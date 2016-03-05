package synapticloop.b2.response;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import synapticloop.b2.exception.B2Exception;

public final class B2ListBucketsResponse extends BaseB2Response {
    private final List<B2BucketResponse> buckets;

    public B2ListBucketsResponse(final String json) throws B2Exception {
        super(json);

        buckets = new ArrayList<>();
        JSONArray optJSONArray = response.optJSONArray("buckets");
        for(int i = 0; i < optJSONArray.length(); i++) {
            buckets.add(new B2BucketResponse(optJSONArray.optJSONObject(i)));
        }
    }

    public List<B2BucketResponse> getBuckets() {
        return buckets;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("B2ListBucketsResponse{");
        sb.append("buckets=").append(buckets);
        sb.append('}');
        return sb.toString();
    }
}
