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
public final class AssessmentDAO_Impl implements AssessmentDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AssessmentEntity> __insertionAdapterOfAssessmentEntity;

  private final EntityDeletionOrUpdateAdapter<AssessmentEntity> __deletionAdapterOfAssessmentEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllAssessments;

  public AssessmentDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAssessmentEntity = new EntityInsertionAdapter<AssessmentEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `assessments` (`assessment_id`,`assessment_name`,`assessment_date`,`assessment_type`,`course_id`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AssessmentEntity value) {
        stmt.bindLong(1, value.getAssessment_id());
        if (value.getAssessment_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAssessment_name());
        }
        if (value.getAssessment_date() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAssessment_date());
        }
        if (value.getAssessment_type() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAssessment_type());
        }
        stmt.bindLong(5, value.getCourse_id());
      }
    };
    this.__deletionAdapterOfAssessmentEntity = new EntityDeletionOrUpdateAdapter<AssessmentEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `assessments` WHERE `assessment_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AssessmentEntity value) {
        stmt.bindLong(1, value.getAssessment_id());
      }
    };
    this.__preparedStmtOfDeleteAllAssessments = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM assessments";
        return _query;
      }
    };
  }

  @Override
  public void insertAssessment(final AssessmentEntity assessmentEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAssessmentEntity.insert(assessmentEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAssessment(final AssessmentEntity assessmentEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfAssessmentEntity.handle(assessmentEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAllAssessments() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllAssessments.acquire();
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllAssessments.release(_stmt);
    }
  }

  @Override
  public AssessmentEntity getAssessmentByID(final int assessmentID) {
    final String _sql = "SELECT * FROM assessments WHERE assessment_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, assessmentID);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfAssessmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_id");
      final int _cursorIndexOfAssessmentName = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_name");
      final int _cursorIndexOfAssessmentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_date");
      final int _cursorIndexOfAssessmentType = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_type");
      final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "course_id");
      final AssessmentEntity _result;
      if(_cursor.moveToFirst()) {
        final String _tmpAssessment_name;
        _tmpAssessment_name = _cursor.getString(_cursorIndexOfAssessmentName);
        final String _tmpAssessment_date;
        _tmpAssessment_date = _cursor.getString(_cursorIndexOfAssessmentDate);
        final String _tmpAssessment_type;
        _tmpAssessment_type = _cursor.getString(_cursorIndexOfAssessmentType);
        final int _tmpCourse_id;
        _tmpCourse_id = _cursor.getInt(_cursorIndexOfCourseId);
        _result = new AssessmentEntity(_tmpAssessment_name,_tmpAssessment_date,_tmpAssessment_type,_tmpCourse_id);
        final int _tmpAssessment_id;
        _tmpAssessment_id = _cursor.getInt(_cursorIndexOfAssessmentId);
        _result.setAssessment_id(_tmpAssessment_id);
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
  public LiveData<List<AssessmentEntity>> getAssessmentByCourse(final int courseID) {
    final String _sql = "SELECT * FROM assessments WHERE course_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, courseID);
    return __db.getInvalidationTracker().createLiveData(new String[]{"assessments"}, false, new Callable<List<AssessmentEntity>>() {
      @Override
      public List<AssessmentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAssessmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_id");
          final int _cursorIndexOfAssessmentName = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_name");
          final int _cursorIndexOfAssessmentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_date");
          final int _cursorIndexOfAssessmentType = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_type");
          final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "course_id");
          final List<AssessmentEntity> _result = new ArrayList<AssessmentEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AssessmentEntity _item;
            final String _tmpAssessment_name;
            _tmpAssessment_name = _cursor.getString(_cursorIndexOfAssessmentName);
            final String _tmpAssessment_date;
            _tmpAssessment_date = _cursor.getString(_cursorIndexOfAssessmentDate);
            final String _tmpAssessment_type;
            _tmpAssessment_type = _cursor.getString(_cursorIndexOfAssessmentType);
            final int _tmpCourse_id;
            _tmpCourse_id = _cursor.getInt(_cursorIndexOfCourseId);
            _item = new AssessmentEntity(_tmpAssessment_name,_tmpAssessment_date,_tmpAssessment_type,_tmpCourse_id);
            final int _tmpAssessment_id;
            _tmpAssessment_id = _cursor.getInt(_cursorIndexOfAssessmentId);
            _item.setAssessment_id(_tmpAssessment_id);
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
  public LiveData<List<AssessmentEntity>> getAllAssessments() {
    final String _sql = "SELECT * FROM assessments ORDER BY assessment_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"assessments"}, false, new Callable<List<AssessmentEntity>>() {
      @Override
      public List<AssessmentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAssessmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_id");
          final int _cursorIndexOfAssessmentName = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_name");
          final int _cursorIndexOfAssessmentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_date");
          final int _cursorIndexOfAssessmentType = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_type");
          final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "course_id");
          final List<AssessmentEntity> _result = new ArrayList<AssessmentEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AssessmentEntity _item;
            final String _tmpAssessment_name;
            _tmpAssessment_name = _cursor.getString(_cursorIndexOfAssessmentName);
            final String _tmpAssessment_date;
            _tmpAssessment_date = _cursor.getString(_cursorIndexOfAssessmentDate);
            final String _tmpAssessment_type;
            _tmpAssessment_type = _cursor.getString(_cursorIndexOfAssessmentType);
            final int _tmpCourse_id;
            _tmpCourse_id = _cursor.getInt(_cursorIndexOfCourseId);
            _item = new AssessmentEntity(_tmpAssessment_name,_tmpAssessment_date,_tmpAssessment_type,_tmpCourse_id);
            final int _tmpAssessment_id;
            _tmpAssessment_id = _cursor.getInt(_cursorIndexOfAssessmentId);
            _item.setAssessment_id(_tmpAssessment_id);
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
