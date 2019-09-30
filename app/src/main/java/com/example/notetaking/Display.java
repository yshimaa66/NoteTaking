package com.example.notetaking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Display extends AppCompatActivity {

    public static Object data;
    private TextView TitleTextViewDisplay,DescriptionTextViewDisplay,TimeTextViewDisplay;
    private ImageView imageViewDisplay;

    //private boolean isImageFitToScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        String Title = getIntent().getExtras().getString("Title");
        String Description = getIntent().getExtras().getString("Description");
        String Time = getIntent().getExtras().getString("Time");
        final Bitmap bmp = (Bitmap) data;

        TitleTextViewDisplay = findViewById(R.id.TextViewTitleDisplay);
        DescriptionTextViewDisplay = findViewById(R.id.TextViewDescriptionDisplay);
        TimeTextViewDisplay = findViewById(R.id.TextViewTimeDisplay);
        imageViewDisplay=findViewById(R.id.imageviewdisplay);


        TitleTextViewDisplay.setText(Title);
        DescriptionTextViewDisplay.setText(Description);
        TimeTextViewDisplay.setText(Time);

        if(bmp!=null){



            imageViewDisplay.setImageBitmap(bmp);



            imageViewDisplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        FullScreen.data=bmp;
                        Intent intent = new Intent(Display.this, FullScreen.class);
/*
                        Bundle extras = new Bundle();
                        extras.putParcelable("imagebitmap", imgbytes);
                        intent.putExtras(extras);


 */

                        startActivity(intent);








                }


                /*

                if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ){
                    imageViewDisplay.setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION );

                }
                else if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB )
                    imageViewDisplay.setSystemUiVisibility( View.SCREEN_STATE_OFF );
                else{}
                }

                 */

            });





        }else{

            imageViewDisplay.setVisibility(View.GONE);

            imageViewDisplay.setImageBitmap(null);

        }



    }
}
