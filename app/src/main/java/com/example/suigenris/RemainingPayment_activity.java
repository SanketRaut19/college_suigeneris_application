package com.example.suigenris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RemainingPayment_activity extends AppCompatActivity {

    private EditText pay_event, pay_name, pay_email, pay_con, pay_amt, pay_balance;
    private Button payment_btn;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    int amt;
    int total_payment;
    String a;
    String b;
    String event;
    String name;
    String email;
    String contact;
    String amount;
    String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remaining_payment_activity);
        paymentDetails();


        String user = getIntent().getStringExtra("uidscan");
        firebaseDatabase = FirebaseDatabase.getInstance();
      final DatabaseReference databaseReference = firebaseDatabase.getReference(user);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Event_profile eventProfile = snapshot.getValue(Event_profile.class);
                pay_event.setText(eventProfile.getEvent());
                eventName = eventProfile.getName();
                pay_name.setText(eventProfile.getName());
                pay_email.setText(eventProfile.getEmail());
                pay_con.setText(eventProfile.getMob_no());
                pay_amt.setText(eventProfile.getAmount());
                a =pay_amt.getText().toString();

                Log.d("ABCDEF","this is snapshot" + snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RemainingPayment_activity.this, error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b =pay_balance.getText().toString();
                a =pay_amt.getText().toString();

//                total_payment = Integer.toString(amt);
                int previousAmount = Integer.parseInt(b);
                int enteredAmount = Integer.parseInt(a);
                int totalAmount = previousAmount + enteredAmount;
                Log.d("ABCDEF", "this is total amount: " + totalAmount);
//                if(totalAmount == 90)
                    event = pay_event.getText().toString().trim();
                    name = pay_name.getText().toString().trim();
                    email = pay_email.getText().toString().trim();
                    contact = pay_con.getText().toString().trim();
                    amount = Integer.toString(total_payment);
                if(validate(totalAmount, event)) {

                    Event_profile event_profile = new Event_profile(event, name, email, contact, String.valueOf(totalAmount));

                    databaseReference.setValue(event_profile);
                    finish();
                }else {

                 Toast.makeText(RemainingPayment_activity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private Boolean validate(int amt, String event){
        boolean result = false;
       /* if(event.isEmpty() || name.isEmpty() || email.isEmpty() || contact.isEmpty() || a.isEmpty() || b.isEmpty()) {
            Toast.makeText(this, "Enter All The Details", Toast.LENGTH_SHORT).show();
        }else {

//            amt = Integer.parseInt(amount);
            if (amt == 0) {
                result = false;
                Toast.makeText(this, "Full payment required. ", Toast.LENGTH_SHORT).show();
            }else {*/
                switch (event) {
                    case "Cs Go":
                    case "Carrom":
                        if(amt == 20){
                            result = true;
                        }else {
                            Toast.makeText(this, "Full payment required. ", Toast.LENGTH_SHORT).show();
                                                        return false;
                        }
                        break;
                    case "PUBG(Duo)":if (amt == 40) {
                        result = true;
                    }else {
                        Toast.makeText(this, "Full payment required. ", Toast.LENGTH_SHORT).show();
                                                return false;
                    }
                        break;

                    case "PUBG(Squad)":
                    case "DBugging":
                    case "Protovision":
                    case "Rugby":
                    case "Unravel": if (amt == 80) {
                        result = true;
                    }else {
                        Toast.makeText(this, "Full payment required. ", Toast.LENGTH_SHORT).show();
                                                return false;
                    }
                    break;

                    case "Search In the Dark":
                    case "Dead Shot":
                    case "Knok It Down": if (amt == 90) {
//                        result = true;
                        return true;
                    }else {
                        Toast.makeText(this, "Full payment required. ", Toast.LENGTH_SHORT).show();
                                                return false;
                    }

                    case "VR": if (amt == 100) {
                        result = true;
                    }else {
                        Toast.makeText(this, "Full payment required. ", Toast.LENGTH_SHORT).show();
                                                return false;
                    }
                        break;

                    case "IPL": if (amt == 200) {
                        result = true;
                    }else {
                        Toast.makeText(this, "Full payment required. ", Toast.LENGTH_SHORT).show();
                                                return false;
                    }
                        break;
                }
//            }


//        }
        return result;

    }

    private void paymentDetails() {
        pay_event = findViewById(R.id.pay_event);
        pay_name =findViewById(R.id.pay_name);
        pay_email = findViewById(R.id.pay_email);
        pay_con = findViewById(R.id.pay_con);
        pay_amt = findViewById(R.id.pay_amt);
        pay_balance =findViewById(R.id.pay_balanceamt);
        payment_btn =findViewById(R.id.payment_btn);
    }
}