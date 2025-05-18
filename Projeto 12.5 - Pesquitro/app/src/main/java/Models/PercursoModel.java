package Models;

public class PercursoModel {
    private int id;
    private String origem, destino;

    public PercursoModel() {
        this.id = 0;
        this.origem = "";
        this.destino = "";
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
