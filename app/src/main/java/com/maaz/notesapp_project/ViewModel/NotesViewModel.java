package com.maaz.notesapp_project.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.maaz.notesapp_project.Dao.NotesDao;
import com.maaz.notesapp_project.Model.NotesEntity;
import com.maaz.notesapp_project.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<NotesEntity>> getAllData;
    public LiveData<List<NotesEntity>> getHighToLow;
    public LiveData<List<NotesEntity>> getLowToHigh;

    public NotesViewModel(@NonNull Application application) {
        super(application);

        repository = new NotesRepository(application);
        getAllData = repository.getAllNotes; // get all notes here which no filter.

        getHighToLow = repository.getHighToLow;
        getLowToHigh = repository.getLowToHigh;
    }

    public void insertNotesViewModel(NotesEntity notes){
        repository.insertNotes(notes);
    }

    public void deleteNotesViewModel(int id){
        repository.deleteNotes(id);
    }

    public void updateNotesViewModel(NotesEntity notes){
        repository.updateNotes(notes);
    }




}
