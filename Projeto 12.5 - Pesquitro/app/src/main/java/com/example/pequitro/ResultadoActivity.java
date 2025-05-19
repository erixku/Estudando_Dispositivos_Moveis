package com.example.pequitro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import DAO.EntrevistadoDAO;
import DAO.PercursoDAO;
import DAO.ServidorCentralDAO;
import Models.EntrevistadoModel;
import Models.PercursoModel;
import Models.ServidorCentralModel;

public class ResultadoActivity extends AppCompatActivity {

    Button btLimparDados;
    EntrevistadoDAO entrevistadoDAO = new EntrevistadoDAO(this);
    PercursoDAO percursoDAO = new PercursoDAO(this);
    ServidorCentralDAO servidorCentralDAO = new ServidorCentralDAO(this);

    List<EntrevistadoModel> entrevistados = entrevistadoDAO.consultarTodosEntrevistados();
    List<PercursoModel> percursos = percursoDAO.consultarTodosPercursos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultado);

        btLimparDados = findViewById(R.id.btLimparDados);

        btLimparDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entrevistados.size() == percursos.size()) {
                    for (int i = 0; i < entrevistados.size(); i++) {
                        EntrevistadoModel entrevistado = entrevistados.get(i);
                        PercursoModel percurso = percursos.get(i);

                        ServidorCentralModel servidorCentral = new ServidorCentralModel();
                        servidorCentral.setEtr_nome(entrevistado.getNome());
                        servidorCentral.setEtr_telefone(entrevistado.getTelefone());
                        servidorCentral.setPer_origem(percurso.getOrigem());
                        servidorCentral.setPer_destino(percurso.getDestino());

                        servidorCentralDAO.inserirServidorCentral(servidorCentral);
                    }
                }
                entrevistadoDAO.apagarTodosEntrevistados();
                percursoDAO.apagarTodosPercurso();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}