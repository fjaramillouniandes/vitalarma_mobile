package com.a48.vitalarma;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class HoyActivity extends AppCompatActivity {

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoy);

        configurarNavegacion();
        configurarBotonCrear();
        configurarTarjetas();
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*
         * HoyActivity puede ser reutilizada desde la pila.
         * Por eso restablecemos su item seleccionado.
         */
        if (navigation != null) {
            navigation.setSelectedItemId(R.id.nav_hoy);
        }
    }

    private void configurarBotonCrear() {
        MaterialButton btnCrear =
                findViewById(R.id.btnCrearRecordatorio);

        btnCrear.setOnClickListener(
                view -> abrirCrearRecordatorio()
        );
    }

    private void configurarTarjetas() {
        MaterialCardView cardProximaAlerta =
                findViewById(R.id.cardProximaAlerta);
        MaterialCardView cardRecordatorioActivo =
                findViewById(R.id.cardRecordatorioActivo);

        cardProximaAlerta.setOnClickListener(
                view -> abrirDetalleRecordatorio(false)
        );

        cardRecordatorioActivo.setOnClickListener(
                view -> abrirDetalleRecordatorio(true)
        );
    }

    private void configurarNavegacion() {
        /*
         * Aquí se asigna la propiedad de la clase.
         * No escribas nuevamente:
         * BottomNavigationView navigation = ...
         */
        navigation = findViewById(R.id.bottomNavigation);

        BottomNavigationHelper.configurar(
                navigation,
                R.id.nav_hoy
        );

        navigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_hoy) {
                return true;
            }

            if (id == R.id.nav_crear) {
                abrirCrearRecordatorio();
                return true;
            }

            Toast.makeText(
                    this,
                    R.string.vista_pendiente,
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        });
    }

    private void abrirDetalleRecordatorio(boolean activo) {
        startActivity(
                DetalleRecordatorioActivity.crearIntent(this, activo)
        );
    }

    private void abrirCrearRecordatorio() {
        Intent intent = new Intent(
                HoyActivity.this,
                CrearRecordatorioActivity.class
        );

        startActivity(intent);
    }
}