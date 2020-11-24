package com.example.wguc196stephenw.Database;
import androidx.room.TypeConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static Long toTimestamp(String dateVal) {
        try {
            Date date = DateConverter.dateFormat.parse(dateVal + TimeZone.getDefault().getDisplayName());
            return date.getTime();
        }
        catch (ParseException e) {
            return 0l;
        }
    }
    public static long nowDate() {
        String currentDate = DateConverter.dateFormat.format(new Date());
        return toTimestamp(currentDate);
    }
}

