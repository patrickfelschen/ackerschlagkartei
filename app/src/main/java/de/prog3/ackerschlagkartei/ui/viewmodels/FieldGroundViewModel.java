package de.prog3.ackerschlagkartei.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class FieldGroundViewModel extends AndroidViewModel {
    private final Application application;

    public FieldGroundViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }
}
