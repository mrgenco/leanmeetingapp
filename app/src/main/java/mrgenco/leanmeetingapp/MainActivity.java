package mrgenco.leanmeetingapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;


// The first activity in the application
// User have two options to login to the app (moderator and attendant)

public class MainActivity extends ActionBarActivity {


    Button attendantsButton, moderatorButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attendantsButton = (Button) findViewById(R.id.attendantsButton);
        moderatorButton = (Button) findViewById(R.id.moderatorButton);


    }

    // Opens login interface for moderators

    public void moderatorLogin(View view){

        Intent moderatorActivity = new Intent(this, ModeratorActivity.class);

        startActivity(moderatorActivity);

    }
    // Opens login interface for attendants

    public void attendantLogin(View view){

        Intent attendantActivity = new Intent(this, AttendantsLoginActivity.class);

        startActivity(attendantActivity);

    }

}