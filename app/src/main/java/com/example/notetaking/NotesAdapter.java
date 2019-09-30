package com.example.notetaking;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.Viewholder> /*implements Filterable*/ {


    private List<Notes_List> NotesList;
    //private List<Notes_List> NotesListFull;
    private Context context;
    private List<Note> notes;



    public NotesAdapter(List<Notes_List> notesList, Context context) {
        NotesList = notesList;
        //NotesListFull = new ArrayList<>(notesList);
        this.context = context;
    }




    @NonNull
    @Override
    public NotesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notes__list,parent,false);




        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {

        final Notes_List notelist= NotesList.get(position);




        holder.TextViewTitle.setText(notelist.getT());
        holder.TextViewTime.setText(notelist.getTime());
        holder.TextViewDescription.setText(notelist.getDescription());

        /*byte[] imgbytes = Base64.decode(notelist.getImage(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgbytes, 0,
                imgbytes.length);*/


        if(notelist.getImage()!=null){

            /*Bitmap imgbytes = BitmapFactory.decodeByteArray(notelist.getImage(), 0, notelist.getImage().length);
            holder.imageView.setImageBitmap(imgbytes);*/


            Bitmap imgbytes =  BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_cameramain);
            holder.imageView.setImageBitmap(imgbytes);

        }else{

            holder.imageView.setImageBitmap(null);

        }






        holder.Deletebtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                /*Note note = new Note();
                note.setId(position);
                MainActivity.noteDatabase.noteDao().delete(note);*/

                new AlertDialog.Builder(context)
                        .setTitle("Delete note")
                        .setMessage("Are you sure you want to delete this note?")


                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                MainActivity.noteDatabase.noteDao().deleteByItemId(notelist.getId());





                                NotesList.remove(position);
                                notifyItemRemoved(position);
                                //this line below gives you the animation and also updates the
                                //list items after the deleted item
                                notifyItemRangeChanged(position, getItemCount());
                /*
                NoteDatabase.getDatabase(context).noteDao()
                        .delete(notes.get(holder.getAdapterPosition()));*/






                                Toast.makeText(context,"Note deleted successfully", Toast.LENGTH_SHORT).show();


                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);


                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();






                // remove your item from data base



/*
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);

 */
            }
        });


        holder.Updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 
                
                
                Intent intent = new Intent(context, Update.class);
                intent.putExtra("Title",notelist.getT());
                intent.putExtra("Description",notelist.getDescription());
                intent.putExtra("Id",notelist.getId());
                intent.putExtra("Image",notelist.getImage());
                context.startActivity(intent);
                
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(notelist.getImage()!=null){

                    final Bitmap imgbytes = BitmapFactory.decodeByteArray(notelist.getImage(), 0, notelist.getImage().length);

                    Display.data=imgbytes;
                }else{

                    Display.data=null;

                }



                Intent intent = new Intent(context, Display.class);
                intent.putExtra("Title",notelist.getT());
                intent.putExtra("Description",notelist.getDescription());
                intent.putExtra("Time",notelist.getTime());
                intent.putExtra("Image",notelist.getImage());




                context.startActivity(intent);

            }
        });



    }


    @Override
    public int getItemCount() {
        return NotesList.size();
    }


    /*

    @Override
    public  Filter getFilter() {
        return NotesFilter;
    }

     Filter NotesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Notes_List> FilteredNotes= new ArrayList<>();

            if(constraint==null || constraint.length()==0){
                FilteredNotes.addAll(NotesListFull);
            }else{
                String filterPattern =  constraint.toString().toLowerCase().trim();
                for(Notes_List note : NotesListFull){
                    if(note.getT().toLowerCase().contains(filterPattern)){
                        FilteredNotes.add(note);
                    }
                }
            }

            FilterResults filterresults= new FilterResults();
            filterresults.values = FilteredNotes;
            return filterresults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            NotesList.clear();
            NotesList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };*/


    public static class Viewholder extends RecyclerView.ViewHolder {

        public TextView TextViewTitle, TextViewTime, TextViewDescription;
        public Button Deletebtn,Updatebtn;
        public ImageView imageView;
        public int camera;

        @SuppressLint("ResourceType")
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            TextViewTitle=(TextView)itemView.findViewById(R.id.TextViewTitle);
            TextViewTime=(TextView)itemView.findViewById(R.id.TextViewTime);
            TextViewDescription=(TextView)itemView.findViewById(R.id.TextViewDescription);
            Deletebtn=(Button)itemView.findViewById(R.id.Deletebtn);
            Updatebtn=(Button)itemView.findViewById(R.id.Updatebtn);
            camera=R.drawable.ic_action_camera;


           imageView = (ImageView)itemView.findViewById(R.id.imageviewmain);

        }




    }

    public void updateList(List<Notes_List> newList){


     NotesList = new ArrayList<>();
     NotesList.addAll(newList);
     notifyDataSetChanged();


    }



}
