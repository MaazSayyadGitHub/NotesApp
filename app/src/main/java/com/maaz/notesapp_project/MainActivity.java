package com.maaz.notesapp_project;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.maaz.notesapp_project.Activity.AddNotes;
import com.maaz.notesapp_project.Adapter.NotesAdapter;
import com.maaz.notesapp_project.Model.NotesEntity;
import com.maaz.notesapp_project.ViewModel.NotesViewModel;
import com.maaz.notesapp_project.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    NotesViewModel notesViewModel;
    NotesAdapter adapter;
    List<NotesEntity> filterNotesAllList; // we will put all type of filter data into this.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Notes App using Room DB, MVVM, LiveData.

        // MVVM means - we will take data from (room db -> dao -> repository -> view model), this is MVVM pattern

        // Steps :
        // 1 : Entity Class.
        // 2 : Dao class
        // 3 : Create Database class and instance
        // 4 : create repository class and get database and dao reference.
        // 5 : View Model - we will get all data from repository to viewModel and from VM to
        //    get data in our activities/fragments


        // get viewModel here
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        notesViewModel.getAllData.observe(this, new Observer<List<NotesEntity>>() {
            @Override
            public void onChanged(List<NotesEntity> notesEntities) {
                setAdapter(notesEntities);
                filterNotesAllList = notesEntities;
            }
        });

        // fab
        binding.newNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNotes.class));
            }
        });


        // Filter Section.

        // by default no filter.
        binding.noFilter.setBackgroundResource(R.drawable.filter_selected);

        binding.noFilter.setOnClickListener(v -> {
            binding.noFilter.setBackgroundResource(R.drawable.filter_selected);
            binding.highToLow.setBackgroundResource(R.drawable.filter_unselected);
            binding.lowToHigh.setBackgroundResource(R.drawable.filter_unselected);

            // No Filter List.

            // we can observe here, that what changes are being performed here, how much size of lists is.
            // notesEntities is lists.
            notesViewModel.getAllData.observe(this, new Observer<List<NotesEntity>>() {
                @Override
                public void onChanged(List<NotesEntity> notesEntities) {
                    setAdapter(notesEntities);
                    filterNotesAllList = notesEntities;
                }
            });
        });

        binding.highToLow.setOnClickListener(v -> {
            binding.noFilter.setBackgroundResource(R.drawable.filter_unselected);
            binding.highToLow.setBackgroundResource(R.drawable.filter_selected);
            binding.lowToHigh.setBackgroundResource(R.drawable.filter_unselected);

            // high to low
            notesViewModel.getHighToLow.observe(this, new Observer<List<NotesEntity>>() {
                @Override
                public void onChanged(List<NotesEntity> notesEntities) {
                    setAdapter(notesEntities);
                    filterNotesAllList = notesEntities;
                }
            });
        });

        binding.lowToHigh.setOnClickListener(v -> {
            binding.noFilter.setBackgroundResource(R.drawable.filter_unselected);
            binding.highToLow.setBackgroundResource(R.drawable.filter_unselected);
            binding.lowToHigh.setBackgroundResource(R.drawable.filter_selected);

            // low to high
            notesViewModel.getLowToHigh.observe(this, new Observer<List<NotesEntity>>() {
                @Override
                public void onChanged(List<NotesEntity> notesEntities) {
                    setAdapter(notesEntities);
                    filterNotesAllList = notesEntities;
                }
            });
        });

    }

    private void setAdapter(List<NotesEntity> notesEntities){
        // set adapter on recyclerView here
        binding.notesRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new NotesAdapter(MainActivity.this, notesEntities);
        binding.notesRecyclerview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.search);
        android.widget.SearchView searchView = (android.widget.SearchView) item.getActionView();
        searchView.setQueryHint("Search Notes Here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });

        return true;
    }

    private void NotesFilter(String newText) {
        ArrayList<NotesEntity> filterNames = new ArrayList<>();

        for (NotesEntity notes : filterNotesAllList){
            // for title and subTitle
            if (notes.notesTitle.contains(newText) || notes.notesSubTittle.contains(newText)){
                filterNames.add(notes);
            }
        }

        adapter.searchNotes(filterNames); // pass this list to searchView method.

        // New Comments Added Bro just check it

    }


}