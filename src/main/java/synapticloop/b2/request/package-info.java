/**
 * This package contains the lower level interfaces to the API request mechanism.
 * 
 * Each request utilises one or more of the following data structures stored in 
 * the {@link synapticloop.b2.request.BaseB2Request} object:
 * 
 * <ul>
 *   <li>
 *     Map&lt;String, String&gt; requestHeaders - these key value pairs are added 
 *     to the request headers
 *   </li>
 *   <li>
 *     Map&lt;String, String&gt; requestParameters - these key value pairs are
 *     added to the request parameters
 *   </li>
 *   <li>
 *     Map&lt;String, Object&gt; requestBodyData - these key value pairs are added
 *     to the body of the request which is a JSONObject with keys to object
 *     values
 *   </li>
 * </ul>
 * 
 * @author synapticloop
 *
 */
package synapticloop.b2.request;