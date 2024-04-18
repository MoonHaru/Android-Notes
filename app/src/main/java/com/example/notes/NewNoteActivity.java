package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class NewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        EditText title = findViewById(R.id.notetitle);
        EditText note = findViewById(R.id.notecontent);
        Button btn = findViewById(R.id.button);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String saveTitle = title.getText().toString();
                String saveNote = note.getText().toString();
                long createdtime = System.currentTimeMillis();

                realm.beginTransaction();
                Note note1 = realm.createObject(Note.class);
                note1.setTitle(saveTitle);
                note1.setDescription(saveNote);
                note1.setTimecreated(createdtime);
                realm.commitTransaction();
                Toast.makeText(getApplicationContext(), "Nota guardada", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}