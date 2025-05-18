package Models;

public class EntrevistadoModel {
    private int id;
    private String nome, telefone;

    public EntrevistadoModel() {
        this.id = 0;
        this.nome = "";
        this.telefone = "";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
