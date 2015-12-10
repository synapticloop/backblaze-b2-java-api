/**
 * This package contains the lower level interfaces to the API request mechanism.
 * 
 * Each request utilises one or more of the following data structures stored in 
 * the {@link synapticloop.b2.request.BaseB2Request} object:
 * 
 * <ul>
 *   <li>
 *     Map&lt;String, String&gt; headers - these key value pairs are added 
 *     to the request headers
 *   </li>
 *   <li>
 *     Map&lt;String, String&gt; parameters - these key value pairs are
 *     added to the request parameters
 *   </li>
 *   <li>
 *     Map&lt;String, String&gt; stringData - these key value pairs are added
 *     to the body of the request which is a JSONObject with keys to string
 *     values
 *   </li>
 *   <li>
 *     Map&lt;String, Integer&gt; integerData - these key value pairs are added
 *     to the body of the request which is a JSONObject with keys to integer
 *     values
 *   </li>
 * </ul>
 * 
 * @author synapticloop
 *
 */
package synapticloop.b2.request;