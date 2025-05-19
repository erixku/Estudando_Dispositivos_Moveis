package Helpers;

import Models.LocalizacaoModel;
import Models.PercursoModel;

public class PercursoInnerLocalizacao {
    private PercursoModel percurso;
    private LocalizacaoModel origem;
    private LocalizacaoModel destino;

    public PercursoInnerLocalizacao(PercursoModel percurso, LocalizacaoModel origem, LocalizacaoModel destino) {
        this.percurso = percurso;
        this.origem = origem;
        this.destino = destino;
    }

    public PercursoModel getPercurso() {
        return percurso;
    }

    public LocalizacaoModel getOrigem() {
        return origem;
    }

    public LocalizacaoModel getDestino() {
        return destino;
    }
}
