package com.example.conversor;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText edNumero;
    Spinner spBaseOrigem, spBaseDestino;
    Button btConverter;
    TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNumero = findViewById(R.id.edNumero);
        spBaseOrigem = findViewById(R.id.spBaseOrigem);
        spBaseDestino = findViewById(R.id.spBaseDestino);
        btConverter = findViewById(R.id.btConverter);
        tvResultado = findViewById(R.id.tvResultado);

        String[] bases = {"Decimal", "Binário", "Octal", "Hexadecimal"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bases);
        spBaseOrigem.setAdapter(adapter);
        spBaseDestino.setAdapter(adapter);

        btConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                converterNumero();
            }
        });
    }

    private void converterNumero() {
        String numero = edNumero.getText().toString().trim();
        if (numero.isEmpty()) {
            Toast.makeText(this, "Digite um número", Toast.LENGTH_SHORT).show();
            return;
        }

        String baseOrigem = spBaseOrigem.getSelectedItem().toString();
        String baseDestino = spBaseDestino.getSelectedItem().toString();
        String resultado = "";

        try {
            int numeroConvertido;

            switch (baseOrigem) {
                case "Decimal":
                    numeroConvertido = Integer.parseInt(numero);
                    break;
                case "Binário":
                    numeroConvertido = Integer.parseInt(numero, 2);
                    break;
                case "Octal":
                    numeroConvertido = Integer.parseInt(numero, 8);
                    break;
                case "Hexadecimal":
                    numeroConvertido = Integer.parseInt(numero, 16);
                    break;
                default:
                    Toast.makeText(this, "Base de origem inválida", Toast.LENGTH_SHORT).show();
                    return;
            }

            switch (baseDestino) {
                case "Decimal":
                    resultado = String.valueOf(numeroConvertido);
                    break;
                case "Binário":
                    resultado = Integer.toBinaryString(numeroConvertido);
                    break;
                case "Octal":
                    resultado = Integer.toOctalString(numeroConvertido);
                    break;
                case "Hexadecimal":
                    resultado = Integer.toHexString(numeroConvertido).toUpperCase();
                    break;
                default:
                    Toast.makeText(this, "Base de destino inválida", Toast.LENGTH_SHORT).show();
                    return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Número inválido para a base selecionada", Toast.LENGTH_SHORT).show();
            return;
        }

        tvResultado.setText("Resultado: " + resultado);
    }
}
