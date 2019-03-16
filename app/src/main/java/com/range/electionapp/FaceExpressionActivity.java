package com.range.electionapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class FaceExpressionActivity extends AppCompatActivity {

    ImageView image;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;
    FaceDetector detector;
    String vid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_expression);
        vid = getIntent().getStringExtra("vid");
        image = findViewById(R.id.img);
        Toast.makeText(this, "Smile!", Toast.LENGTH_LONG).show();
         detector = new FaceDetector.Builder(getApplicationContext())
                                        .setTrackingEnabled(false)
                                        .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)

                                        .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                                        .build();
         dispatchTakePictureIntent();


    }
    void predict()
    {
        Frame frame = new Frame.Builder().setBitmap(imageBitmap).build();
        SparseArray<Face> faces = detector.detect(frame);
        for(int i=0;i<faces.size();++i){
            Face f = faces.valueAt(i);
            //Log.d("Values",f.getIsLeftEyeOpenProbability()+" "+f.getIsRightEyeOpenProbability() + " "+f.getWidth());
            if(f.getIsLeftEyeOpenProbability()>0) {
                Toast.makeText(this, "Smiling " + f.getIsSmilingProbability(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),FirstActivity.class).putExtra("vid",vid));
                finish();
            }
            else
                Toast.makeText(this, "Nope "+f.getIsSmilingProbability(), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
            predict();
        }
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

}
