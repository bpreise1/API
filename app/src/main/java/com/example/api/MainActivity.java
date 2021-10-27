package com.example.api;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.api.databinding.ActivityMainBinding;
import com.example.api.databinding.ImageEditBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ImageEditBinding imageEditBinding;
    boolean drawBearcat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        imageEditBinding = ImageEditBinding.inflate(getLayoutInflater());
        drawBearcat = false;
        View view = binding.getRoot();
        setContentView(view);

        binding.cameraButton.setOnClickListener(new View.OnClickListener() {//logic for camera access
            @Override
            public void onClick(View view) {
                requestCamera();
            }
        });

        imageEditBinding.rotateButton.setOnClickListener(new View.OnClickListener() {//logic for rotation
            @Override
            public void onClick(View view) {
                imageEditBinding.imageView2.setRotation(imageEditBinding.imageView2.getRotation() + 45);
            }
        });

        imageEditBinding.bearcatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawBearcat) {
                    drawBearcat = false;
                }
                else {
                    drawBearcat = true;
                }
            }
        });

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                drawImage(x, y);
                return false;
            }
        };

        imageEditBinding.getRoot().setOnTouchListener(onTouchListener);
    }

    public void drawImage(float x, float y) {
        if(drawBearcat) {
            ImageView imageView = new ImageView(this);
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(50, 50);

            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(R.drawable.bearcat);
            imageView.setX(x);
            imageView.setY(y);

            imageEditBinding.constraintLayout.addView(imageView);
        }
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

        imageEditBinding.imageView2.setImageBitmap(bitmap);
        setContentView(imageEditBinding.getRoot());
    }
}