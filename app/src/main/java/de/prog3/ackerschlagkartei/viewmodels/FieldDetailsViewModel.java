package de.prog3.ackerschlagkartei.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;

import de.prog3.ackerschlagkartei.models.ActionModel;
import de.prog3.ackerschlagkartei.models.FieldModel;
import de.prog3.ackerschlagkartei.models.WeatherModel;
import de.prog3.ackerschlagkartei.repositories.FirestoreRepository;
import de.prog3.ackerschlagkartei.repositories.WeatherRepository;

public class FieldDetailsViewModel extends AndroidViewModel {

    private final Application application;
    private final FirestoreRepository firestoreRepository;
    private final WeatherRepository weatherRepository;
    private MutableLiveData<FieldModel> fieldModelMutableLiveData;
    private final MutableLiveData<String> actionCategory;

    public FieldDetailsViewModel(@NonNull Application application) {
        super(application);

        this.application = application;
        this.firestoreRepository = new FirestoreRepository(application);
        this.weatherRepository = new WeatherRepository(application);
        this.actionCategory = new MutableLiveData<>();
    }

    public void setFieldModelMutableLiveData(@NonNull String fieldUid) {
        this.fieldModelMutableLiveData = this.firestoreRepository.getFieldMutableLiveData(fieldUid);
    }

    public MutableLiveData<FieldModel> getFieldModelMutableLiveData() {
        return this.fieldModelMutableLiveData;
    }

    public void updateField(String field, Object changes) {
        if (fieldModelMutableLiveData == null) {
            return;
        }
        this.firestoreRepository.updateFieldModel(fieldModelMutableLiveData.getValue(), field, changes);
    }

    public void deleteFieldModel(@NonNull FieldModel fieldModel) {
        this.firestoreRepository.deleteFieldModel(fieldModel);
    }

    public void loadWeather(@NonNull FieldModel fieldModel) {
        this.weatherRepository.loadWeather(fieldModel.getInfo().getPositions().get(0));
    }

    public MutableLiveData<WeatherModel> getWeatherMutableLiveData() {
        return this.weatherRepository.getWeatherModelMutableLiveData();
    }

    public void setActionCategory(String actionCategory) {
        this.actionCategory.setValue(actionCategory);
    }

    public MutableLiveData<String> getActionCategory() {
        return this.actionCategory;
    }

    public void setAction(String action, String category) {
        this.firestoreRepository.createActionModel(fieldModelMutableLiveData.getValue().getUid(), new ActionModel(fieldModelMutableLiveData.getValue().getUid(), action, new Date(), category));
    }

    public MutableLiveData<List<ActionModel>> getActions() {
       return this.firestoreRepository.getActionListMutableLiveData(fieldModelMutableLiveData.getValue().getUid(), actionCategory.getValue());
    }
}
