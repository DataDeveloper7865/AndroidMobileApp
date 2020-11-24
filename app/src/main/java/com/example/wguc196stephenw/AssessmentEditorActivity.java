package com.example.wguc196stephenw;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wguc196stephenw.Adapter.NoteAdapter;
import com.example.wguc196stephenw.Database.AssessmentEntity;
import com.example.wguc196stephenw.Database.CourseEntity;
import com.example.wguc196stephenw.Database.DateConverter;
import com.example.wguc196stephenw.Database.NoteEntity;
import com.example.wguc196stephenw.ViewModel.AssessmentEditorViewModel;
import com.example.wguc196stephenw.ViewModel.CourseEditorViewModel;
import com.example.wguc196stephenw.ViewModel.CourseViewModel;
import com.example.wguc196stephenw.ViewModel.NoteViewModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
public class AssessmentEditorActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    public static final String EXTRA_ASSESSMENTID =
            "com.example.wguc196stephenw.EXTRA_ASSESSMENTID";
    public static final String EXTRA_NAME =
            "com.example.wguc196stephenw.EXTRA_NAME";
    public static final String EXTRA_DUE_DATE =
            "com.example.wguc196stephenw.EXTRA_DUE_DATE";
    public static final String EXTRA_TYPE =
            "com.example.wguc196stephenw.EXTRA_TYPE";
    public static final String EXTRA_COURSEID =
            "com.example.wguc196stephenw.EXTRA_COURSEID";
    public static final String EXTRA_COURSE_TITLE =
            "com.example.wguc196stephenw.EXTRA_COURSE_TITLE";
    private AssessmentEditorViewModel assessmentEditorViewModel;
    private NoteViewModel noteViewModel;
    private CourseViewModel courseViewModel;
    private CourseEditorViewModel courseEditorViewModel;
    private String originalCourseTitle;
    private int currentAssessmentID;
    private boolean newAssessment;
    private EditText assessmentNameEditText;
    private EditText assessmentDateEditText;
    private TextView assessmentTypeTextView;
    private TextView assessmentCourseTitleTextView;
    Button assessmentDatePickerButton;
    Button buttonAddNote;
    private TextView datePickerView;
    private Spinner assessmentTypeSpinner;
    private Spinner assessmentCourseIDSpinner;
    private int spinnerCount = 0;
    private int currentCourseID;
    private String currentCourseTitle;
    private String currentCourseTitleID;
    private int assessmentCourseID;
    private SimpleDateFormat dateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        buttonAddNote = findViewById(R.id.add_note_button);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentEditorActivity.this, NoteEditorActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });
        setupDatePickers();
        initViewModel();
        initNoteRecyclerView();
        assessmentTypeSpinner = findViewById(R.id.assessment_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(adapter);
        assessmentTypeSpinner.setOnItemSelectedListener(this);
        assessmentCourseIDSpinner = findViewById(R.id.assessment_course_spinner);
        final ArrayAdapter<CourseEntity> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentCourseIDSpinner.setAdapter(adapter2);
        assessmentCourseIDSpinner.setOnItemSelectedListener(this);
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courseEntities) {
                adapter2.addAll(courseEntities);
            }
        });
        initViewModel2();
        assessmentNameEditText = findViewById(R.id.assessment_name_edit_text);
        assessmentDateEditText = findViewById(R.id.assessment_due_date_edit_text);
        assessmentTypeTextView = findViewById(R.id.assessment_type_text_view);
        assessmentCourseTitleTextView = findViewById(R.id.course_title_text_view);
        assessmentNameEditText.addTextChangedListener(assessmentTextWatcher);
        assessmentDateEditText.addTextChangedListener(assessmentTextWatcher);
        assessmentTypeTextView.addTextChangedListener(assessmentTextWatcher);
        assessmentCourseTitleTextView.addTextChangedListener(assessmentTextWatcher);
    }
    private void _scheduleAlert(int id, String time, String title, String text) {
        long now = DateConverter.nowDate();
        long alertTime = DateConverter.toTimestamp(time);
        if (now <= DateConverter.toTimestamp(time)) {
            NotificationReceiver.scheduleAssessmentAlarm(getApplicationContext(), id, alertTime,
                    text, title + ", starts: " + time);
        }
    }
    public void scheduleAlert(MenuItem menuItem) {
        String dateText = assessmentDateEditText.getText().toString();
        String text = "You have a test today!";
        String title = assessmentNameEditText.getText().toString();
        _scheduleAlert(currentAssessmentID, dateText, title, text);
    }
    private TextWatcher assessmentTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String assessmentName = assessmentNameEditText.getText().toString().trim();
            String date = assessmentDateEditText.getText().toString().trim();
            String type = assessmentTypeTextView.getText().toString().trim();
            String course = assessmentCourseTitleTextView.getText().toString().trim();
            buttonAddNote.setEnabled(!newAssessment && !assessmentName.isEmpty() && !date.isEmpty() && !type.isEmpty() && !course.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    private void initNoteRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.note_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getNotesByAssessment(currentAssessmentID).observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                noteAdapter.setNotes(noteEntities);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AssessmentEditorActivity.this);
                builder.setMessage("delete this note?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                noteViewModel.deleteNote(noteAdapter.getNoteAtPosition(viewHolder.getAdapterPosition()));
                                Toast.makeText(AssessmentEditorActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AssessmentEditorActivity.this, AssessmentListActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AssessmentEditorActivity.this, AssessmentListActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }).attachToRecyclerView(recyclerView);
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            //Selects item clicked to be edited in NoteEditorActivity and populates fields with selected term data
            @Override
            public void onItemClick(NoteEntity note) {
                Intent intent = new Intent(AssessmentEditorActivity.this, NoteEditorActivity.class);
                intent.putExtra(NoteEditorActivity.EXTRA_NOTEID, note.getNote_id());
                intent.putExtra(NoteEditorActivity.EXTRA_TITLE, note.getNote_title());
                intent.putExtra(NoteEditorActivity.EXTRA_TEXT, note.getNote_text());
                intent.putExtra(NoteEditorActivity.EXTRA_ASSESSMENTID, note.getAssessment_id());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }
    private int getSpinnerIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().trim().equals(myString.trim())) {
                index = i;
            }
        }
        return index;
    }
    private void initViewModel2() {
        courseEditorViewModel = ViewModelProviders.of(this)
                .get(CourseEditorViewModel.class);
            courseEditorViewModel.mLiveCourse.observe(this, new Observer<CourseEntity>() {

                @Override
                public void onChanged(@Nullable CourseEntity courseEntity) {
                    Intent intent = getIntent();
                    if (courseEntity != null && intent.hasExtra(EXTRA_ASSESSMENTID)) {
                        assessmentCourseTitleTextView.setText(String.valueOf(courseEntity.getCourse_title()));
                        originalCourseTitle = assessmentCourseTitleTextView.getText().toString().trim();
                    }
                }
            });
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                int courseID = extras.getInt(EXTRA_COURSEID);
                this.currentCourseID = courseID;
                courseEditorViewModel.loadData(courseID);
            }
        }
    private void initViewModel() {
        assessmentEditorViewModel = ViewModelProviders.of(this)
                .get(AssessmentEditorViewModel.class);
        assessmentEditorViewModel.mLiveAssessment.observe(this, new Observer<AssessmentEntity>() {
            @Override
            public void onChanged(@Nullable AssessmentEntity assessmentEntity) {
                Intent intent = getIntent();
                if (assessmentEntity != null && intent.hasExtra(EXTRA_ASSESSMENTID)) {
                    assessmentNameEditText.setText(assessmentEntity.getAssessment_name());
                    assessmentDateEditText.setText(assessmentEntity.getAssessment_date());
                    assessmentTypeTextView.setText(assessmentEntity.getAssessment_type());
                    assessmentCourseID = assessmentEntity.getCourse_id();
                    if(assessmentTypeTextView != null){
                        assessmentTypeSpinner.setSelection(getSpinnerIndex(assessmentTypeSpinner, assessmentTypeTextView.getText().toString()));
                    }
                }
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("Add My Assessment");
            newAssessment = true;
        } else {
            setTitle("Modify My Assessment");
            newAssessment = false;
            int assessmentID = extras.getInt(EXTRA_ASSESSMENTID);
            this.currentAssessmentID = assessmentID;
            assessmentEditorViewModel.loadData(assessmentID);
        }
    }
    private void setupDatePickers() {
        assessmentDatePickerButton = findViewById(R.id.due_date_picker);
        assessmentDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerView = findViewById(R.id.assessment_due_date_edit_text);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }
    private boolean saveAssessment() {
        if(assessmentCourseIDSpinner.getCount()== 0){
            Toast.makeText(this, "Please create course first", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }
        String name = assessmentNameEditText.getText().toString();
        String dueDate = assessmentDateEditText.getText().toString();
        String type = assessmentTypeSpinner.getSelectedItem().toString();

        if (name.trim().isEmpty() || dueDate.trim().isEmpty() || type.trim().isEmpty()) {
            Toast.makeText(this, "Insert Name, Due Date, and Type.", Toast.LENGTH_SHORT).show();
            return false;
        }
        assessmentEditorViewModel.saveData(name, dueDate, type, currentCourseID);
        finish();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.assessment_editor_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_assessment:
                AlertDialog.Builder builder = new AlertDialog.Builder(AssessmentEditorActivity.this);
                builder.setMessage("Save?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(saveAssessment()){
                                    Toast.makeText(AssessmentEditorActivity.this, "Assessment successfully saved.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AssessmentEditorActivity.this, "Assessment not saved", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AssessmentEditorActivity.this, CourseListActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AssessmentEditorActivity.this, CourseListActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.alert_assessment:
                scheduleAlert(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month = month + 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = year + "-" + month + "-" + dayOfMonth;
        datePickerView.setText(currentDateString);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CourseEntity courseSelected = (CourseEntity) assessmentCourseIDSpinner.getSelectedItem();
        if (courseSelected != null){
            if(spinnerCount <= 1 && !newAssessment){
                assessmentCourseIDSpinner.setSelection(getSpinnerIndex(assessmentCourseIDSpinner, originalCourseTitle));
                spinnerCount++;
            }
            currentCourseTitleID = String.valueOf(courseSelected.getCourse_id());
            currentCourseTitle = String.valueOf(courseSelected.getCourse_title());
            if(spinnerCount > 1){
                assessmentCourseTitleTextView.setText(currentCourseTitle);
            }
            currentCourseID = courseSelected.getCourse_id();
    }
            assessmentTypeTextView.setText(assessmentTypeSpinner.getSelectedItem().toString());
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
