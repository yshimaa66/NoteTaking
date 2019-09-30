package com.example.notetaking;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class Notes_List extends AppCompatActivity {

 private String T,Time,Description;
 private int Id;
 private byte[] Imageview;


    public Notes_List(String t, String time, String description, int id , byte[] imageview ) {

        T = t;
        Time = time;
        Description = description;
        Id=id;
        Imageview=imageview;

    }



    public byte[] getImage() {

        return Imageview;
    }


    public int getId() {
        return Id;
    }

    public String getT() {
        return T;
    }

    public String getTime() {
        return Time;
    }

    public String getDescription() {
        return Description;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes__list);
    }


}
