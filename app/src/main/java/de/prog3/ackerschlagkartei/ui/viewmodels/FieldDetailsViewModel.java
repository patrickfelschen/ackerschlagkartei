package de.prog3.ackerschlagkartei.ui.viewmodels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.Timestamp;

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
    private final FirestoreRepository firestoreRepository;
    private final WeatherRepository weatherRepository;
    private final StorageRepository storageRepository;

    private MutableLiveData<FieldModel> fieldModelMutableLiveData;
    private final MutableLiveData<String> actionCategory;

    public FieldDetailsViewModel(@NonNull Application application) {
        super(application);

        this.application = application;
        this.firestoreRepository = new FirestoreRepository(application);
        this.weatherRepository = new WeatherRepository(application);
        this.storageRepository = new StorageRepository(application);

        this.actionCategory = new MutableLiveData<>();
    }

    // FIELDMODEL

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

    // WEATHERMODEL

    public void loadWeather(@NonNull FieldModel fieldModel) {
        this.weatherRepository.loadWeather(fieldModel.getInfo().getPositions().get(0));
    }

    public MutableLiveData<WeatherModel> getWeatherMutableLiveData() {
        return this.weatherRepository.getWeatherModelMutableLiveData();
    }

    // ACTIONMODEL

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

    // DOCUMENTMODEL

    public void uploadFieldDocument(Uri contentUri) {
        this.storageRepository.uploadFieldDocument(fieldModelMutableLiveData.getValue().getUid(), contentUri);
        this.firestoreRepository.createDocumentModel(fieldModelMutableLiveData.getValue().getUid(), new DocumentModel("", "", Timestamp.now().toDate()));
    }
}
