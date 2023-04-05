package com.maaz.notesapp_project.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.maaz.notesapp_project.Dao.NotesDao;
import com.maaz.notesapp_project.Model.NotesEntity;

@Database(entities = NotesEntity.class, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "NotesDB";

    public abstract NotesDao notesDao(); // we will use this to get dao in repository.

    public static NotesDatabase INSTANCE;

    public static NotesDatabase getDatabaseInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, NotesDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries() // this will disable to main thread query check in room.
                    .build();

        }
        return INSTANCE;
    }

}
