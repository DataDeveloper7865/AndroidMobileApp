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
public final class NoteDAO_Impl implements NoteDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NoteEntity> __insertionAdapterOfNoteEntity;

  private final EntityDeletionOrUpdateAdapter<NoteEntity> __deletionAdapterOfNoteEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllNotes;

  public NoteDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNoteEntity = new EntityInsertionAdapter<NoteEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `notes` (`note_id`,`note_title`,`note_text`,`assessment_id`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NoteEntity value) {
        stmt.bindLong(1, value.getNote_id());
        if (value.getNote_title() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNote_title());
        }
        if (value.getNote_text() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNote_text());
        }
        stmt.bindLong(4, value.getAssessment_id());
      }
    };
    this.__deletionAdapterOfNoteEntity = new EntityDeletionOrUpdateAdapter<NoteEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `notes` WHERE `note_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NoteEntity value) {
        stmt.bindLong(1, value.getNote_id());
      }
    };
    this.__preparedStmtOfDeleteAllNotes = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM notes";
        return _query;
      }
    };
  }

  @Override
  public void insertNote(final NoteEntity noteEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfNoteEntity.insert(noteEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteNote(final NoteEntity noteEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfNoteEntity.handle(noteEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAllNotes() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllNotes.acquire();
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllNotes.release(_stmt);
    }
  }

  @Override
  public NoteEntity getNoteByID(final int noteID) {
    final String _sql = "SELECT * FROM notes WHERE note_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, noteID);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfNoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "note_id");
      final int _cursorIndexOfNoteTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "note_title");
      final int _cursorIndexOfNoteText = CursorUtil.getColumnIndexOrThrow(_cursor, "note_text");
      final int _cursorIndexOfAssessmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_id");
      final NoteEntity _result;
      if(_cursor.moveToFirst()) {
        final String _tmpNote_title;
        _tmpNote_title = _cursor.getString(_cursorIndexOfNoteTitle);
        final String _tmpNote_text;
        _tmpNote_text = _cursor.getString(_cursorIndexOfNoteText);
        final int _tmpAssessment_id;
        _tmpAssessment_id = _cursor.getInt(_cursorIndexOfAssessmentId);
        _result = new NoteEntity(_tmpNote_title,_tmpNote_text,_tmpAssessment_id);
        final int _tmpNote_id;
        _tmpNote_id = _cursor.getInt(_cursorIndexOfNoteId);
        _result.setNote_id(_tmpNote_id);
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
  public LiveData<List<NoteEntity>> getAllNotes() {
    final String _sql = "SELECT * FROM notes ORDER BY note_title DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"notes"}, false, new Callable<List<NoteEntity>>() {
      @Override
      public List<NoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "note_id");
          final int _cursorIndexOfNoteTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "note_title");
          final int _cursorIndexOfNoteText = CursorUtil.getColumnIndexOrThrow(_cursor, "note_text");
          final int _cursorIndexOfAssessmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_id");
          final List<NoteEntity> _result = new ArrayList<NoteEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final NoteEntity _item;
            final String _tmpNote_title;
            _tmpNote_title = _cursor.getString(_cursorIndexOfNoteTitle);
            final String _tmpNote_text;
            _tmpNote_text = _cursor.getString(_cursorIndexOfNoteText);
            final int _tmpAssessment_id;
            _tmpAssessment_id = _cursor.getInt(_cursorIndexOfAssessmentId);
            _item = new NoteEntity(_tmpNote_title,_tmpNote_text,_tmpAssessment_id);
            final int _tmpNote_id;
            _tmpNote_id = _cursor.getInt(_cursorIndexOfNoteId);
            _item.setNote_id(_tmpNote_id);
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
  public LiveData<List<NoteEntity>> getNoteByAssessment(final int assessmentID) {
    final String _sql = "SELECT * FROM notes WHERE assessment_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, assessmentID);
    return __db.getInvalidationTracker().createLiveData(new String[]{"notes"}, false, new Callable<List<NoteEntity>>() {
      @Override
      public List<NoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "note_id");
          final int _cursorIndexOfNoteTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "note_title");
          final int _cursorIndexOfNoteText = CursorUtil.getColumnIndexOrThrow(_cursor, "note_text");
          final int _cursorIndexOfAssessmentId = CursorUtil.getColumnIndexOrThrow(_cursor, "assessment_id");
          final List<NoteEntity> _result = new ArrayList<NoteEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final NoteEntity _item;
            final String _tmpNote_title;
            _tmpNote_title = _cursor.getString(_cursorIndexOfNoteTitle);
            final String _tmpNote_text;
            _tmpNote_text = _cursor.getString(_cursorIndexOfNoteText);
            final int _tmpAssessment_id;
            _tmpAssessment_id = _cursor.getInt(_cursorIndexOfAssessmentId);
            _item = new NoteEntity(_tmpNote_title,_tmpNote_text,_tmpAssessment_id);
            final int _tmpNote_id;
            _tmpNote_id = _cursor.getInt(_cursorIndexOfNoteId);
            _item.setNote_id(_tmpNote_id);
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
