package com.a48.vitalarma;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CrearRecordatorioActivity extends AppCompatActivity {

    private TextInputLayout layoutNombre;
    private TextInputLayout layoutFecha;
    private TextInputLayout layoutHora;

    private TextInputEditText inputNombre;
    private TextInputEditText inputFecha;
    private TextInputEditText inputHora;

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_recordatorio);

        obtenerReferencias();
        configurarBarraSuperior();
        configurarValidacionNombre();
        configurarFecha();
        configurarHora();
        configurarDias();
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
        layoutNombre = findViewById(R.id.layoutNombre);
        layoutFecha = findViewById(R.id.layoutFecha);
        layoutHora = findViewById(R.id.layoutHora);

        inputNombre = findViewById(R.id.inputNombre);
        inputFecha = findViewById(R.id.inputFecha);
        inputHora = findViewById(R.id.inputHora);
    }

    private void configurarBarraSuperior() {
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed()
        );
    }

    private void configurarValidacionNombre() {
        inputNombre.setOnFocusChangeListener((view, tieneFoco) -> {
            if (!tieneFoco) {
                validarNombre();
            }
        });

        inputNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence texto,
                    int inicio,
                    int cantidad,
                    int despues
            ) {
                // No hacer nada
            }

            @Override
            public void onTextChanged(
                    CharSequence texto,
                    int inicio,
                    int antes,
                    int cantidad
            ) {
                if (!texto.toString().trim().isEmpty()) {
                    layoutNombre.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //No hago nada
            }
        });
    }

    private boolean validarNombre() {
        Editable contenido = inputNombre.getText();

        String nombre = contenido == null
                ? ""
                : contenido.toString().trim();

        if (nombre.isEmpty()) {
            layoutNombre.setError(
                    getString(R.string.error_nombre_obligatorio)
            );

            inputNombre.requestFocus();
            return false;
        }

        if (nombre.length() > 60) {
            layoutNombre.setError(
                    getString(R.string.error_maximos_caracteres)
            );

            inputNombre.requestFocus();
            return false;
        }

        layoutNombre.setError(null);
        return true;
    }

    private void configurarFecha() {
        inputFecha.setOnClickListener(view -> mostrarSelectorFecha());

        layoutFecha.setEndIconOnClickListener(
                view -> mostrarSelectorFecha()
        );
    }

    private void mostrarSelectorFecha() {
        MaterialDatePicker<Long> selectorFecha =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText(R.string.seleccionar_fecha)
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

        selectorFecha.addOnPositiveButtonClickListener(
                fechaSeleccionada -> {
                    SimpleDateFormat formatoFecha =
                            new SimpleDateFormat(
                                    "d 'de' MMMM 'de' yyyy",
                                    new Locale("es", "CO")
                            );

                    formatoFecha.setTimeZone(
                            TimeZone.getTimeZone("UTC")
                    );

                    String fechaFormateada = formatoFecha.format(
                            new Date(fechaSeleccionada)
                    );

                    inputFecha.setText(fechaFormateada);
                    layoutFecha.setError(null);
                }
        );

        selectorFecha.show(
                getSupportFragmentManager(),
                "SELECTOR_FECHA"
        );
    }

    private void configurarHora() {
        inputHora.setOnClickListener(view -> mostrarSelectorHora());

        layoutHora.setEndIconOnClickListener(
                view -> mostrarSelectorHora()
        );
    }

    private void mostrarSelectorHora() {
        MaterialTimePicker selectorHora =
                new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(15)
                        .setMinute(0)
                        .setTitleText(R.string.seleccionar_hora)
                        .build();

        selectorHora.addOnPositiveButtonClickListener(view -> {
            int hora24 = selectorHora.getHour();
            int minutos = selectorHora.getMinute();

            String periodo = hora24 >= 12
                    ? "p. m."
                    : "a. m.";

            int hora12 = hora24 % 12;

            if (hora12 == 0) {
                hora12 = 12;
            }

            String horaFormateada = String.format(
                    Locale.getDefault(),
                    "%d:%02d %s",
                    hora12,
                    minutos,
                    periodo
            );

            inputHora.setText(horaFormateada);
            layoutHora.setError(null);
        });

        selectorHora.show(
                getSupportFragmentManager(),
                "SELECTOR_HORA"
        );
    }

    private void configurarDias() {
        MaterialButton diaLunes = findViewById(R.id.diaLunes);
        MaterialButton diaMartes = findViewById(R.id.diaMartes);
        MaterialButton diaMiercoles = findViewById(R.id.diaMiercoles);
        MaterialButton diaJueves = findViewById(R.id.diaJueves);
        MaterialButton diaViernes = findViewById(R.id.diaViernes);
        MaterialButton diaSabado = findViewById(R.id.diaSabado);

        diaLunes.setChecked(true);
        diaMartes.setChecked(true);
        diaMiercoles.setChecked(true);
        diaJueves.setChecked(true);
        diaViernes.setChecked(true);
        diaSabado.setChecked(true);
    }

    private void configurarAcciones() {
        MaterialButton btnCancelar = findViewById(R.id.btnCancelar);
        MaterialButton btnContinuar = findViewById(R.id.btnContinuar);

        btnCancelar.setOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed()
        );

        btnContinuar.setOnClickListener(view -> {
            if (!validarNombre()) {
                return;
            }

            Intent intent = new Intent(
                    CrearRecordatorioActivity.this,
                    AnticipacionActivity.class
            );

            startActivity(intent);
        });
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
                volverAHoy();
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

    private void volverAHoy() {
        Intent intent = new Intent(
                CrearRecordatorioActivity.this,
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