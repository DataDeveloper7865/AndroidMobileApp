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
public final class TermDAO_Impl implements TermDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TermEntity> __insertionAdapterOfTermEntity;

  private final EntityDeletionOrUpdateAdapter<TermEntity> __deletionAdapterOfTermEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllTerms;

  public TermDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTermEntity = new EntityInsertionAdapter<TermEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `terms` (`term_id`,`term_title`,`term_start_date`,`term_end_date`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TermEntity value) {
        stmt.bindLong(1, value.getTerm_id());
        if (value.getTerm_title() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTerm_title());
        }
        if (value.getTerm_start_date() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTerm_start_date());
        }
        if (value.getTerm_end_date() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTerm_end_date());
        }
      }
    };
    this.__deletionAdapterOfTermEntity = new EntityDeletionOrUpdateAdapter<TermEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `terms` WHERE `term_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TermEntity value) {
        stmt.bindLong(1, value.getTerm_id());
      }
    };
    this.__preparedStmtOfDeleteAllTerms = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM terms";
        return _query;
      }
    };
  }

  @Override
  public void insertTerm(final TermEntity termEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTermEntity.insert(termEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteTerm(final TermEntity termEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTermEntity.handle(termEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAllTerms() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllTerms.acquire();
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllTerms.release(_stmt);
    }
  }

  @Override
  public TermEntity getTermByID(final int termID) {
    final String _sql = "SELECT * FROM terms WHERE term_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, termID);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTermId = CursorUtil.getColumnIndexOrThrow(_cursor, "term_id");
      final int _cursorIndexOfTermTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "term_title");
      final int _cursorIndexOfTermStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "term_start_date");
      final int _cursorIndexOfTermEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "term_end_date");
      final TermEntity _result;
      if(_cursor.moveToFirst()) {
        final String _tmpTerm_title;
        _tmpTerm_title = _cursor.getString(_cursorIndexOfTermTitle);
        final String _tmpTerm_start_date;
        _tmpTerm_start_date = _cursor.getString(_cursorIndexOfTermStartDate);
        final String _tmpTerm_end_date;
        _tmpTerm_end_date = _cursor.getString(_cursorIndexOfTermEndDate);
        _result = new TermEntity(_tmpTerm_title,_tmpTerm_start_date,_tmpTerm_end_date);
        final int _tmpTerm_id;
        _tmpTerm_id = _cursor.getInt(_cursorIndexOfTermId);
        _result.setTerm_id(_tmpTerm_id);
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
  public LiveData<List<TermEntity>> getAllTerms() {
    final String _sql = "SELECT * FROM terms ORDER BY term_start_date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"terms"}, false, new Callable<List<TermEntity>>() {
      @Override
      public List<TermEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTermId = CursorUtil.getColumnIndexOrThrow(_cursor, "term_id");
          final int _cursorIndexOfTermTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "term_title");
          final int _cursorIndexOfTermStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "term_start_date");
          final int _cursorIndexOfTermEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "term_end_date");
          final List<TermEntity> _result = new ArrayList<TermEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TermEntity _item;
            final String _tmpTerm_title;
            _tmpTerm_title = _cursor.getString(_cursorIndexOfTermTitle);
            final String _tmpTerm_start_date;
            _tmpTerm_start_date = _cursor.getString(_cursorIndexOfTermStartDate);
            final String _tmpTerm_end_date;
            _tmpTerm_end_date = _cursor.getString(_cursorIndexOfTermEndDate);
            _item = new TermEntity(_tmpTerm_title,_tmpTerm_start_date,_tmpTerm_end_date);
            final int _tmpTerm_id;
            _tmpTerm_id = _cursor.getInt(_cursorIndexOfTermId);
            _item.setTerm_id(_tmpTerm_id);
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
