package com.example.suigenris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView login;
    private View main_view;
    private EditText email;
    private EditText password;
    private Button login_btn;
    private TextView tv_new_user;
    private ImageView main_iv;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.tv_main_login);
        main_view = findViewById(R.id.main_view);
        password = findViewById(R.id.main_password);
        main_iv = findViewById(R.id.main_iv);
        email = findViewById(R.id.main_email);
        login_btn = findViewById(R.id.main_login_btn);
        tv_new_user = findViewById(R.id.tv_new_user);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            startActivity(new Intent(MainActivity.this, Event_activity.class));
        }

        tv_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register_activity.class))
                ;
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        GlobalClass globalClass = new GlobalClass();
                String user_email = email.getText().toString();
                String user_password =password.getText().toString();
                if(globalClass.validate(getApplicationContext(), user_email, user_password, null, null, null)){
                    progressDialog.setMessage("Be patient");
                    firebaseAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if(task.isSuccessful()) {
                                progressDialog.dismiss();
                                SharedPreferences preferences  = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("userUid", firebaseAuth.getUid());
                                editor.apply();
                                Log.d("TEST", "login  :  " + firebaseAuth.getUid());
                                Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, Event_activity.class));
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Login Faild", Toast.LENGTH_SHORT).show();


                            }
                        }
                    });
                }

            }
        });


    }



    private Boolean validate(){
        Boolean result = false;
        String user_email = email.getText().toString();
        String user_password =password.getText().toString();
        if(user_email.isEmpty() || user_password.isEmpty()  ){
            Toast.makeText(this, "Enter the Email and Password", Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }
        return result;

    }
}