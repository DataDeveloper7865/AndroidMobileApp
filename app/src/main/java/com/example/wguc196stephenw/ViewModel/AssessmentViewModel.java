package com.example.wguc196stephenw.ViewModel;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.wguc196stephenw.Database.AssessmentEntity;
import com.example.wguc196stephenw.Database.AssessmentRepository;
import java.util.List;
public class AssessmentViewModel extends AndroidViewModel {
    private AssessmentRepository repository;
    public LiveData<List<AssessmentEntity>> allAssessments;
    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repository = new AssessmentRepository(application);
        allAssessments = repository.getAllAssessments();
    }
    public void deleteAssessment(AssessmentEntity assessment){
        repository.deleteAssessment(assessment);
    }
    public void deleteAllAssessments(){
        repository.deleteAllAssessments();
    }
    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return allAssessments;
    }
    public LiveData<List<AssessmentEntity>> getAssessmentByCourse(int currentCourseID){
        return repository.getAssessmentByCourse(currentCourseID);
    }
}
