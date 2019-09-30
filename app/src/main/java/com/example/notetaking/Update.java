package com.example.notetaking;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Locale;

public class Update extends AppCompatActivity {

    private EditText TitleEditTextUpdate,DescriptionEditTextUpdate;
    private Button updateNote,cameraiconupdate,deletecameraiconupdate;

    Integer Request_Camera=1 , Select_File=0;

    private ImageView imageViewUpdate;
    public byte[] imageInByte=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        String Title = getIntent().getExtras().getString("Title");
        String Description = getIntent().getExtras().getString("Description");
        final int Id= getIntent().getExtras().getInt("Id");
        byte[] Image= getIntent().getExtras().getByteArray("Image");


        TitleEditTextUpdate = findViewById(R.id.EditTextTitleUpdate);
        DescriptionEditTextUpdate = findViewById(R.id.EditTextDescriptionUpdate);
        imageViewUpdate=findViewById(R.id.imageviewupdate);
        deletecameraiconupdate=findViewById(R.id.deletecameraiconupdate);
        deletecameraiconupdate.setVisibility(View.GONE);

        TitleEditTextUpdate.setText(Title);
        DescriptionEditTextUpdate.setText(Description);


        if(Image!=null){

            Bitmap imgbytes = BitmapFactory.decodeByteArray(Image, 0, Image.length);

            deletecameraiconupdate.setVisibility(View.VISIBLE);
            imageViewUpdate.setImageBitmap(imgbytes);

        }else{

            imageViewUpdate.setVisibility(View.GONE);

            imageViewUpdate.setImageBitmap(null);

        }



        updateNote = findViewById(R.id.updateNote);

        cameraiconupdate=findViewById(R.id.cameraiconupdate);

        cameraiconupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImage();
            }
        });





        deletecameraiconupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageViewUpdate.setImageDrawable(null);
                deletecameraiconupdate.setVisibility(View.GONE);
                imageViewUpdate.setVisibility(View.GONE);
            }
        });




        updateNote.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                String TitleStringUpdate = TitleEditTextUpdate.getText().toString();
                String DescriptionStringUpdate = DescriptionEditTextUpdate.getText().toString();


                if(TitleStringUpdate.matches("") || DescriptionStringUpdate.matches("")){

                    Toast.makeText(getApplicationContext(),"Please fill out the blank fields", Toast.LENGTH_SHORT ).show();


                }else{

                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                Note note=new Note();

                note.setId(Id);
                note.setTitle(TitleStringUpdate);
                note.setTime(currentDate+" "+currentTime);
                note.setDescription(DescriptionStringUpdate);

                    if(imageViewUpdate.getDrawable()!=null){

                        Bitmap bitmap = ((BitmapDrawable) imageViewUpdate.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        imageInByte = baos.toByteArray();
                        deletecameraiconupdate.setVisibility(View.VISIBLE);


                    }

                    note.setImage(imageInByte);


                MainActivity.noteDatabase.noteDao().update(note);

                Toast.makeText(getApplicationContext(),"Note updateded successfully", Toast.LENGTH_SHORT ).show();


                TitleEditTextUpdate.setText("");
                DescriptionEditTextUpdate.setText("");




                Intent intent = new Intent(Update.this, MainActivity.class);
                startActivity(intent);
                finish();


       }}
    });





}




    private void SelectImage(){

        final CharSequence[] items ={"Camera","Gallery","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Update.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                if(items[i].equals("Camera")){

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,Request_Camera);


                }else if (items[i].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent.createChooser(intent,"Select File"),Select_File);

                }else  if(items[i].equals("Cancel")){

                    dialog.dismiss();

                }



            }
        });

        builder.show();


    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode== Request_Camera){


                Bundle bundle = data.getExtras();
                final Bitmap bitmap = (Bitmap) bundle.get("data");
                imageViewUpdate.setImageBitmap(bitmap);

            }else if(requestCode== Select_File){

                Uri SelectedImageUri = data.getData();
                imageViewUpdate.setImageURI(SelectedImageUri);
            }


        }
    }



}
