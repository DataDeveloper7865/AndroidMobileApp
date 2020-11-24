package com.example.wguc196stephenw.Database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class Database_Impl extends Database {
  private volatile TermDAO _termDAO;

  private volatile CourseDAO _courseDAO;

  private volatile AssessmentDAO _assessmentDAO;

  private volatile MentorDAO _mentorDAO;

  private volatile NoteDAO _noteDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`note_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `note_title` TEXT, `note_text` TEXT, `assessment_id` INTEGER NOT NULL, FOREIGN KEY(`assessment_id`) REFERENCES `assessments`(`assessment_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `terms` (`term_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `term_title` TEXT, `term_start_date` TEXT, `term_end_date` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `courses` (`course_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `course_title` TEXT, `course_start_date` TEXT, `course_end_date` TEXT, `course_status` TEXT, `term_id` INTEGER NOT NULL, FOREIGN KEY(`term_id`) REFERENCES `terms`(`term_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `assessments` (`assessment_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `assessment_name` TEXT, `assessment_date` TEXT, `assessment_type` TEXT, `course_id` INTEGER NOT NULL, FOREIGN KEY(`course_id`) REFERENCES `courses`(`course_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `mentors` (`mentor_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `mentor_name` TEXT, `mentor_phone` TEXT, `mentor_email` TEXT, `course_id` INTEGER NOT NULL, FOREIGN KEY(`course_id`) REFERENCES `courses`(`course_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'ddae15a47fe72728db300d5366c701e8')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `notes`");
        _db.execSQL("DROP TABLE IF EXISTS `terms`");
        _db.execSQL("DROP TABLE IF EXISTS `courses`");
        _db.execSQL("DROP TABLE IF EXISTS `assessments`");
        _db.execSQL("DROP TABLE IF EXISTS `mentors`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsNotes = new HashMap<String, TableInfo.Column>(4);
        _columnsNotes.put("note_id", new TableInfo.Column("note_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("note_title", new TableInfo.Column("note_title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("note_text", new TableInfo.Column("note_text", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("assessment_id", new TableInfo.Column("assessment_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotes = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysNotes.add(new TableInfo.ForeignKey("assessments", "CASCADE", "NO ACTION",Arrays.asList("assessment_id"), Arrays.asList("assessment_id")));
        final HashSet<TableInfo.Index> _indicesNotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNotes = new TableInfo("notes", _columnsNotes, _foreignKeysNotes, _indicesNotes);
        final TableInfo _existingNotes = TableInfo.read(_db, "notes");
        if (! _infoNotes.equals(_existingNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "notes(com.example.wguc196stephenw.Database.NoteEntity).\n"
                  + " Expected:\n" + _infoNotes + "\n"
                  + " Found:\n" + _existingNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsTerms = new HashMap<String, TableInfo.Column>(4);
        _columnsTerms.put("term_id", new TableInfo.Column("term_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTerms.put("term_title", new TableInfo.Column("term_title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTerms.put("term_start_date", new TableInfo.Column("term_start_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTerms.put("term_end_date", new TableInfo.Column("term_end_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTerms = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTerms = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTerms = new TableInfo("terms", _columnsTerms, _foreignKeysTerms, _indicesTerms);
        final TableInfo _existingTerms = TableInfo.read(_db, "terms");
        if (! _infoTerms.equals(_existingTerms)) {
          return new RoomOpenHelper.ValidationResult(false, "terms(com.example.wguc196stephenw.Database.TermEntity).\n"
                  + " Expected:\n" + _infoTerms + "\n"
                  + " Found:\n" + _existingTerms);
        }
        final HashMap<String, TableInfo.Column> _columnsCourses = new HashMap<String, TableInfo.Column>(6);
        _columnsCourses.put("course_id", new TableInfo.Column("course_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("course_title", new TableInfo.Column("course_title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("course_start_date", new TableInfo.Column("course_start_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("course_end_date", new TableInfo.Column("course_end_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("course_status", new TableInfo.Column("course_status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("term_id", new TableInfo.Column("term_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCourses = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCourses.add(new TableInfo.ForeignKey("terms", "CASCADE", "NO ACTION",Arrays.asList("term_id"), Arrays.asList("term_id")));
        final HashSet<TableInfo.Index> _indicesCourses = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCourses = new TableInfo("courses", _columnsCourses, _foreignKeysCourses, _indicesCourses);
        final TableInfo _existingCourses = TableInfo.read(_db, "courses");
        if (! _infoCourses.equals(_existingCourses)) {
          return new RoomOpenHelper.ValidationResult(false, "courses(com.example.wguc196stephenw.Database.CourseEntity).\n"
                  + " Expected:\n" + _infoCourses + "\n"
                  + " Found:\n" + _existingCourses);
        }
        final HashMap<String, TableInfo.Column> _columnsAssessments = new HashMap<String, TableInfo.Column>(5);
        _columnsAssessments.put("assessment_id", new TableInfo.Column("assessment_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssessments.put("assessment_name", new TableInfo.Column("assessment_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssessments.put("assessment_date", new TableInfo.Column("assessment_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssessments.put("assessment_type", new TableInfo.Column("assessment_type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssessments.put("course_id", new TableInfo.Column("course_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAssessments = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysAssessments.add(new TableInfo.ForeignKey("courses", "CASCADE", "NO ACTION",Arrays.asList("course_id"), Arrays.asList("course_id")));
        final HashSet<TableInfo.Index> _indicesAssessments = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAssessments = new TableInfo("assessments", _columnsAssessments, _foreignKeysAssessments, _indicesAssessments);
        final TableInfo _existingAssessments = TableInfo.read(_db, "assessments");
        if (! _infoAssessments.equals(_existingAssessments)) {
          return new RoomOpenHelper.ValidationResult(false, "assessments(com.example.wguc196stephenw.Database.AssessmentEntity).\n"
                  + " Expected:\n" + _infoAssessments + "\n"
                  + " Found:\n" + _existingAssessments);
        }
        final HashMap<String, TableInfo.Column> _columnsMentors = new HashMap<String, TableInfo.Column>(5);
        _columnsMentors.put("mentor_id", new TableInfo.Column("mentor_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMentors.put("mentor_name", new TableInfo.Column("mentor_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMentors.put("mentor_phone", new TableInfo.Column("mentor_phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMentors.put("mentor_email", new TableInfo.Column("mentor_email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMentors.put("course_id", new TableInfo.Column("course_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMentors = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMentors.add(new TableInfo.ForeignKey("courses", "CASCADE", "NO ACTION",Arrays.asList("course_id"), Arrays.asList("course_id")));
        final HashSet<TableInfo.Index> _indicesMentors = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMentors = new TableInfo("mentors", _columnsMentors, _foreignKeysMentors, _indicesMentors);
        final TableInfo _existingMentors = TableInfo.read(_db, "mentors");
        if (! _infoMentors.equals(_existingMentors)) {
          return new RoomOpenHelper.ValidationResult(false, "mentors(com.example.wguc196stephenw.Database.MentorEntity).\n"
                  + " Expected:\n" + _infoMentors + "\n"
                  + " Found:\n" + _existingMentors);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "ddae15a47fe72728db300d5366c701e8", "babe78ba6f8ebc8c39b5d2fa4ed8da96");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "notes","terms","courses","assessments","mentors");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `notes`");
      _db.execSQL("DELETE FROM `terms`");
      _db.execSQL("DELETE FROM `courses`");
      _db.execSQL("DELETE FROM `assessments`");
      _db.execSQL("DELETE FROM `mentors`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public TermDAO termDAO() {
    if (_termDAO != null) {
      return _termDAO;
    } else {
      synchronized(this) {
        if(_termDAO == null) {
          _termDAO = new TermDAO_Impl(this);
        }
        return _termDAO;
      }
    }
  }

  @Override
  public CourseDAO courseDAO() {
    if (_courseDAO != null) {
      return _courseDAO;
    } else {
      synchronized(this) {
        if(_courseDAO == null) {
          _courseDAO = new CourseDAO_Impl(this);
        }
        return _courseDAO;
      }
    }
  }

  @Override
  public AssessmentDAO assessmentDAO() {
    if (_assessmentDAO != null) {
      return _assessmentDAO;
    } else {
      synchronized(this) {
        if(_assessmentDAO == null) {
          _assessmentDAO = new AssessmentDAO_Impl(this);
        }
        return _assessmentDAO;
      }
    }
  }

  @Override
  public MentorDAO mentorDAO() {
    if (_mentorDAO != null) {
      return _mentorDAO;
    } else {
      synchronized(this) {
        if(_mentorDAO == null) {
          _mentorDAO = new MentorDAO_Impl(this);
        }
        return _mentorDAO;
      }
    }
  }

  @Override
  public NoteDAO noteDAO() {
    if (_noteDAO != null) {
      return _noteDAO;
    } else {
      synchronized(this) {
        if(_noteDAO == null) {
          _noteDAO = new NoteDAO_Impl(this);
        }
        return _noteDAO;
      }
    }
  }
}
