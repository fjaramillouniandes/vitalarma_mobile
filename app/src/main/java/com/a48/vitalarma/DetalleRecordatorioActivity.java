package com.a48.vitalarma;

import android.content.Context;
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
import com.google.android.material.card.MaterialCardView;

public class DetalleRecordatorioActivity extends AppCompatActivity {

    /*
     * Indica si se abrió desde la tarjeta "Activo" (Después)
     * o desde la tarjeta "Próximo" (Próxima alerta) de Hoy.
     */
    private static final String EXTRA_ACTIVO = "extra_activo";

    private MaterialCardView cardAdvertencia;
    private MaterialCardView cardPermisosListos;

    public static Intent crearIntent(Context context, boolean activo) {
        Intent intent = new Intent(
                context,
                DetalleRecordatorioActivity.class
        );

        intent.putExtra(EXTRA_ACTIVO, activo);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_recordatorio);

        configurarBarraSuperior();
        configurarRecordatorio();
        configurarPermisos();
        configurarAcciones();
        configurarNavegacion();
    }

    private void configurarBarraSuperior() {
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed()
        );
    }

    private void configurarRecordatorio() {
        boolean activo = getIntent().getBooleanExtra(EXTRA_ACTIVO, false);

        if (!activo) {
            return;
        }

        View lineaEstado = findViewById(R.id.lineaEstado);
        MaterialCardView chipEstado = findViewById(R.id.chipEstado);
        TextView estado = findViewById(R.id.estadoRecordatorio);
        TextView hora = findViewById(R.id.horaRecordatorio);

        lineaEstado.setBackgroundResource(R.color.secondary_container);

        chipEstado.setCardBackgroundColor(
                ContextCompat.getColor(this, R.color.success_container)
        );

        estado.setText(R.string.estado_activo);
        estado.setTextColor(
                ContextCompat.getColor(this, R.color.on_success_container)
        );

        hora.setText(R.string.hora_cita_hoy);
    }

    private void configurarPermisos() {
        cardAdvertencia = findViewById(R.id.cardAdvertencia);
        cardPermisosListos = findViewById(R.id.cardPermisosListos);

        MaterialButton btnActivar = findViewById(R.id.btnActivarPermiso);

        /*
         * Prototipo: al activar el permiso se cambia
         * la advertencia por el banner "Permisos listos".
         */
        btnActivar.setOnClickListener(view -> {
            cardAdvertencia.setVisibility(View.GONE);
            cardPermisosListos.setVisibility(View.VISIBLE);

            Toast.makeText(
                    this,
                    R.string.permisos_activados,
                    Toast.LENGTH_SHORT
            ).show();
        });
    }

    private void configurarAcciones() {
        MaterialButton btnCompletar = findViewById(R.id.btnCompletar);

        btnCompletar.setOnClickListener(view -> {
            Toast.makeText(
                    this,
                    R.string.recordatorio_completado,
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        });

        int[] accionesPendientes = {
                R.id.btnPosponer,
                R.id.btnReprogramar
        };

        for (int id : accionesPendientes) {
            findViewById(id).setOnClickListener(view -> mostrarVistaPendiente());
        }
    }

    private void configurarNavegacion() {
        BottomNavigationView bottomNavigation =
                findViewById(R.id.bottomNavigation);

        BottomNavigationHelper.configurar(
                bottomNavigation,
                R.id.nav_hoy
        );

        /*
         * El detalle pertenece a la sección Hoy, por eso
         * Hoy se mantiene seleccionado y al tocarlo se regresa.
         * Se retorna false para no cambiar la selección al abrir otra vista.
         */
        bottomNavigation.setOnItemReselectedListener(item -> abrirHoy());

        bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_pendientes) {
                PendientesActivity.abrir(this);
                return false;
            }

            if (item.getItemId() == R.id.nav_crear) {
                startActivity(new Intent(
                        DetalleRecordatorioActivity.this,
                        CrearRecordatorioActivity.class
                ));
                return false;
            }

            mostrarVistaPendiente();

            return false;
        });
    }

    private void mostrarVistaPendiente() {
        Toast.makeText(
                this,
                R.string.vista_pendiente,
                Toast.LENGTH_SHORT
        ).show();
    }

    private void abrirHoy() {
        Intent intent = new Intent(
                DetalleRecordatorioActivity.this,
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
