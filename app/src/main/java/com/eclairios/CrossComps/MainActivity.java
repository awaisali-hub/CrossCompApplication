package com.eclairios.CrossComps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.eclairios.CrossComps.Registration;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    CheckBox rememberMe;
    ProgressDialog progressDialog;
    Button SignInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        rememberMe = findViewById(R.id.rememberMe);
        SignInButton = findViewById(R.id.SignInButton);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String rememberEmail = preferences.getString("Email","");
        String rememberPassword = preferences.getString("Password","");

        if(rememberEmail != null && rememberPassword!=null){
            email.setText(rememberEmail);
            password.setText(rememberPassword);

//            if(!rememberMe.isChecked()){
//                rememberMe.setChecked(true);
//           //     moveToDashboard();
//            }

        }

//        SignInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                moveToDashboard();
//            }
//        });
    }

    public void registerScreen(View view) {
        startActivity(new Intent(MainActivity.this, Registration.class));
    }

    public void moveToDashboard(View view) {
        String strEmail = email.getText().toString();
        String strPassword = password.getText().toString();
        String method = "login";

        if(TextUtils.isEmpty(strEmail) && TextUtils.isEmpty(strPassword)){
            Toast.makeText(this, "Email And Password Are Required", Toast.LENGTH_SHORT).show();
        }else{


                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();


            SharedPreferences Preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor Editor = Preferences.edit();
            if(rememberMe.isChecked()){
                Editor.putString("Email",strEmail);
                Editor.putString("Password",strPassword);
                Editor.putString("Email123",strEmail);
                Editor.putString("Password123",strPassword);

            }else{
                Editor.putString("Email","");
                Editor.putString("Password","");
            }
            Editor.apply();

            BackgroundTask backgroundTask = new BackgroundTask(MainActivity.this);
            backgroundTask.execute(method,strEmail,strPassword);
        }
    }
}