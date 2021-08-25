package de.prog3.ackerschlagkartei.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class FieldActionsViewModel extends AndroidViewModel {
    private final Application application;
    private final MutableLiveData<String> actionCategory;

    public FieldActionsViewModel(@NonNull Application application) {
        super(application);
        this.application = application;

        this.actionCategory = new MutableLiveData<>();
    }

    public void setActionCategory(String actionCategory) {
        this.actionCategory.setValue(actionCategory);
    }

    public MutableLiveData<String> getActionCategory() {
        return this.actionCategory;
    }
}
