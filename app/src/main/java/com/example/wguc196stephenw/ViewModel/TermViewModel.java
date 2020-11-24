package com.example.wguc196stephenw.ViewModel;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.wguc196stephenw.Database.TermEntity;
import com.example.wguc196stephenw.Database.TermRepository;
import java.util.List;
public class TermViewModel extends AndroidViewModel {
    private TermRepository repository;
    private LiveData<List<TermEntity>> allTerms;
    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new TermRepository(application);
        allTerms = repository.getAllTerms();
    }
    public void deleteTerm(TermEntity term){
        repository.deleteTerm(term);
    }
    public LiveData<List<TermEntity>> getAllTerms() {
        return allTerms;
    }
}
