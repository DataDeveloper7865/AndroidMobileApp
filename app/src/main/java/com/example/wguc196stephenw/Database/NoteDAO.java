package com.example.wguc196stephenw.Database;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
@Dao
public interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteEntity noteEntity);
    @Delete
    void deleteNote(NoteEntity noteEntity);
    @Query("SELECT * FROM notes WHERE note_id = :noteID")
    NoteEntity getNoteByID(int noteID);
    @Query("SELECT * FROM notes ORDER BY note_title DESC")
    LiveData<List<NoteEntity>> getAllNotes();
    @Query("SELECT * FROM notes WHERE assessment_id = :assessmentID")
    LiveData<List<NoteEntity>> getNoteByAssessment(int assessmentID);
    @Query("DELETE FROM notes")
    int deleteAllNotes();
}
