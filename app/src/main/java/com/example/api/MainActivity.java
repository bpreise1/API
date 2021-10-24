package com.example.api;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.api.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.cameraButton.setOnClickListener(new View.OnClickListener() {//logic for camera access
            @Override
            public void onClick(View view) {
                requestCamera();
            }
        });

        binding.rotateButton.setOnClickListener(new View.OnClickListener() {//logic for rotation
            @Override
            public void onClick(View view) {
                binding.imageView.setRotation(binding.imageView.getRotation() + 45);
            }
        });
    }

    public void requestCamera() {//get camera from device
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            
        }
    }

    @Override
    public void onActivityResult(int resultCode, int requestCode, Intent data) {//show picture on screen
        super.onActivityResult(requestCode, resultCode, data);

        Bundle extras = data.getExtras();
        Bitmap bitmap = (Bitmap) extras.get("data");
        binding.imageView.setImageBitmap(bitmap);
    }
}