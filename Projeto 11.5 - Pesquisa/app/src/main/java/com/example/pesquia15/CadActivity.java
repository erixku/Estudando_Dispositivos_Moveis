package com.example.pesquia15;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDate;
import java.time. LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jakewharton.threetenabp.AndroidThreeTen;

import helpers.LocationHelper;
import helpers.PrefsHelper;
import models.EntrevistadoModel;


public class CadActivity extends AppCompatActivity {

    EditText edNome, edTelefone;
    Button btNova, btVoltar;
    private PrefsHelper prefsHelper;
    private LocationHelper locationHelper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cad);

        AndroidThreeTen.init(CadActivity.this);

        prefsHelper = PrefsHelper.getInstance(CadActivity.this);
        locationHelper = new LocationHelper(CadActivity.this);

        edNome = findViewById(R.id.edNome);
        edTelefone = findViewById(R.id.edTelefone);
        btNova = findViewById(R.id.btNova);
        btVoltar = findViewById(R.id.btVoltar);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);
            return; // Espera o resultado antes de continuar
        }

        btNova.setOnClickListener(new View.OnClickListener() {
            @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
            @Override
            public void onClick(View v) {
                salvar(edNome.getText().toString(), edTelefone.getText().toString(), () -> {
                    Intent nova = new Intent(CadActivity.this, EspActivity.class);
                    startActivity(nova);
                    finish();
                });
            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
            @Override
            public void onClick(View v) {
                salvar(edNome.getText().toString(), edTelefone.getText().toString(), () -> {
                    Intent voltar = new Intent(CadActivity.this, LoginActivity.class);
                    startActivity(voltar);
                    finish();
                });
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void salvar(String nome, String telefone, Runnable onFinish) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDate data = agora.toLocalDate();
        LocalTime hora = agora.toLocalTime();

        Log.d("DEBUG", "Tentando obter a Localização");
        locationHelper.obterLocalizacao(localizacao -> {
            if (localizacao != null) {
                EntrevistadoModel entre = new EntrevistadoModel(
                        nome, telefone, data, hora,
                        localizacao.getLatitude(), localizacao.getLongitude()
                );
                prefsHelper.addToList("ultimo_cad", entre, EntrevistadoModel.class);
            } else {
                Log.d("DEBUG", "Localização nula mesmo com fallback.");
            }
            onFinish.run();
        });
    }
}