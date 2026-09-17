package com.a48.vitalarma;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class HoyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoy);

        MaterialButton btnCrear = findViewById(R.id.btnCrearRecordatorio);
        BottomNavigationView navigation = findViewById(R.id.bottomNavigation);

        //Marca Hoy como seleccionado en el menú
        BottomNavigationHelper.configurar(
                navigation,
                R.id.nav_hoy
        );

        btnCrear.setOnClickListener(view -> abrirCrear());

        navigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_hoy) return true;
            if (id == R.id.nav_crear) {
                abrirCrear();
                return true;
            }
            Toast.makeText(this, R.string.vista_pendiente, Toast.LENGTH_SHORT).show();
            return false;
        });
    }

    private void abrirCrear() {

    }
}