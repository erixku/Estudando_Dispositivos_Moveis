import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {EntrevistadoModel.class, EspontaneaModel.class, EstimuladaModel.class, ProblemaModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EntrevistadoDAO entrevistadoDAO();
    public abstract PesquisaDAO pesquisaDAO();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "pesquisa").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
