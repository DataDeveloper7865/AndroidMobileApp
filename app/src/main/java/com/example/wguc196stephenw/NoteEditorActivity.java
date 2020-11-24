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
import com.example.wguc196stephenw.Database.AssessmentEntity;
import com.example.wguc196stephenw.Database.NoteEntity;
import com.example.wguc196stephenw.ViewModel.AssessmentEditorViewModel;
import com.example.wguc196stephenw.ViewModel.AssessmentViewModel;
import com.example.wguc196stephenw.ViewModel.NoteEditorViewModel;
import java.util.List;
public class NoteEditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_NOTEID =
            "com.example.wguc196stephenw.EXTRA_NOTEID";
    public static final String EXTRA_TITLE =
            "com.example.wguc196stephenw.EXTRA_TITLE";
    public static final String EXTRA_TEXT =
            "com.example.wguc196stephenw.EXTRA_TEXT";
    public static final String EXTRA_ASSESSMENTID =
            "com.example.wguc196stephenw.EXTRA_ASSESSMENTID";
    private NoteEditorViewModel noteEditorViewModel;
    private AssessmentViewModel assessmentViewModel;
    private AssessmentEditorViewModel assessmentEditorViewModel;
    private int currentNoteID;
    private boolean newNote;
    private boolean editNote;
    private EditText noteTitleEditText;
    private EditText noteTextEditText;
    private TextView noteAssessmentNameTextView;
    private Spinner noteAssessmentIDSpinner;
    private int spinnerCount = 0;
    private String originalAssessmentName;
    private int currentAssessmentID;
    private String currentAssessmentName;
    private String currentAssessmentNameID;
    private int noteAssessmentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        initViewModel();
        noteAssessmentIDSpinner = findViewById(R.id.note_assessment_spinner);
        final ArrayAdapter<AssessmentEntity> adapter2 = new ArrayAdapter<AssessmentEntity>(this,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noteAssessmentIDSpinner.setAdapter(adapter2);
        noteAssessmentIDSpinner.setOnItemSelectedListener(this);
        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(List<AssessmentEntity> assessmentEntities) {
                adapter2.addAll(assessmentEntities);
            }
        });
        initViewModel2();
        noteTitleEditText = findViewById(R.id.note_title_edit_text);
        noteTextEditText = findViewById(R.id.note_text_edit_Text);
        noteAssessmentNameTextView = findViewById(R.id.note_assessment_name_text_view);

    }
    private void initViewModel2() {
        assessmentEditorViewModel = ViewModelProviders.of(this)
                .get(AssessmentEditorViewModel.class);
        assessmentEditorViewModel.mLiveAssessment.observe(this, new Observer<AssessmentEntity>() {

            @Override
            public void onChanged(@Nullable AssessmentEntity assessmentEntity) {
                Intent intent = getIntent();
                if (assessmentEntity != null && intent.hasExtra(EXTRA_NOTEID)) {
                    noteAssessmentNameTextView.setText(String.valueOf(assessmentEntity.getAssessment_name()));
                    originalAssessmentName = noteAssessmentNameTextView.getText().toString().trim();
                }
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int assessmentID = extras.getInt(EXTRA_ASSESSMENTID);
            this.currentAssessmentID = assessmentID;
            assessmentEditorViewModel.loadData(assessmentID);
        }
    }
    private void initViewModel() {
        noteEditorViewModel = ViewModelProviders.of(this)
                .get(NoteEditorViewModel.class);
        noteEditorViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {

            @Override
            public void onChanged(@Nullable NoteEntity noteEntity) {
                Intent intent = getIntent();
                if (noteEntity != null && intent.hasExtra(EXTRA_NOTEID)) {
                    noteTitleEditText.setText(noteEntity.getNote_title());
                    noteTextEditText.setText(noteEntity.getNote_text());
                    noteAssessmentID = noteEntity.getAssessment_id();
                }
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("Add Note");
            newNote = true;
        } else {
            setTitle("Edit Note");
            newNote = false;
            int noteID = extras.getInt(EXTRA_NOTEID);
            this.currentNoteID = noteID;
            noteEditorViewModel.loadData(noteID);
        }
    }
    private int getSpinnerIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(myString.trim())) {
                index = i;
            }
        }
        return index;
    }
    private void saveNote() {
        String title = noteTitleEditText.getText().toString();
        String text = noteTextEditText.getText().toString();
        String assessment = noteAssessmentIDSpinner.getSelectedItem().toString();
        if (title.trim().isEmpty() || text.trim().isEmpty() || assessment.trim().isEmpty()) {
            Toast.makeText(this, "Insert info for note", Toast.LENGTH_SHORT).show();
            return;
        }
        noteEditorViewModel.saveData(title, text, currentAssessmentID);
        finish();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        AssessmentEntity assessmentSelected = (AssessmentEntity) noteAssessmentIDSpinner.getSelectedItem();
        if(assessmentSelected != null){
            if(spinnerCount <= 1 && !newNote){
                noteAssessmentIDSpinner.setSelection(getSpinnerIndex(noteAssessmentIDSpinner, originalAssessmentName));
                spinnerCount++;
            }
        }
        currentAssessmentNameID = String.valueOf(assessmentSelected.getAssessment_id());
        currentAssessmentName = String.valueOf(assessmentSelected.getAssessment_name());
        if(spinnerCount > 1){
            noteAssessmentNameTextView.setText(currentAssessmentName);
        }
        currentAssessmentID = assessmentSelected.getAssessment_id();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_editor_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                AlertDialog.Builder builder = new AlertDialog.Builder(NoteEditorActivity.this);
                builder.setMessage("Save?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveNote();
                                Toast.makeText(NoteEditorActivity.this, "Note saved successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(NoteEditorActivity.this, AssessmentListActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.share_note:
                String title = noteTitleEditText.getText().toString();
                String text = noteTextEditText.getText().toString();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                sendIntent.putExtra(Intent.EXTRA_TITLE, title);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
