package mrgenco.leanmeetingapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;



// TODO : Moderator must be able to attach additional files before sending email

public class MeetingNotesActivity extends ActionBarActivity {


    DBTools dbTools = new DBTools(this);

    EditText meetingTitleEditText;
    EditText meetingScopeEditText;
    Button sendToAttendantsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_notes);

        meetingTitleEditText = (EditText) findViewById(R.id.meetingTitleEditText);
        meetingScopeEditText = (EditText) findViewById(R.id.meetingScopeEditText);
        sendToAttendantsButton = (Button) findViewById(R.id.sendToAttendantsButton);
    }


    // sendAttendant function sends the meeting notes to the attendants
    // default mail application must be opened with additional files,
    // title and body when this function triggered
    public void sendAttendant(View view){

        // Getting all attendants info
        ArrayList<HashMap<String,String>> attendantList = dbTools.getAllAttendants();

        ArrayList<String> mailListArray = new ArrayList<>();

        for(int i=0; i<attendantList.size(); i++){

            // Getting all emails from attendants
            String recipientMail = attendantList.get(i).get("email");
            mailListArray.add(recipientMail);
        }
        String[] mailList = new String[mailListArray.size()];
        String mailSubject = meetingTitleEditText.getText().toString();
        String mailBody = meetingScopeEditText.getText().toString();

        // Convert array list to string array
        mailList = mailListArray.toArray(mailList);


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, mailSubject);
        intent.putExtra(Intent.EXTRA_TEXT   , mailBody);
        intent.putExtra(Intent.EXTRA_EMAIL  , mailList);

        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meeting_notes, menu);
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
