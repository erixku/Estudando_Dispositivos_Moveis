package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class EntrevistadoModel {

    private String nome;
    private String telefone;
    private LocalDate data;
    private LocalTime hora;
    private double latitude;
    private double longitude;

    public EntrevistadoModel(String nome, String telefone, LocalDate data, LocalTime hora, double latitude, double longitude) {
        this.nome = nome;
        this.telefone = telefone;
        this.data = data;
        this.hora = hora;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
