package com.example.suigenris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.StringValue;

public class Returndata_Activity extends AppCompatActivity {

    private TextView User_event, User_name, User_email, User_number, User_amt;
    private Button update_btn;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    public static TextView data;
    String scannerUid;
    String eventName;
    String amount;
    int amt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returndata);
        firebaseDatabase = FirebaseDatabase.getInstance();
         scannerUid = getIntent().getStringExtra("abcdef");

        returnInfo();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference =  new DatabaseReference("abcd");
        if(scannerUid!= null && scannerUid != "") {
            DatabaseReference databaseReference = firebaseDatabase.getReference(scannerUid);



            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Event_profile eventProfile = snapshot.getValue(Event_profile.class);
                    User_event.setText("Event : " + eventProfile.getEvent());
                    User_name.setText("Name : " + eventProfile.getName());
                    User_email.setText("Email : " + eventProfile.getEmail());
                    User_number.setText("Contact no. : " + eventProfile.getMob_no());
                    User_amt.setText("Amount : " + eventProfile.getAmount());
                    amt = Integer.parseInt(eventProfile.getAmount());
                    Log.d("ABCDEF", "this is name: " + eventProfile.getName() + " and amount:" + amt);
                    validate(eventProfile.getEvent(), amt);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Returndata_Activity.this, error.getCode(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(Returndata_Activity.this, "This QR Code not Valid", Toast.LENGTH_SHORT).show();
        }

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(validate()) {
                    startActivity(new Intent(Returndata_Activity.this, RemainingPayment_activity.class)
                            .putExtra("uidscan", scannerUid));
                //}else {
                   // Toast.makeText(Returndata_Activity.this, "something went wrong", Toast.LENGTH_SHORT).show();
             //   }
            }
        });

    }

    private void validate(String eventName, int amt) {
//         eventName = User_event.getText().toString().trim();
       // String number = User_number.getText().toString().trim();
//        amount= User_amt.getText().toString().trim();

//       amt = Integer.parseInt(amount);

//        if(amt == 0){
//        }else {
            switch (eventName) {

                case "Cs Go":

                case "Carrom":
                    if (amt == 20) {
                        update_btn.setEnabled(false);
                    }
                    break;

                case "PUBG(Duo)":
                    if (amt == 40) {
                        update_btn.setEnabled(false);
                    }
                    break;

                case "PUBG(Squad)":

                case "DBugging":

                case "Protovision":

                case "Rugby":

                case "Unravel":
                    if (amt == 80) {
                        update_btn.setEnabled(false);
                    }
                    break;

                case "Search In the Dark":

                case "Dead Shot":

                case "Knok It Down":
                    if (amt == 90) {
                        update_btn.setEnabled(false);
                    }else{
                        update_btn.setEnabled(true);
                    }
                    break;

                case "VR":
                    if (amt == 100) {
                        update_btn.setEnabled(false);
                    }
                    break;

                case "IPL":
                    if (amt == 200) {
                        update_btn.setEnabled(false);
                    }
                    break;
            }
       // }

    }

    private void returnInfo() {
        User_event = findViewById(R.id.Return_event);
        User_name = findViewById(R.id.Return_name);
        User_email = findViewById(R.id.Return_email);
        User_number = findViewById(R.id.Return_contact);
        User_amt = findViewById(R.id.Return_amt);
        update_btn= findViewById(R.id.Return_btn);
    }
}