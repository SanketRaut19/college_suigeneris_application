package com.example.suigenris;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner_Activity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    ZXingScannerView scannerView;
    String scannedUid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(Scanner_Activity.this);
        setContentView(scannerView);
    }


    @Override
    public void handleResult(Result result) {
       scannedUid = result.toString();

        onBackPressed();


        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                scannerView.startCamera();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();

    }
    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
        Intent intent = new Intent(this, Returndata_Activity.class);
        if(scannedUid != null){
            intent.putExtra("abcdef",scannedUid);
        }

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(Scanner_Activity.this);
        scannerView.startCamera();
    }
}

