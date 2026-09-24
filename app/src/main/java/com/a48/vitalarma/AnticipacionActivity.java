package com.a48.vitalarma;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;

public class AnticipacionActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    private MaterialButton btnCincoMinutos;
    private MaterialButton btnQuinceMinutos;
    private MaterialButton btnUnaHora;

    private Chip chipCincoMinutos;
    private Chip chipQuinceMinutos;
    private Chip chipUnaHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anticipacion);

        obtenerReferencias();
        configurarBarraSuperior();
        configurarAnticipacion();
        configurarAcciones();
        configurarNavegacion();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (bottomNavigation != null) {
            bottomNavigation.setSelectedItemId(R.id.nav_crear);
        }
    }

    private void obtenerReferencias() {
        btnCincoMinutos = findViewById(R.id.btnCincoMinutos);
        btnQuinceMinutos = findViewById(R.id.btnQuinceMinutos);
        btnUnaHora = findViewById(R.id.btnUnaHora);

        chipCincoMinutos = findViewById(R.id.chipCincoMinutos);
        chipQuinceMinutos = findViewById(R.id.chipQuinceMinutos);
        chipUnaHora = findViewById(R.id.chipUnaHora);
    }

    private void configurarBarraSuperior() {
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed()
        );
    }

    private void configurarAnticipacion() {
        MaterialButtonToggleGroup grupo =
                findViewById(R.id.grupoAnticipacion);

        btnCincoMinutos.setChecked(true);
        btnQuinceMinutos.setChecked(false);
        btnUnaHora.setChecked(true);

        actualizarSeleccion();

        grupo.addOnButtonCheckedListener(
                (toggleGroup, checkedId, isChecked) ->
                        actualizarSeleccion()
        );
    }

    private void actualizarSeleccion() {
        actualizarBoton(
                btnCincoMinutos,
                chipCincoMinutos
        );

        actualizarBoton(
                btnQuinceMinutos,
                chipQuinceMinutos
        );

        actualizarBoton(
                btnUnaHora,
                chipUnaHora
        );

        TextView tituloAvisosSeleccionados = findViewById(R.id.tituloAvisosSeleccionados);

        if(hayAvisoSeleccionado())
            tituloAvisosSeleccionados.setVisibility(VISIBLE);
        else
            tituloAvisosSeleccionados.setVisibility(INVISIBLE);
    }

    private void actualizarBoton(
            MaterialButton boton,
            Chip chip
    ) {
        boolean seleccionado = boton.isChecked();

        chip.setVisibility(
                seleccionado ? VISIBLE : View.GONE
        );

        if (seleccionado) {
            boton.setIconResource(R.drawable.ic_check);
            boton.setIconTintResource(R.color.primary_container);
        } else {
            boton.setIcon(null);
        }
    }

    private void configurarAcciones() {
        MaterialButton btnCancelar = findViewById(R.id.btnCancelar);
        MaterialButton btnContinuar = findViewById(R.id.btnContinuar);

        btnCancelar.setOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed()
        );

        btnContinuar.setOnClickListener(view -> {
            if (!hayAvisoSeleccionado()) {
                Toast.makeText(
                        this,
                        R.string.selecciona_un_aviso,
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            Toast.makeText(
                    this,
                    R.string.avisos_guardados,
                    Toast.LENGTH_SHORT
            ).show();

            // Aqui va la logica para pasar a Revisar y confirmar
        });
    }

    private boolean hayAvisoSeleccionado() {
        return btnCincoMinutos.isChecked()
                || btnQuinceMinutos.isChecked()
                || btnUnaHora.isChecked();
    }

    private void configurarNavegacion() {
        bottomNavigation = findViewById(R.id.bottomNavigation);

        BottomNavigationHelper.configurar(
                bottomNavigation,
                R.id.nav_crear
        );

        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_crear) {
                return true;
            }

            if (id == R.id.nav_pendientes) {
                PendientesActivity.abrir(this);
                return true;
            }

            if (id == R.id.nav_ajustes) {
                AjustesActivity.abrir(this);
                return true;
            }

            if (id == R.id.nav_hoy) {
                abrirHoy();
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
                AnticipacionActivity.this,
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