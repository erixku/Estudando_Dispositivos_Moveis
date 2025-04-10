package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Entrevistados {

        private String nome;
        private String telefone;
        private LocalDate data;
        private LocalTime hora;
        private double latitude;
        private double longitude;

        public String getNome() {
            return nome;
        }

        public String getTelefone() {
            return telefone;
        }

        public LocalDate getData() {
            return data;
        }

        public LocalTime getHora() {
            return hora;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public Entrevistados(String nome, String telefone, LocalDate data, LocalTime hora, double latitude, double longitude) {
            this.nome = nome;
            this.telefone = telefone;
            this.data = data;
            this.hora = hora;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

