package com.maaz.notesapp_project.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.maaz.notesapp_project.Dao.NotesDao;
import com.maaz.notesapp_project.Database.NotesDatabase;
import com.maaz.notesapp_project.Model.NotesEntity;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;

    public LiveData<List<NotesEntity>> getAllNotes; // we will put all notes in this.
    public LiveData<List<NotesEntity>> getHighToLow; // we will put all notes in this which is DES.
    public LiveData<List<NotesEntity>> getLowToHigh; // we will put all notes in this which is ASC .


    public NotesRepository(Application application) { // application means context.

        NotesDatabase database = NotesDatabase.getDatabaseInstance(application); // getting NotesDatabase object.
        notesDao = database.notesDao(); // getting dao from notesDatabase.

        // this getAllNotes will give all inserted Notes.
        getAllNotes = notesDao.getAllNotes(); // getting getAllNotes from dao class

        getHighToLow = notesDao.highToLow(); // filter
        getLowToHigh = notesDao.lowToHigh(); // filter

    }

    public void insertNotes(NotesEntity notes){
        notesDao.insertNotes(notes);
    }

    public void deleteNotes(int id){
        notesDao.deleteNotes(id);
    }

    public void updateNotes(NotesEntity notes){
        notesDao.updateNotes(notes);
    }



}
