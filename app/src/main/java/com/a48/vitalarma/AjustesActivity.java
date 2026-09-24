package com.a48.vitalarma;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AjustesActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    public static void abrir(Context context) {
        Intent intent = new Intent(context, AjustesActivity.class);

        intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
        );

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        configurarBarraSuperior();
        configurarNavegacion();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (bottomNavigation != null) {
            bottomNavigation.setSelectedItemId(R.id.nav_ajustes);
        }
    }

    private void configurarBarraSuperior() {
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setNavigationOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void configurarNavegacion() {
        bottomNavigation = findViewById(R.id.bottomNavigation);

        BottomNavigationHelper.configurar(
                bottomNavigation,
                R.id.nav_ajustes
        );

        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_ajustes) {
                return true;
            }

            if (id == R.id.nav_hoy) {
                abrirHoy();
                return true;
            }

            if (id == R.id.nav_pendientes) {
                PendientesActivity.abrir(this);
                return true;
            }

            if (id == R.id.nav_crear) {
                startActivity(new Intent(
                        AjustesActivity.this,
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
                AjustesActivity.this,
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
