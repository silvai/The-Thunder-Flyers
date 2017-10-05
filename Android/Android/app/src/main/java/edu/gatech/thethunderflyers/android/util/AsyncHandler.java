package edu.gatech.thethunderflyers.android.util;

public interface AsyncHandler<T> {
    void handleResponse(T response, Exception ex);
}
