package com.a48.vitalarma;

import android.content.res.ColorStateList;
import android.graphics.Color;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public final class BottomNavigationHelper {

    private BottomNavigationHelper() {   }

    public static void configurar(
            BottomNavigationView navigation,
            int itemSeleccionado
    ) {
        navigation.setItemActiveIndicatorEnabled(true);

        navigation.setItemActiveIndicatorColor(
                ColorStateList.valueOf(Color.TRANSPARENT)
        );

        navigation.setLabelVisibilityMode(
                NavigationBarView.LABEL_VISIBILITY_LABELED
        );

        navigation.setSelectedItemId(itemSeleccionado);
    }
}