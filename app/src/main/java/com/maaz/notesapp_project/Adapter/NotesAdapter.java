package com.maaz.notesapp_project.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maaz.notesapp_project.Activity.UpdateActivity;
import com.maaz.notesapp_project.MainActivity;
import com.maaz.notesapp_project.Model.NotesEntity;
import com.maaz.notesapp_project.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {

    MainActivity mainActivity; // this is also context.
    List<NotesEntity> list;
    List<NotesEntity> getNotesItem;

    public NotesAdapter(MainActivity mainActivity, List<NotesEntity> list) {
        this.mainActivity = mainActivity;
        this.list = list;
        getNotesItem = new ArrayList<>(list); // for searchView
    }

    // for searchView method
    public void searchNotes(List<NotesEntity> filteredNotes){
        this.list = filteredNotes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.notes_items_layout, parent, false);
        return new notesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notesViewHolder holder, int position) {
        NotesEntity entity = list.get(position);
        holder.title.setText(entity.notesTitle);
        holder.subTitle.setText(entity.notesSubTittle);
        holder.Date.setText(entity.Date);

        // set Priority
        if (entity.Priority.equals("1")){
            holder.Priority.setBackgroundResource(R.drawable.shape_green);
        } else if (entity.Priority.equals("2")){
            holder.Priority.setBackgroundResource(R.drawable.shape_yellow);
        } else {
            holder.Priority.setBackgroundResource(R.drawable.shape_red);
        }

        // click Listener on recyclerview Items
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, UpdateActivity.class);

                intent.putExtra("id", entity.id);
                intent.putExtra("title", entity.notesTitle);
                intent.putExtra("subTitle", entity.notesSubTittle);
                intent.putExtra("notes", entity.Notes);
                intent.putExtra("priority", entity.Priority);

                mainActivity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class notesViewHolder extends RecyclerView.ViewHolder{

        TextView title, subTitle, Date;
        View Priority;

        public notesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notesTitle);
            subTitle = itemView.findViewById(R.id.notesSubTitle);
            Date = itemView.findViewById(R.id.notesDate);
            Priority = itemView.findViewById(R.id.notesPriority);
        }
    }
}
