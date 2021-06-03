package com.example.suigenris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Event_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private View event_view;
    private Spinner event_id;
    private TextView tv_event;
    private TextView event_name;
    private TextView event_email;
    private TextView event_con;
    private TextView event_amt;
    private String uPost, uName, uPassword, uEmail;
    private int uEventCount;
    User_profile updatedUserPro = new User_profile();
    String TAG = "TEST";
    String userUid;
    String eventUid;

    private FirebaseAuth firebaseAuth;
    private Button event_btn;
    String eve_name, name, mob_no, email, amount ;
    int amt;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    Event_profile event_profile;

    String[] event ={"Event", "Cs Go","PUBG(Duo)", "PUBG(Squad)", "Carrom", "Search In the Dark", "VR", "Dead Shot", "DBugging", "Protovision", "Rugby", "Unravel", "Knok It Down", "IPL"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_activity);

       setUpUI();
        firebaseAuth = FirebaseAuth.getInstance();

        event_profile = new Event_profile();

        SharedPreferences preferences  = PreferenceManager.getDefaultSharedPreferences(Event_activity.this);
        String myRef = preferences.getString("userUid", "");
        Log.d(TAG, "Event : " + myRef);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, event);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        event_id.setAdapter(adapter);
        foundDetails();

        event_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    Random rand = new Random();
                    String newmail = event_name.getText().toString().trim();
                    mob_no = event_con.getText().toString().trim();
                    newmail = newmail.replaceAll("\\s", "");
                    String completeEmail = newmail.toLowerCase() + rand.nextInt(1000) + "@gmail.com";


                    firebaseAuth.createUserWithEmailAndPassword(completeEmail, mob_no).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                send_event_data();
                                updateEventCounter();
                            }
                        }
                    });

                }else {

                }
            }
        });
    }

    private void setUpUI() {
        event_view = findViewById(R.id.event_view);
        event_id = findViewById(R.id.event_id);
        databaseReference = FirebaseDatabase.getInstance().getReference("event");
        event_btn = findViewById(R.id.event_btn);
        tv_event = findViewById(R.id.User_info);
        event_name = findViewById(R.id.event_name);
        event_email = findViewById(R.id. event_email);
        event_con = findViewById(R.id.event_con);
        event_amt = findViewById(R.id.event_amt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(Event_activity.this, MainActivity.class));
            }
            case R.id.scannerMenu:
                startActivity(new Intent(Event_activity.this, Scanner_Activity.class));
        }
        return super.onOptionsItemSelected(item);

    }

    private boolean validate() {
        boolean result = false;

        name = event_name.getText().toString().trim();
        email = event_email.getText().toString().trim();
        mob_no = event_con.getText().toString().trim();
        amount = event_amt.getText().toString().trim();
        eve_name = event_id.getSelectedItem().toString();

        if(name.isEmpty() || email.isEmpty() || mob_no.isEmpty() || amount.isEmpty() || eve_name.equals("Event")){
            Toast.makeText(this, "Enter All The Details",Toast.LENGTH_SHORT).show();
        } else {
            amt = Integer.parseInt(amount);
           // Log.d("myTag", "This is my message" + amt);
            if (amt == 0) {
                result = false;
                Toast.makeText(this, "Enter Valid Amount", Toast.LENGTH_SHORT).show();
            } else {
                switch (eve_name) {

                    case "Cs Go":

                    case "Carrom":
                        if (amt == 20) {
                            Toast.makeText(this, "Full Paid", Toast.LENGTH_SHORT).show();
                            result = true;
//                           getTextt();
                        } else if (amt < 20) {
                            Toast.makeText(this, "Not Full Paid", Toast.LENGTH_SHORT).show();
                            result = true;
                        } else if (amt > 20){
                            Toast.makeText(this, "Enter Correct Amount", Toast.LENGTH_SHORT).show();
                            result = false;
                        }
                        break;



                    case "PUBG(Duo)":if (amt == 40) {
                        Toast.makeText(this, "Full Paid", Toast.LENGTH_SHORT).show();
                        result = true;
//                        getTextt();
                    } else if (amt < 40) {
                        Toast.makeText(this, "Not Full Paid", Toast.LENGTH_SHORT).show();
                        result = true;
                    } else if (amt > 40){
                        Toast.makeText(this, "Enter Correct Amount", Toast.LENGTH_SHORT).show();
                        result = false;
                    }
                        break;


                    case "PUBG(Squad)":

                    case "DBugging":

                    case "Protovision":

                    case "Rugby":

                    case "Unravel": if (amt == 80) {
                        Toast.makeText(this, "Full Paid", Toast.LENGTH_SHORT).show();
                        result = true;
//                        getTextt();
                    } else if (amt < 80) {
                        Toast.makeText(this, "Not Full Paid", Toast.LENGTH_SHORT).show();
                        result = true;
                    } else if (amt > 80){
                        Toast.makeText(this, "Enter Correct Amount", Toast.LENGTH_SHORT).show();
                        result = false;
                    }
                        break;


                    case "Search In the Dark":

                    case "Dead Shot":

                    case "Knok It Down": if (amt == 90) {
                        Toast.makeText(this, "Full Paid", Toast.LENGTH_SHORT).show();
                        result = true;
//                        getTextt();
                    } else if (amt < 90) {
                        Toast.makeText(this, "Not Full Paid", Toast.LENGTH_SHORT).show();
                        result = true;
                    } else if (amt > 90){
                        Toast.makeText(this, "Enter Correct Amount", Toast.LENGTH_SHORT).show();
                        result = false;
                    }
                        break;


                    case "VR": if (amt == 100) {
                        Toast.makeText(this, "Full Paid", Toast.LENGTH_SHORT).show();
                        result = true;
//                        getTextt();
                    } else if (amt < 100) {
                        Toast.makeText(this, "Not Full Paid", Toast.LENGTH_SHORT).show();
                        result = true;
                    } else if (amt > 100){
                        Toast.makeText(this, "Enter Correct Amount", Toast.LENGTH_SHORT).show();
                        result = false;
                    }
                        break;


                    case "IPL": if (amt == 200) {
                        Toast.makeText(this, "Full Paid", Toast.LENGTH_SHORT).show();
                        result = true;
//                        getTextt();
                    } else if (amt < 200) {
                        Toast.makeText(this, "Not Full Paid", Toast.LENGTH_SHORT).show();
                        result = true;
                    } else if (amt > 200){
                        Toast.makeText(this, "Enter Correct Amount", Toast.LENGTH_SHORT).show();
                        result = false;
                    }
                        break;
                }

            }

        }
        return result;
    }

    private void getQrcode(String eventUidd) {
        Intent intent = new Intent(Event_activity.this, Qrcode_activity.class);
        intent.putExtra("keyevent",eve_name);
        intent.putExtra("keyname",name);
        //Log.d("some event uid in getTextt()", "event uid: " + eventUidd);
        intent.putExtra("keyuid", eventUidd);
        startActivity(intent);
    }

    private void send_event_data(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        eventUid = firebaseAuth.getUid();
        Log.d("some firebase uid " , "event Uid:" + eventUid);
        Event_profile event_profile = new Event_profile (eve_name, name, email, mob_no, amount);
        myRef.setValue(event_profile);
        getQrcode(eventUid);
    }

    private User_profile foundDetails(){
        SharedPreferences preferences  = PreferenceManager.getDefaultSharedPreferences(Event_activity.this);
        userUid = preferences.getString("userUid", "");
       // Log.d(TAG, "Event : " + userUid);
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(userUid);
        final User_profile[] userProfile = {new User_profile()};

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userProfile[0] = snapshot.getValue(User_profile.class);
                uPost = userProfile[0].getPost();
                uName = userProfile[0].getName();
                uEmail = userProfile[0].getEmail();
                uEventCount = userProfile[0].getEvent_reg();
                uPassword = userProfile[0].getPassword();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Event_activity.this, error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        return userProfile[0];
    }

    private void updateEventCounter(){
        uEventCount += 1;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userDatabaseReference = firebaseDatabase.getReference(userUid);
        User_profile updatedUserProfile = new User_profile(uPost, uName, uEmail, uPassword, uEventCount);
        userDatabaseReference.setValue(updatedUserProfile);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        eve_name = event_id.getSelectedItem().toString();

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}