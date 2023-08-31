package com.jip.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jip.noteapp.Adapter.NoteAdapter;
import com.jip.noteapp.ViewModelPackage.MainActivityViewModel;
import com.jip.noteapp.model.NoteEntity;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<NoteEntity> getlist=new ArrayList<>();
    MainActivityViewModel viewModelMain;
    RecyclerView rycView;
    FloatingActionButton floatingActionButton,deleteall;
    NoteAdapter noteAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rycView=findViewById(R.id.rycView);
        floatingActionButton=findViewById(R.id.floatingActionButton);
        deleteall=findViewById(R.id.delete_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
               // addSampleDate();
       /*         Intent intent=new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);*/
            }
        });

        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteallNote();
            }
        });



       // getlist= viewModelMain.noteEntities;
        initRcyView();
        initViewModel();
    }

    private void deleteallNote()
    {
        viewModelMain.deleteAllNote();
    }


    private void initViewModel()
    {
  Observer<List<NoteEntity>> observer=new Observer<List<NoteEntity>>() {
      @Override
      public void onChanged(List<NoteEntity> noteEntities) {

          getlist.clear();
          getlist.addAll(noteEntities);
          if (noteAdapter==null)
          {
              noteAdapter=new NoteAdapter(MainActivity.this,getlist);
              rycView.setAdapter(noteAdapter);
          }
          else {
              noteAdapter.notifyDataSetChanged();
          }
      }
  };
        viewModelMain = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModelMain.noteEntities.observe(MainActivity.this,observer);
    }

    void initRcyView()
    {

        rycView.hasFixedSize();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rycView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration decoration=new DividerItemDecoration(rycView.getContext(),linearLayoutManager.getOrientation());
        rycView.addItemDecoration(decoration);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped( RecyclerView.ViewHolder viewHolder, int direction) {

                deleteNote(noteAdapter.getnote(viewHolder.getAdapterPosition()));
            }
        });

        itemTouchHelper.attachToRecyclerView(rycView);

    }

    private void deleteNote(NoteEntity getnote)
    {
        viewModelMain.deleteNote(getnote);
        Toast.makeText(MainActivity.this,"Note Deleted",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id)
        {



        }
        return super.onOptionsItemSelected(item);
    }

    private void addSampleDate()
    {
        viewModelMain.addSampleData();
    }
}