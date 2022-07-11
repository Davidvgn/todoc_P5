package com.davidvignon.todoc.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class LiveDataTestUtils {

    public static <T> T getValueForTesting(@NonNull final LiveData<T> liveData) {
        liveData.observeForever(new Observer<T>() {
            @Override
            public void onChanged(T t) {
            }
        });

        return liveData.getValue();
    }
}

