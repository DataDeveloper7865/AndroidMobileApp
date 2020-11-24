package com.example.wguc196stephenw;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wguc196stephenw.Database.CourseEntity;
import com.example.wguc196stephenw.Database.MentorEntity;
import com.example.wguc196stephenw.ViewModel.CourseEditorViewModel;
import com.example.wguc196stephenw.ViewModel.CourseViewModel;
import com.example.wguc196stephenw.ViewModel.MentorEditorViewModel;
import java.util.List;
public class MentorEditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_MENTORID =
            "com.example.wguc196stephenw.EXTRA_MENTORID";
    public static final String EXTRA_NAME =
            "com.example.wguc196stephenw.EXTRA_NAME";
    public static final String EXTRA_PHONE =
            "com.example.wguc196stephenw.EXTRA_PHONE";
    public static final String EXTRA_EMAIL =
            "com.example.wguc196stephenw.EXTRA_EMAIL";
    public static final String EXTRA_COURSEID =
            "com.example.wguc196stephenw.EXTRA_COURSEID";
    private MentorEditorViewModel mentorEditorViewModel;
    private CourseViewModel courseViewModel;
    private CourseEditorViewModel courseEditorViewModel;
    private int currentMentorID;
    private boolean newMentor;
    private boolean editMentor;
    private EditText mentorNameEditText;
    private EditText mentorPhoneEditText;
    private EditText mentorEmailEditText;
    private TextView mentorCourseTitleTextView;
    private Spinner mentorCourseIDSpinner;
    private int spinnerCount = 0;
    private String originalCourseTitle;
    private int currentCourseID;
    private String currentCourseTitle;
    private String currentCourseTitleID;
    private int mentorCourseID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        initViewModel();
        mentorNameEditText = findViewById(R.id.mentor_name_edit_text);
        mentorPhoneEditText = findViewById(R.id.mentor_phone_edit_text);
        mentorEmailEditText = findViewById(R.id.mentor_email_edit_text);
        mentorCourseTitleTextView = findViewById(R.id.mentor_course_title_text_view);
        mentorCourseIDSpinner = findViewById(R.id.mentor_course_spinner);
        final ArrayAdapter<CourseEntity> adapter2 = new ArrayAdapter<CourseEntity>(this,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mentorCourseIDSpinner.setAdapter(adapter2);
        mentorCourseIDSpinner.setOnItemSelectedListener(this);
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courseEntities) {
                adapter2.addAll(courseEntities);
            }
        });
        initViewModel2();
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
                if (courseEntity != null && intent.hasExtra(EXTRA_MENTORID)) {
                    mentorCourseTitleTextView.setText(String.valueOf(courseEntity.getCourse_title()));
                    originalCourseTitle = mentorCourseTitleTextView.getText().toString().trim();
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
        mentorEditorViewModel = ViewModelProviders.of(this)
                .get(MentorEditorViewModel.class);
        mentorEditorViewModel.mLiveMentor.observe(this, new Observer<MentorEntity>() {

            @Override
            public void onChanged(@Nullable MentorEntity mentorEntity) {
                Intent intent = getIntent();
                if (mentorEntity != null && intent.hasExtra(EXTRA_MENTORID)) {
                    mentorNameEditText.setText(mentorEntity.getMentor_name());
                    mentorPhoneEditText.setText(mentorEntity.getMentor_phone());
                    mentorEmailEditText.setText(mentorEntity.getMentor_email());
                    mentorCourseID = mentorEntity.getCourse_id();
                }
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("Add Your Mentor");
            newMentor = true;
        } else {
            setTitle("Modify A Mentor");
            newMentor = false;
            int mentorID = extras.getInt(EXTRA_MENTORID);
            this.currentMentorID = mentorID;
            mentorEditorViewModel.loadData(mentorID);
        }
    }
    private void saveMentor() {
        String name = mentorNameEditText.getText().toString();
        String phone = mentorPhoneEditText.getText().toString();
        String email = mentorEmailEditText.getText().toString();
        String course = mentorCourseIDSpinner.getSelectedItem().toString();
        if (name.trim().isEmpty() || phone.trim().isEmpty() || email.trim().isEmpty() || course.trim().isEmpty()) {
            Toast.makeText(this, "Insert contact info and course", Toast.LENGTH_SHORT).show();
            return;
        }
        mentorEditorViewModel.saveData(name, phone, email, currentCourseID);
        finish();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CourseEntity courseSelected = (CourseEntity) mentorCourseIDSpinner.getSelectedItem();
        if (courseSelected != null) {
            if (spinnerCount <= 1 && !newMentor) {
                mentorCourseIDSpinner.setSelection(getSpinnerIndex(mentorCourseIDSpinner, originalCourseTitle));
                spinnerCount++;
            }
            currentCourseTitleID = String.valueOf(courseSelected.getCourse_id());
            currentCourseTitle = String.valueOf(courseSelected.getCourse_title());
            if (spinnerCount > 1) {
                mentorCourseTitleTextView.setText(currentCourseTitle);
            }
            currentCourseID = courseSelected.getCourse_id();
        }
    }
        @Override
        public void onNothingSelected (AdapterView < ? > parent){
        }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.mentor_editor_menu, menu);
            return true;
        }
        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){
            switch (item.getItemId()) {
                case R.id.save_mentor:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MentorEditorActivity.this);
                    builder.setMessage("Save?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    saveMentor();
                                    Toast.makeText(MentorEditorActivity.this, "Mentor saved", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MentorEditorActivity.this, CourseListActivity.class);
                            startActivity(intent);
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;
                case R.id.nav_home:
                    Intent intent = new Intent(MentorEditorActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }
