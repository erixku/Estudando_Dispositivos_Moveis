package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Entrevistados {

        private String nome;
        private String telefone;
        private String data;
        private String hora;

        public String getNome() {
            return nome;
        }

        public String getTelefone() {
            return telefone;
        }

        public String getData() {
            return data;
        }

        public String getHora() {
            return hora;
        }

        public Entrevistados(String nome, String telefone, String data, String hora) {
            this.nome = nome;
            this.telefone = telefone;
            this.data = data;
            this.hora = hora;
        }
    }

