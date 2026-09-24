package com.a48.vitalarma;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PendientesActivity extends AppCompatActivity {

    private static final int[] RECORDATORIOS_VENCIDOS = {
            R.string.cita_medica,
            R.string.comprar_tiquetes,
            R.string.felicitar_diego
    };

    private BottomNavigationView bottomNavigation;

    public static void abrir(Context context) {
        Intent intent = new Intent(context, PendientesActivity.class);

        intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
        );
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendientes);

        configurarBarraSuperior();
        cargarPendientes();
        configurarNavegacion();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (bottomNavigation != null) {
            bottomNavigation.setSelectedItemId(R.id.nav_pendientes);
        }
    }

    private void configurarBarraSuperior() {
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed()
        );
    }

    private void cargarPendientes() {
        LinearLayout lista = findViewById(R.id.listaPendientes);
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int titulo : RECORDATORIOS_VENCIDOS) {
            View tarjeta = inflater.inflate(
                    R.layout.item_recordatorio_vencido,
                    lista,
                    false
            );
            TextView tituloRecordatorio = tarjeta.findViewById(R.id.tituloRecordatorio);
            tituloRecordatorio.setText(titulo);
            lista.addView(tarjeta);
        }
    }

    private void configurarNavegacion() {
        bottomNavigation = findViewById(R.id.bottomNavigation);

        BottomNavigationHelper.configurar(
                bottomNavigation,
                R.id.nav_pendientes
        );

        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_pendientes) {
                return true;
            }

            if (id == R.id.nav_hoy) {
                abrirHoy();
                return true;
            }

            if (id == R.id.nav_crear) {
                startActivity(new Intent(
                        PendientesActivity.this,
                        CrearRecordatorioActivity.class
                ));
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

    private void abrirHoy() {
        Intent intent = new Intent(
                PendientesActivity.this,
                HoyActivity.class
        );

        intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
        );

        startActivity(intent);
        finish();
    }
}
