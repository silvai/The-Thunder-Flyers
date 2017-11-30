package edu.gatech.thethunderflyers.ratapp.util;

/**
 * AsyncHandler: Interface to allow asynchronous acceptance (in an Activity) of an API response
 * @param <T> the type of the object being returned from the API (either APIMessage or List<RatData>
 */
public interface AsyncHandler<T> {
    /**
     * Function prototype to be implemented in an Activity. Handles the API response.
     * @param response the object being returned from the API
     */
    void handleResponse(T response);
}
