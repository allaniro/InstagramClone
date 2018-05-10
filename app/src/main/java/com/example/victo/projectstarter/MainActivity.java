package com.example.victo.projectstarter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseInstallation;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText usernameField;
    EditText passwordField;
    TextView loginTextView;
    Boolean signUpModeActive;
    Button signUpButton;
    ImageView logo1;
    RelativeLayout relativeLayout;

    public void signUpOrLogin(View view){

        if (signUpModeActive == true) {

            // ParseObject.create("signUpOrLogin");

            ParseUser user = new ParseUser();
            user.setUsername(String.valueOf(usernameField.getText()));
            user.setPassword(String.valueOf(passwordField.getText()));

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null) {

                        Log.i("App", "signup successful");
                        Intent i = new Intent(getApplicationContext(),UserList.class);
                        startActivity(i);
                    } else {

                        Toast.makeText(getApplicationContext(), e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            ParseUser.logInInBackground(String.valueOf(usernameField.getText()), String.valueOf(passwordField.getText()), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null){
                        Log.i("AppInfo", "Log in successful");
                        Intent i = new Intent(getApplicationContext(),UserList.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(getApplicationContext(), e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        if (ParseUser.getCurrentUser() != null) {
//
//            Intent i = new Intent(getApplicationContext(), UserList.class);
//            startActivity(i);
//
//        }



            signUpModeActive = true;

            usernameField = findViewById(R.id.username);
            passwordField = findViewById(R.id.password);
            loginTextView = findViewById(R.id.login);
            signUpButton = findViewById(R.id.signUpButton);
            logo1 = findViewById(R.id.logo1);
            relativeLayout = findViewById(R.id.relativeLayout);

            usernameField.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                        signUpOrLogin(v);
                    }
                    return false;

                }

            });

            loginTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (v.getId() == R.id.login) {
                        if (signUpModeActive == true) {

                            signUpModeActive = false;
                            loginTextView.setText("Sign Up");
                            signUpButton.setText("Log in");
                        } else {
                            signUpModeActive = true;
                            loginTextView.setText("Log in");
                            signUpButton.setText("Sign up");

                        }
                    } else if (v.getId() == R.id.logo1 || v.getId() == R.id.relativeLayout) {

                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
                    }

                }
            });


        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
