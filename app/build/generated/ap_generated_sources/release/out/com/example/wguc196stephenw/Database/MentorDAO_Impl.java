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
public final class MentorDAO_Impl implements MentorDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MentorEntity> __insertionAdapterOfMentorEntity;

  private final EntityDeletionOrUpdateAdapter<MentorEntity> __deletionAdapterOfMentorEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllMentors;

  public MentorDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMentorEntity = new EntityInsertionAdapter<MentorEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `mentors` (`mentor_id`,`mentor_name`,`mentor_phone`,`mentor_email`,`course_id`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MentorEntity value) {
        stmt.bindLong(1, value.getMentor_id());
        if (value.getMentor_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMentor_name());
        }
        if (value.getMentor_phone() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMentor_phone());
        }
        if (value.getMentor_email() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMentor_email());
        }
        stmt.bindLong(5, value.getCourse_id());
      }
    };
    this.__deletionAdapterOfMentorEntity = new EntityDeletionOrUpdateAdapter<MentorEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `mentors` WHERE `mentor_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MentorEntity value) {
        stmt.bindLong(1, value.getMentor_id());
      }
    };
    this.__preparedStmtOfDeleteAllMentors = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM mentors";
        return _query;
      }
    };
  }

  @Override
  public void insertMentor(final MentorEntity mentorEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfMentorEntity.insert(mentorEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteMentor(final MentorEntity mentorEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfMentorEntity.handle(mentorEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAllMentors() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllMentors.acquire();
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllMentors.release(_stmt);
    }
  }

  @Override
  public MentorEntity getMentorByID(final int mentorID) {
    final String _sql = "SELECT * FROM mentors WHERE mentor_id= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, mentorID);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMentorId = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_id");
      final int _cursorIndexOfMentorName = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_name");
      final int _cursorIndexOfMentorPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_phone");
      final int _cursorIndexOfMentorEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_email");
      final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "course_id");
      final MentorEntity _result;
      if(_cursor.moveToFirst()) {
        final String _tmpMentor_name;
        _tmpMentor_name = _cursor.getString(_cursorIndexOfMentorName);
        final String _tmpMentor_phone;
        _tmpMentor_phone = _cursor.getString(_cursorIndexOfMentorPhone);
        final String _tmpMentor_email;
        _tmpMentor_email = _cursor.getString(_cursorIndexOfMentorEmail);
        final int _tmpCourse_id;
        _tmpCourse_id = _cursor.getInt(_cursorIndexOfCourseId);
        _result = new MentorEntity(_tmpMentor_name,_tmpMentor_phone,_tmpMentor_email,_tmpCourse_id);
        final int _tmpMentor_id;
        _tmpMentor_id = _cursor.getInt(_cursorIndexOfMentorId);
        _result.setMentor_id(_tmpMentor_id);
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
  public LiveData<List<MentorEntity>> getMentorByCourse(final int courseID) {
    final String _sql = "SELECT * FROM mentors WHERE course_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, courseID);
    return __db.getInvalidationTracker().createLiveData(new String[]{"mentors"}, false, new Callable<List<MentorEntity>>() {
      @Override
      public List<MentorEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMentorId = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_id");
          final int _cursorIndexOfMentorName = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_name");
          final int _cursorIndexOfMentorPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_phone");
          final int _cursorIndexOfMentorEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_email");
          final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "course_id");
          final List<MentorEntity> _result = new ArrayList<MentorEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MentorEntity _item;
            final String _tmpMentor_name;
            _tmpMentor_name = _cursor.getString(_cursorIndexOfMentorName);
            final String _tmpMentor_phone;
            _tmpMentor_phone = _cursor.getString(_cursorIndexOfMentorPhone);
            final String _tmpMentor_email;
            _tmpMentor_email = _cursor.getString(_cursorIndexOfMentorEmail);
            final int _tmpCourse_id;
            _tmpCourse_id = _cursor.getInt(_cursorIndexOfCourseId);
            _item = new MentorEntity(_tmpMentor_name,_tmpMentor_phone,_tmpMentor_email,_tmpCourse_id);
            final int _tmpMentor_id;
            _tmpMentor_id = _cursor.getInt(_cursorIndexOfMentorId);
            _item.setMentor_id(_tmpMentor_id);
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
  public LiveData<List<MentorEntity>> getAllMentors() {
    final String _sql = "SELECT * FROM mentors ORDER BY mentor_name DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"mentors"}, false, new Callable<List<MentorEntity>>() {
      @Override
      public List<MentorEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMentorId = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_id");
          final int _cursorIndexOfMentorName = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_name");
          final int _cursorIndexOfMentorPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_phone");
          final int _cursorIndexOfMentorEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "mentor_email");
          final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "course_id");
          final List<MentorEntity> _result = new ArrayList<MentorEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MentorEntity _item;
            final String _tmpMentor_name;
            _tmpMentor_name = _cursor.getString(_cursorIndexOfMentorName);
            final String _tmpMentor_phone;
            _tmpMentor_phone = _cursor.getString(_cursorIndexOfMentorPhone);
            final String _tmpMentor_email;
            _tmpMentor_email = _cursor.getString(_cursorIndexOfMentorEmail);
            final int _tmpCourse_id;
            _tmpCourse_id = _cursor.getInt(_cursorIndexOfCourseId);
            _item = new MentorEntity(_tmpMentor_name,_tmpMentor_phone,_tmpMentor_email,_tmpCourse_id);
            final int _tmpMentor_id;
            _tmpMentor_id = _cursor.getInt(_cursorIndexOfMentorId);
            _item.setMentor_id(_tmpMentor_id);
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
