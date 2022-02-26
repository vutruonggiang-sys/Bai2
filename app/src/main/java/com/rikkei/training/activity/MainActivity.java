package com.rikkei.training.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button butCamera;
    ImageView imgImage;
    VideoView vidVideo;
    int REQUEST_CODE_CAMERA = 123;
    ImageView imgGetVideo, imgGetImage;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        butCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });
        imgGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            imgImage.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void galleryAddImg(){
        Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

    }

    private File createNameImage(){
        String timeStamp=new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
        String imageFileName="JPGE_"+timeStamp+"_";
        File storeDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image= null;
        try {
            image = File.createTempFile(imageFileName,".jpg",storeDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void dispatchTakePictureIntent(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){
            File photoFile =createNameImage();
            if(photoFile!=null){
                Uri photoUri= FileProvider.getUriForFile(this,"com.rikkei.training.activity",photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);
            }
        }
    }

    public void init() {
        butCamera = findViewById(R.id.butCamera);
        imgImage = findViewById(R.id.imgImage);
        vidVideo = findViewById(R.id.vidVideo);
        imgGetImage = findViewById(R.id.imgGetImage);
        imgGetVideo = findViewById(R.id.imgGetVideo);
    }
}