package Models;

public class ServidorCentralModel {
    private int ser_id;
    private String per_origem, per_destino, etr_nome, etr_telefone;

    public ServidorCentralModel() {
        this.ser_id = 0;
        this.per_origem = "";
        this.per_destino = "";
        this.etr_nome = "";
        this.etr_telefone = "";
    }

    public int getSer_id() {
        return ser_id;
    }

    public void setSer_id(int ser_id) {
        this.ser_id = ser_id;
    }

    public String getPer_origem() {
        return per_origem;
    }

    public void setPer_origem(String per_origem) {
        this.per_origem = per_origem;
    }

    public String getPer_destino() {
        return per_destino;
    }

    public void setPer_destino(String per_destino) {
        this.per_destino = per_destino;
    }

    public String getEtr_nome() {
        return etr_nome;
    }

    public void setEtr_nome(String etr_nome) {
        this.etr_nome = etr_nome;
    }

    public String getEtr_telefone() {
        return etr_telefone;
    }

    public void setEtr_telefone(String etr_telefone) {
        this.etr_telefone = etr_telefone;
    }
}
