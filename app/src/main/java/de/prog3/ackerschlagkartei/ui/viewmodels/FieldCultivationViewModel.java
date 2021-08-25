package de.prog3.ackerschlagkartei.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.data.models.WeatherModel;
import de.prog3.ackerschlagkartei.data.repositories.FirestoreRepository;
import de.prog3.ackerschlagkartei.data.repositories.WeatherRepository;

public class FieldCultivationViewModel extends AndroidViewModel {
    private final Application application;
    private MutableLiveData<FieldModel> selectedField;
    private FirestoreRepository firestoreRepository;
    private WeatherRepository weatherRepository;

    public FieldCultivationViewModel(@NonNull Application application) {
        super(application);

        this.application = application;
        this.firestoreRepository = new FirestoreRepository(application);
        this.weatherRepository = new WeatherRepository(application);

        this.selectedField = new MutableLiveData<>();
    }

    public void setSelectedField(FieldModel selectedField) {
        this.selectedField = firestoreRepository.getFieldMutableLiveData(selectedField.getUid());
    }

    public MutableLiveData<FieldModel> getSelectedField() {
        return this.selectedField;
    }

    public MutableLiveData<FieldModel> getFieldModelMutableLiveData(@NonNull String fieldUid) {
        return this.firestoreRepository.getFieldMutableLiveData(fieldUid);
    }

    public void loadWeather(@NonNull FieldModel fieldModel) {
        this.weatherRepository.loadWeather(fieldModel.getInfo().getPositions().get(0));
    }

    public MutableLiveData<WeatherModel> getWeatherMutableLiveData() {
        return this.weatherRepository.getWeatherModelGetData();
    }

    public void updateField(String field, Object changes) {
        if (selectedField == null) {
            return;
        }
        this.firestoreRepository.updateFieldModel(selectedField.getValue(), field, changes);
    }
}
