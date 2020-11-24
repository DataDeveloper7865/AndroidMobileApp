package com.example.wguc196stephenw.ViewModel;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.wguc196stephenw.Database.MentorEntity;
import com.example.wguc196stephenw.Database.MentorRepository;
import java.util.List;
public class MentorViewModel extends AndroidViewModel {
    private MentorRepository repository;
    public LiveData<List<MentorEntity>> allMentors;
    public MentorViewModel(@NonNull Application application) {
        super(application);
        repository = new MentorRepository(application);
        allMentors = repository.getAllMentors();
    }
    public void deleteMentor(MentorEntity mentor){
        repository.deleteMentor(mentor);
    }
    public LiveData<List<MentorEntity>> getMentorByCourse(int currentCourseID){
        return repository.getMentorByCourse(currentCourseID);
    }
}
