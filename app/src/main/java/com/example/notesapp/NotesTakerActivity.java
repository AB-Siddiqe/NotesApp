package com.example.notesapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.notesapp.Model.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakerActivity extends AppCompatActivity {
    EditText edittext_title, edittext_notes;
    ImageView imageView_save;
    Notes notes;
    Boolean isOldNote= false;
    Button back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes_taker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edittext_title=findViewById(R.id.edittext_title);
        edittext_notes=findViewById(R.id.edittext_notes);
        imageView_save=findViewById(R.id.imageView_save);
        back = findViewById(R.id.backbutton1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotesTakerActivity.this, MainActivity.class));
            }
        });

        notes = new Notes();
        try {
            notes= (Notes) getIntent().getSerializableExtra("Old_note");
            edittext_title.setText(notes.getTitle());
            edittext_notes.setText(notes.getNotes());
            isOldNote= true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edittext_title.getText().toString();
                String description = edittext_notes.getText().toString();

                if (description.isEmpty()){
                    Toast.makeText(NotesTakerActivity.this, "Please add some notes", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat formet = new SimpleDateFormat("EEE, d MMM YYYY HH:mm a");
                Date date = new Date();

                if (!isOldNote){
                    notes=new Notes();
                }

               notes.setTitle(title);
               notes.setNotes(description);
               notes.setDate(formet.format(date));


                Intent intent = new Intent();
                intent.putExtra("note",notes);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });

    }
}