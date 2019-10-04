package com.example.notetaking;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private NotesAdapter adapterfilter;
    private List<Notes_List> notes_list;



    public static NoteDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);






        noteDatabase = Room.databaseBuilder(getApplicationContext(),
                NoteDatabase.class, "notedb").allowMainThreadQueries().build();

        FloatingActionButton addNote = findViewById(R.id.addNote);


        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MainActivity.this, Add.class);
                startActivity(intent);


            }
        });



        recyclerView = (RecyclerView) findViewById(R.id.RecycleVNotes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notes_list = new ArrayList<>();

        adapter = new NotesAdapter(notes_list, this);


        List <Note>  notes =  MainActivity.noteDatabase.noteDao().getAll();


            for (Note note : notes){


                notes_list.add(new Notes_List( note.getTitle(),note.getTime(),note.getDescription(),note.getId(),note.getImage()));
            }


        adapter = new NotesAdapter(notes_list, this);





        recyclerView.setAdapter(adapter);






    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
/*
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.id.action_search,menu);

        MenuItem searchitem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchitem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
*/

        SearchManager searchManager =
                (SearchManager) getSystemService(this.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);






        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Deletall) {
            //NoteDatabase.destroyInstance();

            new AlertDialog.Builder(this)
                    .setTitle("Delete note")
                    .setMessage("Are you sure you want to delete all notes?")


                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
            MainActivity.noteDatabase.noteDao().deleteall();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this,"All notes deleted successfully", Toast.LENGTH_SHORT).show();

                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }



        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        newText= newText.toLowerCase();
        List<Notes_List> newList= new ArrayList<>();

        for( Notes_List note : notes_list){
            String noteString = note.getT().toLowerCase();
            if(noteString.contains(newText)){
                newList.add(note);
            }
        }

        adapter.updateList(newList);
        return true;
    }
}
