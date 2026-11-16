package alvaro.barreto.proyectopmdados;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<Tirada> historial = new ArrayList<>();

    private LinearLayout contenedor;
    private TextView tvVacio;
    private TextView tvTotal;

    private final ActivityResultLauncher<Intent> launcherTirada =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Tirada tirada = leerTirada(result.getData());
                    if (tirada != null) {
                        historial.add(tirada);
                        mostrarHistorial();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contenedor = findViewById(R.id.contenedorTiradas);
        tvVacio = findViewById(R.id.tvVacio);
        tvTotal = findViewById(R.id.tvTotal);
        Button btnNueva = findViewById(R.id.btnNuevaTirada);
        Button btnBorrar = findViewById(R.id.btnBorrar);

        btnNueva.setOnClickListener(v ->
                launcherTirada.launch(new Intent(this, TiradaActivity.class)));

        btnBorrar.setOnClickListener(v -> {
            historial.clear();
            mostrarHistorial();
        });

        mostrarHistorial();
    }

    @SuppressWarnings("deprecation")
    private Tirada leerTirada(Intent data) {
        if (Build.VERSION.SDK_INT >= 33) {
            return data.getSerializableExtra(TiradaActivity.EXTRA_TIRADA, Tirada.class);
        }
        return (Tirada) data.getSerializableExtra(TiradaActivity.EXTRA_TIRADA);
    }

    private void mostrarHistorial() {
        contenedor.removeAllViews();

        for (Tirada t : historial) {
            View item = getLayoutInflater().inflate(R.layout.item_tirada, contenedor, false);
            ((TextView) item.findViewById(R.id.tvItemJugador))
                    .setText(getString(R.string.item_jugador, t.getJugador()));
            ((TextView) item.findViewById(R.id.tvItemDado))
                    .setText(getString(R.string.item_dado, t.getCaras()));
            ((TextView) item.findViewById(R.id.tvItemResultado))
                    .setText(getString(R.string.item_resultado, t.getResultado()));
            contenedor.addView(item);
        }

        tvVacio.setVisibility(historial.isEmpty() ? View.VISIBLE : View.GONE);
        tvTotal.setText(getString(R.string.total_tiradas, historial.size()));
    }
}