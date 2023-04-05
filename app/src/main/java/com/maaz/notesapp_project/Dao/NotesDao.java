package com.maaz.notesapp_project.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.maaz.notesapp_project.Model.NotesEntity;

import java.util.List;

@Dao
public interface NotesDao {

    // Dao is use for access data which might be list or other format,
    // and will be NotesEntity type.
    // and all queries will be here. (CRUD)

    // here we will taking list format data.
    @Query("SELECT * FROM Notes_Table")
    // generally we use this.
    // List<NotesEntity> getAllNotes();   // abstract method.
    // but we are using live data so we need to put all this in live data.
    LiveData<List<NotesEntity>> getAllNotes();

    // filter query for high to low - DESCENDING
    @Query("SELECT * FROM Notes_Table ORDER BY notes_priority DESC")
    LiveData<List<NotesEntity>> highToLow();

    // filter query for low to high - ASCENDING
    @Query("SELECT * FROM Notes_Table ORDER BY notes_priority ASC")
    LiveData<List<NotesEntity>> lowToHigh();

    // insert data
    @Insert
    void insertNotes(NotesEntity... notes);
    // we can get any type of data here, single, double or array as well.

    // delete data
    @Query("DELETE FROM Notes_Table WHERE id=:id")
    void deleteNotes(int id);

    // update data
    @Update
    void updateNotes(NotesEntity notes);

}
