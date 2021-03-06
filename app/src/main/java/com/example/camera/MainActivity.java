package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button picture;
    CapturePhotoUtils capturePhotoUtils;
    static final int REQUEST_IMAGE= 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picture = (Button) findViewById(R.id.buttonPicture);

    }
    public void onClick(View view){

        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePicIntent, REQUEST_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i1 = new Intent(this, PaintActivity.class);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap thumbnail = (Bitmap) extras.get("data");

            //Intent i1 = new Intent(this, PaintActivity.class);
            capturePhotoUtils.insertImage(getContentResolver(), thumbnail,"image", "The photo I took");
            //MediaStore.Images.Media.insertImage(getContentResolver(), thumbnail, "image", "The photo I took");
            i1.putExtra("photo", thumbnail);
            startActivity(i1);

        }
    }


}