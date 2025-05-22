package com.example.pequitro;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYSeriesFormatter;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.pie.PieRenderer;

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
    private PieChart plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultado);

        plot = findViewById(R.id.gpPizza);

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

        plot.addSegment(s1, sf1);
        plot.addSegment(s2, sf2);
        plot.addSegment(s3, sf3);
        plot.addSegment(s4, sf4);

        PieRenderer pr = plot.getRenderer(PieRenderer.class);
        pr.setDonutSize(0.3f, PieRenderer.DonutMode.PERCENT);
        plot.redraw();

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