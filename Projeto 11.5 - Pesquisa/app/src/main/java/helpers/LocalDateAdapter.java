package helpers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

import java.time.LocalDate;
import java.time. LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        return LocalDate.parse(json.getAsString());
    }
}