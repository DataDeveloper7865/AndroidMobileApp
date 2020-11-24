package com.example.wguc196stephenw.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseEntity courseEntity);
    @Delete
    void deleteCourse(CourseEntity courseEntity);
    @Query("SELECT * FROM courses WHERE course_id = :courseID")
    CourseEntity getCourseByID(int courseID);
    @Query("SELECT * FROM courses WHERE term_id = :termID")
    LiveData<List<CourseEntity>> getCoursesByTerm(int termID);
    @Query("SELECT * FROM courses ORDER BY course_start_date DESC")
    LiveData<List<CourseEntity>> getAllCourses();
    @Query("DELETE FROM courses")
    int deleteAllCourses();
}
