package helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time. LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PrefsHelper {

    private static PrefsHelper instance;
    private final SharedPreferences sharedPref;
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

    public PrefsHelper(Context context) {
        sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
    }

    public static synchronized PrefsHelper getInstance(Context context) {
        if(instance == null) {
            instance = new PrefsHelper(context);
        }
        return instance;
    }

    public <T> void saveObject(String key, T object) {
        String json = gson.toJson(object);
        sharedPref.edit().putString(key, json).apply();
    }

    public <T> T getObject(String key, Class<T> classOfT) {
        String json = sharedPref.getString(key, null);
        return gson.fromJson(json, classOfT);
    }

    public <T> void saveList(String key, List<T> list) {
        String json = gson.toJson(list);
        sharedPref.edit().putString(key, json).apply();
    }

    public <T> List<T> getList(String key, Class<T> classOfT) {
        String json = sharedPref.getString(key, null);
        if(json == null) {
            return new ArrayList<>();
        }

        Type type = TypeToken.getParameterized(List.class, classOfT).getType();
        return gson.fromJson(json, type);
    }

}
