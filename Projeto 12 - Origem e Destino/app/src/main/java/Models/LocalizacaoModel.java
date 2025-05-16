package Models;

public class LocalizacaoModel {
    private int id;
    private String nome;
    private float latitude, longitude;

    public LocalizacaoModel(){
        this.id = 0;
        this.nome = "";
        this.latitude = 0;
        this.longitude = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
