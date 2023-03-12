package lin.scott.noteapp.persistence;

import androidx.room.TypeConverter;

import java.time.Instant;

public class InstantConverter {

    @TypeConverter
    public static Instant toInstant(Long millis) {
        return Instant.ofEpochMilli(millis);
    }

    @TypeConverter
    public static Long toLong(Instant instant) {
        return instant.toEpochMilli();
    }
}
