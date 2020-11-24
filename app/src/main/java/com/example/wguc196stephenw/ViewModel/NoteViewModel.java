package com.example.wguc196stephenw.ViewModel;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.wguc196stephenw.Database.NoteEntity;
import com.example.wguc196stephenw.Database.NoteRepository;
import java.util.List;
public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    public LiveData<List<NoteEntity>> allNotes;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }
    public void deleteNote(NoteEntity note){
        repository.deleteNote(note);
    }
    public LiveData<List<NoteEntity>> getNotesByAssessment(int currentAssessmentID){
        return repository.getNotesByAssessment(currentAssessmentID);
    }
}
