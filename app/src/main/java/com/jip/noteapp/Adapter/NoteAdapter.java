package com.jip.noteapp.Adapter;

import static com.jip.noteapp.constant.Constant.NOTE_ID_KEY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jip.noteapp.EditActivity;
import com.jip.noteapp.R;
import com.jip.noteapp.model.NoteEntity;

import java.util.List;



public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder>
{

    LayoutInflater layoutInflater;
    Context context;
    List<NoteEntity> noteEntities;

    public NoteAdapter(Context context, List<NoteEntity> noteEntities)
    {
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
        this.noteEntities = noteEntities;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view=layoutInflater.inflate(R.layout.noteitemlayout,parent,false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder( NoteHolder holder, int position)
    {

        NoteEntity noteEntity=noteEntities.get(position);
        holder.textView.setText(noteEntity.getText());
        holder.sr.setText(String.valueOf(noteEntity.getId())+" ");

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent mintent=new Intent(context, EditActivity.class);
                mintent.putExtra(NOTE_ID_KEY,noteEntity.getId());
                context.startActivity(mintent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return noteEntities.size();
    }

    public class NoteHolder extends RecyclerView.ViewHolder{


        ImageButton button;

        TextView textView,sr;
        public NoteHolder(View itemView) {
            super(itemView);

            button=itemView.findViewById(R.id.btn_1);
            textView=itemView.findViewById(R.id.text_1);
            sr=itemView.findViewById(R.id.sr);

        }
    }
}
