package com.example.cardbag.Scan;

import java.util.concurrent.Callable;

public interface CompatCallable<T> extends Callable<T> {
    void onPostExecute(Object result);

    void onPreExecute();
}
