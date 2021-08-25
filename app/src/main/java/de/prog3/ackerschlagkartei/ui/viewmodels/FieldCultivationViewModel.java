package de.prog3.ackerschlagkartei.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class FieldCultivationViewModel extends AndroidViewModel {
    private final Application application;

    public FieldCultivationViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }
}
