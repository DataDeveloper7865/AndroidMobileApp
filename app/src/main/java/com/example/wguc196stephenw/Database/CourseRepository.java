package com.example.wguc196stephenw.Database;
import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;
public class CourseRepository {
    private CourseDAO courseDAO;
    private LiveData<List<CourseEntity>> allCourses;
    public CourseRepository(Application application){
        Database database = Database.getDbInstance(application);
        courseDAO = database.courseDAO();
        allCourses = courseDAO.getAllCourses();
    }
    public void insertCourse(CourseEntity course){
        new InsertCourseAsyncTask(courseDAO).execute(course);
    }
    public void deleteCourse(CourseEntity course){
        new DeleteCourseAsyncTask(courseDAO).execute(course);
    }
    public void deleteAllCourses(){
        new DeleteAllCoursesAsyncTask(courseDAO).execute();
    }
    public LiveData<List<CourseEntity>> getAllCourses(){
        return allCourses;
    }
    public CourseEntity getCourseByID(int courseID){
        return courseDAO.getCourseByID(courseID);
    }
    public LiveData<List<CourseEntity>> getCoursesByTerm(int termID){
        return courseDAO.getCoursesByTerm(termID);
    }
    private static class InsertCourseAsyncTask extends AsyncTask<CourseEntity, Void, Void> {
        private CourseDAO courseDAO;
        private InsertCourseAsyncTask(CourseDAO courseDAO){
            this.courseDAO = courseDAO;
        }
        @Override
        protected Void doInBackground(CourseEntity... courseEntities) {
            courseDAO.insertCourse(courseEntities[0]);
            return null;
        }
    }
    private static class DeleteCourseAsyncTask extends AsyncTask<CourseEntity, Void, Void>{
        private CourseDAO courseDAO;
        private DeleteCourseAsyncTask(CourseDAO courseDAO){
            this.courseDAO = courseDAO;
        }
        @Override
        protected Void doInBackground(CourseEntity... courseEntities) {
            courseDAO.deleteCourse(courseEntities[0]);
            return null;
        }
    }
    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void>{
        private CourseDAO courseDAO;
        private DeleteAllCoursesAsyncTask(CourseDAO courseDAO){
            this.courseDAO = courseDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            courseDAO.deleteAllCourses();
            return null;
        }
    }
}