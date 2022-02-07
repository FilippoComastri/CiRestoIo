package com.example.cirestoio;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_REQUEST = 100;
    Button openCamera;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    System.out.println("ALGISE");
                    if (result != null && result.getResultCode() == RESULT_OK  && result.getData() != null) {
                        //Get image capture
                        System.out.println("OK");
                        Bitmap captureImage = (Bitmap) result.getData().getExtras().get("data");
                        Intent intent = new Intent(MainActivity.this, Stat.class);
                        intent.putExtra("img", captureImage);
                        startActivity(intent);
                    }
                    System.out.println("ALGISE 2");
                }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openCamera = findViewById(R.id.openCamera);

        obtainPermission(this);

        openCamera.setOnClickListener(this);

    }

    private boolean obtainPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //i permessi per l'uso della camera non sono stati concessi, quindi bisogna richiederli
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        }
        // a questo punto i permessi per la camera sono già stati concessi, per cui è possibile procedere ad operare
        return true;

    }

    @Override
    public void onClick(View view) {
        // Open camera
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startForResult.launch(takePictureIntent);
    }

}