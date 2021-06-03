package com.example.suigenris;

import android.content.Context;
import android.widget.Toast;

public class GlobalClass {

    public boolean validate(Context context, String userEmail, String userPassword, String name, String confirmPassword, String post ){

        if( userEmail.isEmpty()  || userPassword.isEmpty() ||
                (confirmPassword.equals(null) ||confirmPassword.isEmpty()) ||
                (name.equals(null) ||name.isEmpty()) || (post.equals(null) || post.equals("post")) ) {
            Toast.makeText(context, "Please enter all details", Toast.LENGTH_SHORT).show();
        }else {
            return true;
        }

        return false;

    }




}
