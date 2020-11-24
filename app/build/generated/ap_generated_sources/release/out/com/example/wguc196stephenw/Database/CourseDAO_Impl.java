package com.example.wguc196stephenw.Database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CourseDAO_Impl implements CourseDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CourseEntity> __insertionAdapterOfCourseEntity;

  private final EntityDeletionOrUpdateAdapter<CourseEntity> __deletionAdapterOfCourseEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllCourses;

  public CourseDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCourseEntity = new EntityInsertionAdapter<CourseEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `courses` (`course_id`,`course_title`,`course_start_date`,`course_end_date`,`course_status`,`term_id`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CourseEntity value) {
        stmt.bindLong(1, value.getCourse_id());
        if (value.getCourse_title() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCourse_title());
        }
        if (value.getCourse_start_date() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCourse_start_date());
        }
        if (value.getCourse_end_date() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCourse_end_date());
        }
        if (value.getCourse_status() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCourse_status());
        }
        stmt.bindLong(6, value.getTerm_id());
      }
    };
    this.__deletionAdapterOfCourseEntity = new EntityDeletionOrUpdateAdapter<CourseEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `courses` WHERE `course_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CourseEntity value) {
        stmt.bindLong(1, value.getCourse_id());
      }
    };
    this.__preparedStmtOfDeleteAllCourses = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM courses";
        return _query;
      }
    };
  }

  @Override
  public void insertCourse(final CourseEntity courseEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCourseEntity.insert(courseEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCourse(final CourseEntity courseEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCourseEntity.handle(courseEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAllCourses() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllCourses.acquire();
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllCourses.release(_stmt);
    }
  }

  @Override
  public CourseEntity getCourseByID(final int courseID) {
    final String _sql = "SELECT * FROM courses WHERE course_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, courseID);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "course_id");
      final int _cursorIndexOfCourseTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "course_title");
      final int _cursorIndexOfCourseStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "course_start_date");
      final int _cursorIndexOfCourseEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "course_end_date");
      final int _cursorIndexOfCourseStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "course_status");
      final int _cursorIndexOfTermId = CursorUtil.getColumnIndexOrThrow(_cursor, "term_id");
      final CourseEntity _result;
      if(_cursor.moveToFirst()) {
        final String _tmpCourse_title;
        _tmpCourse_title = _cursor.getString(_cursorIndexOfCourseTitle);
        final String _tmpCourse_start_date;
        _tmpCourse_start_date = _cursor.getString(_cursorIndexOfCourseStartDate);
        final String _tmpCourse_end_date;
        _tmpCourse_end_date = _cursor.getString(_cursorIndexOfCourseEndDate);
        final String _tmpCourse_status;
        _tmpCourse_status = _cursor.getString(_cursorIndexOfCourseStatus);
        final int _tmpTerm_id;
        _tmpTerm_id = _cursor.getInt(_cursorIndexOfTermId);
        _result = new CourseEntity(_tmpCourse_title,_tmpCourse_start_date,_tmpCourse_end_date,_tmpCourse_status,_tmpTerm_id);
        final int _tmpCourse_id;
        _tmpCourse_id = _cursor.getInt(_cursorIndexOfCourseId);
        _result.setCourse_id(_tmpCourse_id);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<CourseEntity>> getCoursesByTerm(final int termID) {
    final String _sql = "SELECT * FROM courses WHERE term_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, termID);
    return __db.getInvalidationTracker().createLiveData(new String[]{"courses"}, false, new Callable<List<CourseEntity>>() {
      @Override
      public List<CourseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "course_id");
          final int _cursorIndexOfCourseTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "course_title");
          final int _cursorIndexOfCourseStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "course_start_date");
          final int _cursorIndexOfCourseEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "course_end_date");
          final int _cursorIndexOfCourseStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "course_status");
          final int _cursorIndexOfTermId = CursorUtil.getColumnIndexOrThrow(_cursor, "term_id");
          final List<CourseEntity> _result = new ArrayList<CourseEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CourseEntity _item;
            final String _tmpCourse_title;
            _tmpCourse_title = _cursor.getString(_cursorIndexOfCourseTitle);
            final String _tmpCourse_start_date;
            _tmpCourse_start_date = _cursor.getString(_cursorIndexOfCourseStartDate);
            final String _tmpCourse_end_date;
            _tmpCourse_end_date = _cursor.getString(_cursorIndexOfCourseEndDate);
            final String _tmpCourse_status;
            _tmpCourse_status = _cursor.getString(_cursorIndexOfCourseStatus);
            final int _tmpTerm_id;
            _tmpTerm_id = _cursor.getInt(_cursorIndexOfTermId);
            _item = new CourseEntity(_tmpCourse_title,_tmpCourse_start_date,_tmpCourse_end_date,_tmpCourse_status,_tmpTerm_id);
            final int _tmpCourse_id;
            _tmpCourse_id = _cursor.getInt(_cursorIndexOfCourseId);
            _item.setCourse_id(_tmpCourse_id);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<CourseEntity>> getAllCourses() {
    final String _sql = "SELECT * FROM courses ORDER BY course_start_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"courses"}, false, new Callable<List<CourseEntity>>() {
      @Override
      public List<CourseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "course_id");
          final int _cursorIndexOfCourseTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "course_title");
          final int _cursorIndexOfCourseStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "course_start_date");
          final int _cursorIndexOfCourseEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "course_end_date");
          final int _cursorIndexOfCourseStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "course_status");
          final int _cursorIndexOfTermId = CursorUtil.getColumnIndexOrThrow(_cursor, "term_id");
          final List<CourseEntity> _result = new ArrayList<CourseEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CourseEntity _item;
            final String _tmpCourse_title;
            _tmpCourse_title = _cursor.getString(_cursorIndexOfCourseTitle);
            final String _tmpCourse_start_date;
            _tmpCourse_start_date = _cursor.getString(_cursorIndexOfCourseStartDate);
            final String _tmpCourse_end_date;
            _tmpCourse_end_date = _cursor.getString(_cursorIndexOfCourseEndDate);
            final String _tmpCourse_status;
            _tmpCourse_status = _cursor.getString(_cursorIndexOfCourseStatus);
            final int _tmpTerm_id;
            _tmpTerm_id = _cursor.getInt(_cursorIndexOfTermId);
            _item = new CourseEntity(_tmpCourse_title,_tmpCourse_start_date,_tmpCourse_end_date,_tmpCourse_status,_tmpTerm_id);
            final int _tmpCourse_id;
            _tmpCourse_id = _cursor.getInt(_cursorIndexOfCourseId);
            _item.setCourse_id(_tmpCourse_id);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
