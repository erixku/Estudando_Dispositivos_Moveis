package com.example.apptotal;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class IdadeActivity extends AppCompatActivity {
    private EditText edNome, edDataNascimento;
    private Button btCalcular;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_idade);

        edNome = findViewById(R.id.edNome);
        edDataNascimento = findViewById(R.id.edDataNascimento);
        btCalcular = findViewById(R.id.btCalcular);
        tvResultado = findViewById(R.id.tvResultado);

        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularIdade();
            }
        });
    }

    private void calcularIdade() {
        String nome = edNome.getText().toString();
        String dataNascimentoStr = edDataNascimento.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date dataNascimento = sdf.parse(dataNascimentoStr);
            Calendar nascimento = Calendar.getInstance();
            nascimento.setTime(dataNascimento);

            Calendar hoje = Calendar.getInstance();
            long diferencaMillis = hoje.getTimeInMillis() - nascimento.getTimeInMillis();

            long anos = TimeUnit.MILLISECONDS.toDays(diferencaMillis) / 365;
            long meses = (TimeUnit.MILLISECONDS.toDays(diferencaMillis) % 365) / 30;
            long dias = TimeUnit.MILLISECONDS.toDays(diferencaMillis) % 30;
            long horas = TimeUnit.MILLISECONDS.toHours(diferencaMillis);
            long minutos = TimeUnit.MILLISECONDS.toMinutes(diferencaMillis);
            long segundos = TimeUnit.MILLISECONDS.toSeconds(diferencaMillis);

            String resultado = "Oie, " + nome + "!\n" +
                    "Sua idade é:\n" +
                    anos + " anos\n" +
                    meses + " meses\n" +
                    dias + " dias\n" +
                    horas + " horas\n" +
                    minutos + " minutos\n" +
                    segundos + " segundos";

            tvResultado.setText(resultado);

        } catch (Exception e) {
            tvResultado.setText("Erro");
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}