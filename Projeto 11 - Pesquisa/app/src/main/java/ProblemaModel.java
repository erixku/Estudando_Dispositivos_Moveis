import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbProblema")
public class ProblemaModel {
    @PrimaryKey(autoGenerate = true)
    public int prob_id;
    public int prob_problemaId;
    public String prob_nome;
    public int prob_prioridade;

    public int getProb_id() {
        return prob_id;
    }

    public void setProb_id(int prob_id) {
        this.prob_id = prob_id;
    }

    public int getProb_problemaId() {
        return prob_problemaId;
    }

    public void setProb_problemaId(int prob_problemaId) {
        this.prob_problemaId = prob_problemaId;
    }

    public String getProb_nome() {
        return prob_nome;
    }

    public void setProb_nome(String prob_nome) {
        this.prob_nome = prob_nome;
    }

    public int getProb_prioridade() {
        return prob_prioridade;
    }

    public void setProb_prioridade(int prob_prioridade) {
        this.prob_prioridade = prob_prioridade;
    }
}
