package database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import java.util.Map;

@Dao
public interface PesquisaDAO {
    @Insert
    void insertEspontaea(EspontaneaModel espontanea);
    @Insert
    void insertEstimulada(EstimuladaModel estimulada);
    @Insert
    void insertProblema(ProblemaModel problema);

    @Query("SELECT est_id, est_voto, COUNT(*) as count FROM tbEstimulada group BY est_voto order by count desc")
    LiveData<List<ResultadoVoto>> getVotosEstimulados();
    @Query("SELECT esp_candidato, COUNT(*) as count from tbespontanea group by esp_id")
    LiveData<List<ResultadoEspontaneo>> getVotosEspontaneos();
    @Query("SELECT prob_problemaId, prob_nome, COUNT(*) as contagem from tbproblema group by prob_problemaId order by contagem desc")
    LiveData<List<ResultadoProblema>> getEstatisticaProblema();
}
