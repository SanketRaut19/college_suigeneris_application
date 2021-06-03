package com.example.suigenris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Qrcode_activity extends AppCompatActivity {

    private TextView name_qr, event_qr;
    private ImageView qr_code;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_activity);

        qr_code = findViewById(R.id.qr_code);
        name_qr = findViewById(R.id.name_qr);
        event_qr = findViewById(R.id.event_qr);

        firebaseDatabase = FirebaseDatabase.getInstance();


        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Event_profile event_profile = snapshot.getValue(Event_profile.class);

                event_qr.setText("Event :  "+ event_profile.getEvent());
                name_qr.setText("Name : " + event_profile.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Qrcode_activity.this, error.getCode(), Toast.LENGTH_SHORT).show();

            }
        });*/
        String event = getIntent().getStringExtra("keyevent");
        String name = getIntent().getStringExtra("keyname");


        event_qr.setText("Event : " + event);
        name_qr.setText("Name : " + name);
//        eventUid.setText()
        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            String eventUid = getIntent().getStringExtra("keyuid");
            Log.d("Something", "event name:" + event + "  Event uid:" + eventUid);
            if(eventUid.equals(null))
            {
                eventUid = "some uid must be appear here";
            }
            // Initialize bit matrix
            BitMatrix matrix = writer.encode(eventUid, BarcodeFormat.QR_CODE,
                    350, 350);

            //Initialize barcode encode
            BarcodeEncoder encoder = new BarcodeEncoder();

            //Initialize bitmmap
            Bitmap bitmap = encoder.createBitmap(matrix);

            // Set Bitmap on image view
            qr_code.setImageBitmap(bitmap);

            // Initialize input manager
//            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

//            manager.hideSoftInputFromWindow(et_input.getApplicationWindowToken(), 0);// Hide Soft Keyboard


        } catch (WriterException e) {
            e.printStackTrace();
            Log.d("Something went wrong", "e->  " + e);
        }

    }

}