package com.base.instagramcloneapp;

import android.Manifest;
import android.app.UiAutomation;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class SharePicture_tab extends Fragment implements View.OnClickListener {

    private TextView textView;
    private ImageView imageView;
    private Button button;
    private Context mContext = getContext();


    ActivityResultLauncher<String> requestPermissionLauncher;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.textView);
        button = view.findViewById(R.id.button);

        imageView.setOnClickListener(SharePicture_tab.this);
        button.setOnClickListener(SharePicture_tab.this);

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        // Permission is granted
                        getChosenImage(imageView);
                        Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_SHORT).show();

                    } else {
                        // Permission is denied
                        Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
        );




        return view;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.button){

            if (Build.VERSION.SDK_INT >= 26 && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Request the permission using the requestPermissionLauncher
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                getChosenImage(imageView);
            }


        }


        if(view.getId() == R.id.imageView){


        }


    }

    public void getChosenImage(ImageView imageView) {
        // Retrieve the Drawable from the ImageView
        Drawable drawable = imageView.getDrawable();

        if (drawable instanceof BitmapDrawable) {
            // Convert the Drawable to a Bitmap
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

            // Create a directory in local storage
            File localDirectory = new File(requireContext().getFilesDir(), "my_images");
            if (!localDirectory.exists()) {
                localDirectory.mkdirs();
            }

            // Define the destination file
            File destinationFile = new File(localDirectory, "image_filename.jpg");

            try {
                // Save the Bitmap to the local storage
                FileOutputStream outputStream = new FileOutputStream(destinationFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.close();

                // The image is now saved to the local directory.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}