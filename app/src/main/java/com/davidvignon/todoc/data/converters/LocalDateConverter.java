package com.davidvignon.todoc.data.converters;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.time.LocalDateTime;

public class LocalDateConverter {
    @TypeConverter
    public static LocalDateTime fromIso(@Nullable String value) {
        return value == null ? null : LocalDateTime.parse(value);
    }

    @TypeConverter
    public static String toLocalDateTime(@Nullable LocalDateTime date) {
        return date == null ? null : date.toString();
    }
}
