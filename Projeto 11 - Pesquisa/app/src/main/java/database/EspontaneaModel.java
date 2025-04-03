package database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbEspontanea")
public class EspontaneaModel {
    @PrimaryKey(autoGenerate = true)
    public int esp_id;
    public String esp_candidato;

    public int getEsp_id() {
        return esp_id;
    }

    public void setEsp_id(int esp_id) {
        this.esp_id = esp_id;
    }

    public String getEsp_candidato() {
        return esp_candidato;
    }

    public void setEsp_candidato(String esp_candidato) {
        this.esp_candidato = esp_candidato;
    }
}
