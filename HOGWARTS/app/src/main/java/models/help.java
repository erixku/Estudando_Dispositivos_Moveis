package models;


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

public class help {

        private static help instance;
        private final SharedPreferences help;
        private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).registerTypeAdapter(LocalTime.class, new LocalTimeAdapter()).create();

        public help(Context context) {
            help = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        }

        public static synchronized help getInstance(Context context) {
            if(instance == null) {
                instance = new help(context);
            }
            return instance;
        }

        public <T> void saveObject(String key, T object) {
            String json = gson.toJson(object);
            help.edit().putString(key, json).apply();
        }

        public <T> T getObject(String key, Class<T> classOfT) {
            String json = help.getString(key, null);
            return gson.fromJson(json, classOfT);
        }

        public <T> void saveList(String key, List<T> list) {
            String json = gson.toJson(list);
            help.edit().putString(key, json).apply();
        }

        public  <T> List<T> getList(String key, Class<T> classOfT) {
            String json = help.getString(key, null);
            if(json == null) {
                return new ArrayList<>();
            }

            Type type = TypeToken.getParameterized(List.class, classOfT).getType();
            return gson.fromJson(json, type);
        }

        public <T> void addToList(String key, T item, Class<T> classOfT) {
            List<T> listaAtual = getList(key, classOfT);
            listaAtual.add(item);
            saveList(key, listaAtual);
        }

    }
