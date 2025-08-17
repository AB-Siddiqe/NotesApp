package com.example.notesapp;

import static java.util.Locale.filter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.notesapp.Adapter.NoteAdapter;
import com.example.notesapp.Database.RoomDB;
import com.example.notesapp.Interface.NoteClickListener;
import com.example.notesapp.Model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    FloatingActionButton addBtn;
    List<Notes> notes = new ArrayList<>();
    RoomDB database;
    SearchView searchview_home;
    Notes selectedNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler_home);
        addBtn=findViewById(R.id.addBtn);
        searchview_home=findViewById(R.id.searchview_home);

        database= RoomDB.getInstance(this);
        notes=database.mainDAO().getAll();

        updateRecycler(notes);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NotesTakerActivity.class);
                startActivityForResult(intent,101);

            }
        });

        searchview_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
            public boolean onCreateOptionsMenu(PopupMenu menu) {
                getMenuInflater().inflate(R.menu.popup_menu,
                        (Menu) menu);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<Notes>filterList=new ArrayList<>();
        for (Notes singleNote : notes){
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase()) || singleNote.getNotes().toLowerCase().contains(newText.toLowerCase())){
                filterList.add(singleNote);
            }
        }
        noteAdapter.filterlist(filterList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==101){
            if (resultCode== Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.mainDAO().insert(new_notes);
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                noteAdapter.notifyDataSetChanged();
            }
        }
        else if (requestCode==102){
            if (resultCode==Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.mainDAO().insert(new_notes);
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                noteAdapter.notifyDataSetChanged();
            }
        }
        else if (requestCode==103){
            if (resultCode==Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.mainDAO().delete(new_notes);
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                noteAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        noteAdapter=new NoteAdapter(MainActivity.this, notes,noteClickListener);
        recyclerView.setAdapter(noteAdapter);
    }
    private final NoteClickListener noteClickListener=new NoteClickListener() {
        @Override
        public void onClick(Notes notes) {

            Intent intent= new Intent(MainActivity.this,NotesTakerActivity.class);
            intent.putExtra("Old_note", notes);
            startActivityForResult(intent,102);

        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {

        }
    };

 }
