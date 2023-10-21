package com.base.instagramcloneapp;

import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;

public class FragmentPermissionHelper {

    public  void startPermissionrequest(FragmentActivity fr, FragmenPermissionInterface frInterface,String menifiest){
        ActivityResultLauncher<String> requestPermissionLauncher =
                fr.registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    frInterface.OnGranted(isGranted);
                });

        requestPermissionLauncher.launch(
              menifiest);

    }
}
