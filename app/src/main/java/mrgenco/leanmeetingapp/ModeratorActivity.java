package mrgenco.leanmeetingapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class ModeratorActivity extends ActionBarActivity {


    DBTools dbTools = new DBTools(this);

    Button inviteAttendantsButton, showAttendantsButton, addMeetingNotesButton;
    EditText nameEditText, emailEditText, ticketIDEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moderator);

        inviteAttendantsButton = (Button)findViewById(R.id.inviteAttendantsButton);
        showAttendantsButton = (Button)findViewById(R.id.showAttendantsButton);
        addMeetingNotesButton = (Button)findViewById(R.id.addMeetingNotesButton);
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        ticketIDEditText = (EditText)findViewById(R.id.ticketIDEditText);

    }
    public void addMeetingNotes(View view){

        Intent meetingNotesActivity = new Intent(this, MeetingNotesActivity.class);

        startActivity(meetingNotesActivity);
    }



    public void inviteAttendant(View view){


        HashMap<String, String> queryValues = new HashMap<String,String>();

        // Get the contact name and email entered
        String attendantName = nameEditText.getText().toString();
        String attendantEmail = emailEditText.getText().toString();
        String attendantTicketID = ticketIDEditText.getText().toString();

        queryValues.put("name", attendantName);
        queryValues.put("email", attendantEmail);
        queryValues.put("ticket_id", attendantTicketID);

        dbTools.insertAttendant(queryValues);

        Toast.makeText(this, "Invitation has been sent successfully!", Toast.LENGTH_LONG);

    }

    public void showAttendants(View view){


        ArrayList<HashMap<String,String>> attendantArrayList = dbTools.getAllAttendants();

        String attendantList = "";

        for(int i=0; i<attendantArrayList.size(); i++){

            String id = attendantArrayList.get(i).get("id");
            String name = attendantArrayList.get(i).get("name");
            String email = attendantArrayList.get(i).get("email");
            String ticket_id = attendantArrayList.get(i).get("ticket_id");

            attendantList += id + " : " + name + " : " + email + " : " + ticket_id+  "\n";

        }

        Toast.makeText(this, attendantList, Toast.LENGTH_LONG).show();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_moderator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
