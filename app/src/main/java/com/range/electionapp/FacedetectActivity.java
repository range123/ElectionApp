package com.range.electionapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import okhttp3.OkHttpClient;

public class FacedetectActivity extends AppCompatActivity {

    ClarifaiClient client;
    ImageView iv ;
    Bitmap imageBitmap;
    ProgressBar pb;

    static final int REQUEST_IMAGE_CAPTURE = 1;


    void predict() {
        new AsyncTask<Void, Void, ClarifaiResponse<List<ClarifaiOutput<Concept>>>>() {
            @Override
            protected ClarifaiResponse<List<ClarifaiOutput<Concept>>> doInBackground(Void... params) {

                final Model generalModel = client.getModelByID("Facerecog").executeSync().get();
                //Toast.makeText(FacedetectActivity.this, "Started async", Toast.LENGTH_SHORT).show();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                byte[] bitmapData = stream.toByteArray();

                return generalModel.predict()
                        //.withInputs(ClarifaiInput.forImage(ClarifaiImage.of(imageBytes)))
                        .withInputs(ClarifaiInput.forImage(bitmapData))
                        .executeSync();
            }

            @Override
            protected void onPostExecute(ClarifaiResponse<List<ClarifaiOutput<Concept>>> response) {
                //TODO stop progressbar
                Toast.makeText(FacedetectActivity.this, "Reached", Toast.LENGTH_SHORT).show();

                if (!response.isSuccessful()) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }
                final List<ClarifaiOutput<Concept>> predictions = response.get();
                if (predictions.isEmpty()) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "No predictions", Toast.LENGTH_SHORT).show();
                    return;
                }

                pb.setVisibility(View.GONE);
                List<Concept> concepts = predictions.get(0).data();
                for(Concept x:concepts)
                {
                    if(x.value()>0.6) {
                        Toast.makeText(FacedetectActivity.this, "Authenticated" + x.value(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),FaceExpressionActivity.class));
                        finish();
                    }
                    else
                        Toast.makeText(FacedetectActivity.this, "You aren't Gokul" + x.value(), Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    pb.setVisibility(View.VISIBLE);
                    imageBitmap = (Bitmap) extras.get("data");
                    iv.setImageBitmap(imageBitmap);
                    predict();
                }
            }

            private void dispatchTakePictureIntent() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facedetect);
        pb = findViewById(R.id.progress_bar);
        iv = findViewById(R.id.imgview);
        client = new ClarifaiBuilder("68de716e1f144b19a68167785444ecce").client(new OkHttpClient()).buildSync();
        dispatchTakePictureIntent();
    }
}
