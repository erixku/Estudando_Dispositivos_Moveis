import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbEntrevistados")
public class EntrevistadoModel {
    @PrimaryKey(autoGenerate = true)
    public int ent_id;

    public String ent_nome;
    public String ent_celular;
    public String ent_data;
    public String ent_hora;
    public double ent_latitude;
    public double ent_longitude;

    public int getEnt_id() {
        return ent_id;
    }

    public void setEnt_id(int ent_id) {
        this.ent_id = ent_id;
    }

    public String getEnt_nome() {
        return ent_nome;
    }

    public void setEnt_nome(String ent_nome) {
        this.ent_nome = ent_nome;
    }

    public String getEnt_celular() {
        return ent_celular;
    }

    public void setEnt_celular(String ent_celular) {
        this.ent_celular = ent_celular;
    }

    public String getEnt_data() {
        return ent_data;
    }

    public void setEnt_data(String ent_data) {
        this.ent_data = ent_data;
    }

    public String getEnt_hora() {
        return ent_hora;
    }

    public void setEnt_hora(String ent_hora) {
        this.ent_hora = ent_hora;
    }

    public double getEnt_latitude() {
        return ent_latitude;
    }

    public void setEnt_latitude(double ent_latitude) {
        this.ent_latitude = ent_latitude;
    }

    public double getEnt_longitude() {
        return ent_longitude;
    }

    public void setEnt_longitude(double ent_longitude) {
        this.ent_longitude = ent_longitude;
    }
}
