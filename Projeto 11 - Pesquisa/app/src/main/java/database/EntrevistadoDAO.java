package database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EntrevistadoDAO {
    @Insert
    void insert(EntrevistadoModel entrevistado);
    @Delete
    void delete(EntrevistadoModel entrevistado);

    @Query("SELECT * FROM tbentrevistados ORDER BY ent_data DESC, ent_hora DESC")
    LiveData<List<EntrevistadoModel>> getTodosEntrevistados();
    @Query("SELECT * FROM tbEntrevistados where ent_id = :ent_id")
    LiveData<EntrevistadoModel> getEntrevistadoPorId(int ent_id);
    @Query("SELECT COUNT(*) FROM tbEntrevistados")
    LiveData<Integer> getTotalEntrevistados();
}
