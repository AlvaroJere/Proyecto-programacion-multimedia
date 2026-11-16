package alvaro.barreto.proyectopmdados;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class TiradaActivity extends AppCompatActivity {

    public static final String EXTRA_TIRADA = "tirada";

    private EditText etJugador;
    private Spinner spDado;
    private TextView tvResultado;

    private final Random random = new Random();
    private boolean haTirado = false;
    private int ultimasCaras;
    private int ultimoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tirada);

        etJugador = findViewById(R.id.etJugador);
        spDado = findViewById(R.id.spDado);
        tvResultado = findViewById(R.id.tvResultado);
        Button btnTirar = findViewById(R.id.btnTirar);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.dados_opciones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDado.setAdapter(adapter);

        btnTirar.setOnClickListener(v -> tirar());
        btnGuardar.setOnClickListener(v -> guardar());
        btnCancelar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void tirar() {
        String opcion = spDado.getSelectedItem().toString(); // "D4", "D6"...
        ultimasCaras = Integer.parseInt(opcion.substring(1));
        ultimoResultado = random.nextInt(ultimasCaras) + 1;
        haTirado = true;
        tvResultado.setText(getString(R.string.resultado_obtenido, ultimoResultado, ultimasCaras));
    }

    private void guardar() {
        String jugador = etJugador.getText().toString().trim();
        if (jugador.isEmpty()) {
            etJugador.setError(getString(R.string.error_nombre));
            return;
        }
        if (!haTirado) {
            Toast.makeText(this, R.string.error_tirar_antes, Toast.LENGTH_SHORT).show();
            return;
        }
        Tirada tirada = new Tirada(jugador, ultimasCaras, ultimoResultado);
        Intent data = new Intent();
        data.putExtra(EXTRA_TIRADA, tirada);
        setResult(RESULT_OK, data);
        finish();
    }
}