package database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbEstimulada")
public class EstimuladaModel {
    @PrimaryKey(autoGenerate = true)
    public int est_id;
    public String est_voto;

    public int getEst_id() {
        return est_id;
    }

    public void setEst_id(int est_id) {
        this.est_id = est_id;
    }

    public String getEst_voto() {
        return est_voto;
    }

    public void setEst_voto(String est_voto) {
        this.est_voto = est_voto;
    }
}
