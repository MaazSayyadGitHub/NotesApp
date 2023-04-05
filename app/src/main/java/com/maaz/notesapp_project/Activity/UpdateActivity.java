package com.maaz.notesapp_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.maaz.notesapp_project.Model.NotesEntity;
import com.maaz.notesapp_project.R;
import com.maaz.notesapp_project.ViewModel.NotesViewModel;
import com.maaz.notesapp_project.databinding.ActivityUpdateBinding;

import java.util.Date;

public class UpdateActivity extends AppCompatActivity {

    ActivityUpdateBinding binding;
    String Priority = "1";

    int id;
    String title, subTitle, notes, priority;

    NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        Intent getIntent = getIntent();
        id = getIntent.getIntExtra("id", 0); // use this id to delete notes.
        title = getIntent.getStringExtra("title");
        subTitle = getIntent.getStringExtra("subTitle");
        notes = getIntent.getStringExtra("notes");
        priority = getIntent.getStringExtra("priority");

        binding.upTitle.setText(title);
        binding.upSubTitle.setText(subTitle);
        binding.upNotes.setText(notes);

        // get what previously provided priority
        if (priority.equals("1")){
            binding.greenPriority.setImageResource(R.drawable.done);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);
        } else if (priority.equals("2")){
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(R.drawable.done);
            binding.redPriority.setImageResource(0);
        } else if (priority.equals("3")){
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(R.drawable.done);
        }


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


        binding.updateNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.upTitle.getText().toString();
                String subTitle = binding.upSubTitle.getText().toString();
                String notes = binding.upNotes.getText().toString();

                // pass data
                updateNotes(title, subTitle, notes);
            }
        });
    }

    private void updateNotes(String title, String subTitle, String notes) {
        Date date = new Date();
        CharSequence sequence = DateFormat.format("dd MMMM yyyy", date.getTime());


        NotesEntity updateEntity = new NotesEntity();

        updateEntity.id = id; // this will update data on same id.
        updateEntity.notesTitle = title;
        updateEntity.notesSubTittle = subTitle;
        updateEntity.Notes = notes;
        // if Priority not changed in update.
        if (!Priority.equals("1")) {
            updateEntity.Priority = Priority;
        } else {
            // if priority changed in update.
            updateEntity.Priority = priority;
        }

        updateEntity.Date = sequence.toString();

        notesViewModel.updateNotesViewModel(updateEntity);

        Toast.makeText(this, "Notes Updated.", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // this is used to set icon on Toolbar.
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.delete){
            // Toast.makeText(this, "Item Deleted", Toast.LENGTH_LONG).show();

            BottomSheetDialog sheetDialog = new BottomSheetDialog(this, R.style.BottomSheetStyle);
            // inflate layout in bottom sheet

            View view = LayoutInflater.from(this).inflate(R.layout.delete_bottomsheet, findViewById(R.id.bottomSheet));
            sheetDialog.setContentView(view); // set on bottom sheet.

            // action on button
            TextView Yes, No;
            Yes = view.findViewById(R.id.deleteYes);
            No = view.findViewById(R.id.deleteNo);

            No.setOnClickListener(v -> {
                // cancel to delete
                sheetDialog.dismiss();
            });

            Yes.setOnClickListener(v -> {
                // delete that note.
                notesViewModel.deleteNotesViewModel(id); // this is opened notes id
                finish();
            });

            sheetDialog.show();
        }

        return true;
    }
}