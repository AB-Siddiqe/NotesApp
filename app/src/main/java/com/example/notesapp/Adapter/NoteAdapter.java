package com.example.notesapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.Interface.NoteClickListener;
import com.example.notesapp.Model.Notes;
import com.example.notesapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    Context context;
    List<Notes>notesList;
    NoteClickListener clickListener;

    public NoteAdapter(Context context, List<Notes> notesList, NoteClickListener clickListener) {
        this.context = context;
        this.notesList = notesList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.note_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {

        holder.titleText.setText(notesList.get(position).getTitle());
        holder.titleText.setSelected(true);

        holder.noteText.setText(notesList.get(position).getNotes());

        holder.dateText.setText(notesList.get(position).getDate());
        holder.dateText.setSelected(true);

        if (notesList.get(position).isPinned()){
            holder.imageView_pin.setImageResource(R.drawable.pin);
        } else {
            holder.imageView_pin.setImageResource(0);
        }

        int color_code = getRandomColor();
        holder.previous_note.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code,null));

        holder.previous_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(notesList.get(holder.getAdapterPosition()));

            }
        });
        holder.previous_note.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickListener.onLongClick(notesList.get(holder.getAdapterPosition()), holder.previous_note);
                return true;
            }
        });
    }
    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return  colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void filterlist(List<Notes>filterList){
        notesList = filterList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView previous_note;
        TextView titleText,noteText,dateText;
        ImageView imageView_pin;
        @SuppressLint("WrongViewCast")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            previous_note=itemView.findViewById(R.id.previous_note);
            titleText=itemView.findViewById(R.id.titleText);
            noteText=itemView.findViewById(R.id.noteText);
            dateText=itemView.findViewById(R.id.dateText);
            imageView_pin=itemView.findViewById(R.id.imageView_pin);

        }
    }
}
