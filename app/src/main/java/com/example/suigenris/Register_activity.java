package com.example.suigenris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner reg_post;
    private EditText reg_name;
    private EditText reg_email;
    private EditText reg_password;
    private EditText reg_confrim_password;
    private EditText reg_evtv;
    private Button reg_button;
    private ImageView reg_iv1, reg_iv2;
    private TextView tv_ald_reg;
    private FirebaseAuth firebaseAuth;
    private View reg_view;
    private DatabaseReference databaseReference;
    String  password, name, email, confrim_password, text;
    int eventRegistered = 0;
    User_profile user_profile;
//    private SharedPreferences preferences;

    String[] post = {"Post", "Decoration", "Documentation", "Event","PRO", "Technical", "Social", "Sport"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        reg_view = findViewById(R.id.reg_view);
        reg_name = findViewById(R.id.reg_name);
        reg_email = findViewById(R.id.reg_email);
        reg_password = findViewById(R.id.reg_password);
        reg_confrim_password = findViewById(R.id.et_reg_cp);
        reg_button = findViewById(R.id.reg_button);
        tv_ald_reg = findViewById(R.id.tv_ald_reg);
        reg_iv1 = findViewById(R.id.reg_iv1);
        reg_iv2 = findViewById(R.id.reg_iv2);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("user");
        reg_post = findViewById(R.id.reg_post);
        reg_post.setOnItemSelectedListener(this);


        user_profile = new User_profile();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, post);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reg_post.setAdapter(arrayAdapter);


        tv_ald_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register_activity.this, MainActivity.class));
            }
        });




        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = reg_name.getText().toString();
                password =reg_password.getText().toString();
                confrim_password = reg_confrim_password.getText().toString();
                email = reg_email.getText().toString();
                text = reg_post.getSelectedItem().toString();
                GlobalClass globalClass = new GlobalClass();
                if(globalClass.validate(getApplicationContext(), email, password, name, confrim_password, text)){
                    if(pass()){
                        email = reg_email.getText().toString().trim();
                        password = reg_password.getText().toString().trim();

                        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){
                                    sendUserData();
                                    Toast.makeText(Register_activity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(Register_activity.this, MainActivity.class));
                                }else {
                                    Toast.makeText(Register_activity.this, "Registration Faild", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }


                }
            }
        });


    }


    private Boolean validate(){
        Boolean result = false;

        name = reg_name.getText().toString();
        password =reg_password.getText().toString();
        confrim_password = reg_confrim_password.getText().toString();
        email = reg_email.getText().toString();
        text = reg_post.getSelectedItem().toString();


        if(name.isEmpty() || password.isEmpty() || confrim_password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please Enter All Details", Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }
        return result;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        text = reg_post.getSelectedItem().toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        User_profile user_profile = new User_profile( text, name, email, password, eventRegistered);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        Log.d("TEST", "Register : "+ firebaseAuth.getUid());
        editor.putString("userUid", firebaseAuth.getUid());
        editor.apply();
        myRef.setValue(user_profile);
    }


    private Boolean pass(){
        Boolean result = true;

        if(password.compareTo(confrim_password)==0){

        }else {
            result = false;
            Toast.makeText(this, "Password and Confirm password Must be Same", Toast.LENGTH_SHORT).show();
        }
        return result;

    }

}