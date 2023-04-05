package com.maaz.notesapp_project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import com.maaz.notesapp_project.Model.NotesEntity;
import com.maaz.notesapp_project.R;
import com.maaz.notesapp_project.ViewModel.NotesViewModel;
import com.maaz.notesapp_project.databinding.ActivityAddNotesBinding;

import java.util.Date;

public class AddNotes extends AppCompatActivity {

    ActivityAddNotesBinding binding;
    String title, subTitle, notes;

    NotesViewModel viewModel;
    String Priority = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // we need to get access of view model like this.
        // viewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        // this is not working
        // that's why we wrote this.
        viewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        // priority
        binding.greenPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set Images to Priority
                binding.greenPriority.setImageResource(R.drawable.done); // set done image
                binding.yellowPriority.setImageResource(0);         // this will remove image from it.
                binding.redPriority.setImageResource(0);            // this will remove image from it.

                Priority = "1";

            }
        });
        binding.yellowPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.yellowPriority.setImageResource(R.drawable.done);
                binding.greenPriority.setImageResource(0);
                binding.redPriority.setImageResource(0);

                Priority = "2";
            }
        });
        binding.redPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.redPriority.setImageResource(R.drawable.done);
                binding.greenPriority.setImageResource(0);
                binding.yellowPriority.setImageResource(0);

                Priority = "3";
            }
        });

        // save notes
        binding.doneNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = binding.notesTitle.getText().toString();
                subTitle = binding.notesSubTitle.getText().toString();
                notes = binding.notesData.getText().toString();

                // pass data
                insertNotes(title, subTitle, notes);

            }
        });
    }


    // here we are inserting all our data in database.
    public void insertNotes(String title, String subTitle, String notes){

        // for date
        Date date = new Date();
        CharSequence sequence = DateFormat.format("dd MMMM yyyy", date.getTime());

        // Notes Structure
        NotesEntity notesEntity = new NotesEntity();

        notesEntity.notesTitle = title;
        notesEntity.notesSubTittle = subTitle;
        notesEntity.Notes = notes;
        notesEntity.Date = sequence.toString(); // pass in string.
        notesEntity.Priority = Priority; // pass Priority here

        // put all these in viewModel method
        viewModel.insertNotesViewModel(notesEntity);

        Toast.makeText(this, "Notes Saved.", Toast.LENGTH_SHORT).show();
        finish();
    }
}