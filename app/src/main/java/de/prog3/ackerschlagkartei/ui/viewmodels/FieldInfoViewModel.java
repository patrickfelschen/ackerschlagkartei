package de.prog3.ackerschlagkartei.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class FieldInfoViewModel extends AndroidViewModel {
    private final Application application;

    public FieldInfoViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }
}
