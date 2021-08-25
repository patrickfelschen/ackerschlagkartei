package de.prog3.ackerschlagkartei.ui.viewmodels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;

import de.prog3.ackerschlagkartei.data.models.ActionModel;
import de.prog3.ackerschlagkartei.data.models.DocumentModel;
import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.data.models.WeatherModel;
import de.prog3.ackerschlagkartei.data.repositories.FirestoreRepository;
import de.prog3.ackerschlagkartei.data.repositories.StorageRepository;
import de.prog3.ackerschlagkartei.data.repositories.WeatherRepository;

public class FieldDetailsViewModel extends AndroidViewModel {
    private final Application application;
    private MutableLiveData<FieldModel> fieldModelMutableLiveData;

    private final MutableLiveData<FieldModel> selectedFieldModel;

    public FieldDetailsViewModel(@NonNull Application application) {
        super(application);

        this.application = application;
        this.selectedFieldModel = new MutableLiveData<>();
    }

    public void setSelectedFieldModel(FieldModel selectedFieldModel) {
        this.selectedFieldModel.postValue(selectedFieldModel);
    }

    public MutableLiveData<FieldModel> getSelectedFieldModel() {
        return this.selectedFieldModel;
    }

}
