package com.davidvignon.todoc.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import static org.junit.Assert.fail;

public class LiveDataTestUtils {

    public static <T> T getValueForTesting(@NonNull final LiveData<T> liveData) {
        liveData.observeForever(t -> {
        });

        return liveData.getValue();
    }
}

