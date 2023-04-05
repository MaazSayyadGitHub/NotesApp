package com.maaz.notesapp_project.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes_Table")
public class NotesEntity {

    // entity is used for making model of data which fields will be in database.
    // this is like structure class.

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "notes_title")
    public String notesTitle;

    @ColumnInfo(name = "notes_subtitle")
    public String notesSubTittle;

    @ColumnInfo(name = "notes")
    public String Notes;

    @ColumnInfo(name = "notes_date")
    public String Date;

    @ColumnInfo(name = "notes_priority")
    public String Priority;
}
