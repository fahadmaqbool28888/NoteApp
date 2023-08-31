package com.jip.noteapp;

import static com.jip.noteapp.constant.Constant.EDITING_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jip.noteapp.ViewModelPackage.EditactivityViewModel;
import com.jip.noteapp.constant.Constant;
import com.jip.noteapp.model.NoteEntity;

import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    EditText edit_notes;
    EditactivityViewModel viewModel;
    FloatingActionButton floatingActionButton,delete_icon;
    Toolbar toolbar;
    boolean newnote;
    boolean isEditing;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        delete_icon=findViewById(R.id.delete);
        floatingActionButton=findViewById(R.id.update);
        edit_notes=findViewById(R.id.edit_notes);

        intViewModel();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNoteAndExist(edit_notes.getText().toString());
                finish();
            }
        });

        if (!newnote)
        {
            delete_icon.setVisibility(View.GONE);

        }
        else
        {
            delete_icon.setVisibility(View.VISIBLE);
        }

        delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                deleteNote();
            }
        });

        if (savedInstanceState!=null)
        {
            isEditing=savedInstanceState.getBoolean(EDITING_KEY);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        outState.putBoolean(EDITING_KEY,true);
        super.onSaveInstanceState(outState);
    }

    private void deleteNote()
    {
        viewModel.deleteNote(); finish();
    }

    private void intViewModel()
    {
        Observer<NoteEntity> noteEntityObserver=new Observer<NoteEntity>() {
            @Override
            public void onChanged(@NonNull NoteEntity noteEntity) {

                if (!isEditing)
                {
                    edit_notes.setText(Objects.requireNonNull(noteEntity.getText()));

                }
            }
        };

        viewModel=new ViewModelProvider(EditActivity.this).get(EditactivityViewModel.class);
        viewModel.entityMutableLiveData.observe(this,noteEntityObserver);


        Bundle bundle=getIntent().getExtras();
        if (bundle==null)
        {
        setTitle("New Note");
        newnote=false;
        }
        else
        {
            setTitle("Edit Note");
            newnote=true;
            int id=bundle.getInt(Constant.NOTE_ID_KEY);

            viewModel.fetechNote(id);
        }

    }


    void saveNoteAndExist(String str)
    {
viewModel.saveNoteAndExist(str);
    }
}