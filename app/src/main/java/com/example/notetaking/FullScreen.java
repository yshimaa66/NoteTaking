package com.example.notetaking;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FullScreen extends AppCompatActivity {

    public static Object data;
    private ImageView imageViewFullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        imageViewFullScreen = findViewById(R.id.FullScreenImageView);

        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) data;


        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ){
            imageViewFullScreen.setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION );

        }
        else if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB )
            imageViewFullScreen.setSystemUiVisibility( View.STATUS_BAR_HIDDEN );
        else{}




        imageViewFullScreen.setImageBitmap(bmp );


    }
}
