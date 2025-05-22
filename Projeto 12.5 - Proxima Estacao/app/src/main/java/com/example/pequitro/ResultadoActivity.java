package com.example.pequitro;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.pie.PieRenderer;
import com.androidplot.xy.XYPlot;

import Helpers.EntrevListAdapter;
import Helpers.PercursoListAdapter;
import DAO.EntrevistadoDAO;
import DAO.PercursoDAO;
import DAO.ServidorCentralDAO;
import Models.EntrevistadoModel;
import Models.PercursoModel;
import Models.ServidorCentralModel;

public class ResultadoActivity extends AppCompatActivity {

    Button btLimparDados, btVoltarLogin2;
    EntrevistadoDAO entrevistadoDAO = new EntrevistadoDAO(this);
    PercursoDAO percursoDAO = new PercursoDAO(this);
    ServidorCentralDAO servidorCentralDAO = new ServidorCentralDAO(this);
    List<EntrevistadoModel> entrevistados = entrevistadoDAO.consultarTodosEntrevistados();
    List<PercursoModel> percursos = percursoDAO.consultarTodosPercursos();
    private PieChart plotTotal;
    private XYPlot plotBarras;
    ListView ltPercursos, ltEntrevistados;
    PercursoListAdapter percursoAdapter;
    EntrevListAdapter entrevAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultado);

        plotTotal = findViewById(R.id.ptTotal);

        Segment s1 = new Segment("1", 10);
        Segment s2 = new Segment("2", 20);
        Segment s3 = new Segment("3", 30);
        Segment s4 = new Segment("4", 40);

        SegmentFormatter sf1 = new SegmentFormatter(Color.rgb(210, 34, 255));
        sf1.getLabelPaint().setTextSize(20f);

        SegmentFormatter sf2 = new SegmentFormatter(Color.rgb(255, 255, 0));
        sf2.getLabelPaint().setTextSize(20f);

        SegmentFormatter sf3 = new SegmentFormatter(Color.rgb(0, 255, 0));
        sf3.getLabelPaint().setTextSize(20f);

        SegmentFormatter sf4 = new SegmentFormatter(Color.rgb(255, 0, 0));
        sf4.getLabelPaint().setTextSize(20f);

        plotTotal.addSegment(s1, sf1);
        plotTotal.addSegment(s2, sf2);
        plotTotal.addSegment(s3, sf3);
        plotTotal.addSegment(s4, sf4);

        PieRenderer pr = plotTotal.getRenderer(PieRenderer.class);
        pr.setDonutSize(0.3f, PieRenderer.DonutMode.PERCENT);
        plotTotal.redraw();

        ltPercursos = findViewById(R.id.ltPercurso);
        ltEntrevistados = findViewById(R.id.ltEntrevistados);

        btLimparDados = findViewById(R.id.btLimparDados);
        btVoltarLogin2 = findViewById(R.id.btVoltarLogin2);

        carregarPercursos();
        carregarEntrevistados();

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
                        servidorCentral.setPer_data(percurso.getData());
                        servidorCentral.setPer_hora(percurso.getHora());

                        servidorCentralDAO.inserirServidorCentral(servidorCentral);
                    }
                }
                entrevistadoDAO.apagarTodosEntrevistados();
                percursoDAO.apagarTodosPercurso();
                carregarPercursos();
                carregarEntrevistados();
            }
        });

        btVoltarLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(ResultadoActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void carregarPercursos() {
        List<PercursoModel> percursos = percursoDAO.consultarTodosPercursos();

        if (percursos != null) { // A lista pode ser vazia, mas não nula
            // Inicializa o adapter apenas se ainda não foi inicializado
            if (percursoAdapter == null) {
                percursoAdapter = new PercursoListAdapter(
                        this,
                        R.layout.list_item_percurso, // Seu layout personalizado para cada item
                        percursos
                );
                ltPercursos.setAdapter(percursoAdapter);
            } else {
                // Se o adapter já existe, apenas limpe e adicione os novos dados
                percursoAdapter.clear();
                percursoAdapter.addAll(percursos);
                percursoAdapter.notifyDataSetChanged(); // Notifica o ListView para redesenhar
            }
        } else {
            Log.d("MainActivity", "Nenhum percurso encontrado ou erro ao consultar.");
            // Opcional: exibir uma mensagem na UI informando que não há dados
        }
    }

    private void carregarEntrevistados() {
        List<EntrevistadoModel> entrevistados = entrevistadoDAO.consultarTodosEntrevistados();

        if (entrevistados != null) { // A lista pode ser vazia, mas não nula
            // Inicializa o adapter apenas se ainda não foi inicializado
            if (entrevAdapter == null) {
                entrevAdapter = new EntrevListAdapter(
                        this,
                        R.layout.list_item_entrev, // Seu layout personalizado para cada item
                        entrevistados
                );
                ltEntrevistados.setAdapter(entrevAdapter);
            } else {
                // Se o adapter já existe, apenas limpe e adicione os novos dados
                entrevAdapter.clear();
                entrevAdapter.addAll(entrevistados);
                entrevAdapter.notifyDataSetChanged(); // Notifica o ListView para redesenhar
            }
        } else {
            Log.d("MainActivity", "Nenhum percurso encontrado ou erro ao consultar.");
            // Opcional: exibir uma mensagem na UI informando que não há dados
        }
    }
}