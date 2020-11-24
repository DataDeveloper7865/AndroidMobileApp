package com.example.wguc196stephenw.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
@Dao
public interface MentorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMentor(MentorEntity mentorEntity);
    @Delete
    void deleteMentor(MentorEntity mentorEntity);
    @Query("SELECT * FROM mentors WHERE mentor_id= :mentorID")
    MentorEntity getMentorByID(int mentorID);
    @Query("SELECT * FROM mentors WHERE course_id = :courseID")
    LiveData<List<MentorEntity>> getMentorByCourse(int courseID);
    @Query("SELECT * FROM mentors ORDER BY mentor_name DESC")
    LiveData<List<MentorEntity>> getAllMentors();
    @Query("DELETE FROM mentors")
    int deleteAllMentors();
}
