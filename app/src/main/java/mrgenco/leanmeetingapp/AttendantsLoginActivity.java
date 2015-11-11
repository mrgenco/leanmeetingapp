package mrgenco.leanmeetingapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class AttendantsLoginActivity extends ActionBarActivity {

    DBTools dbTools = new DBTools(this);


    Button loginAttendantButton = null;
    EditText attendantEmailEditText = null;
    EditText attendantTicketIDEditText = null;
    TextView errorTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendants_login);


        loginAttendantButton = (Button) findViewById(R.id.loginAttendantButton);
        attendantEmailEditText = (EditText) findViewById(R.id.attendantEmailEditText);
        attendantTicketIDEditText = (EditText) findViewById(R.id.attendantTicketIDEditText);
        errorTextView = (TextView) findViewById(R.id.errorTextView);


    }

    public void loginAttendant(View view){

        String email = attendantEmailEditText.getText().toString();
        String ticket_id = attendantTicketIDEditText.getText().toString();

        HashMap<String,String> attendantInfo = dbTools.getAttendantInfo(ticket_id);

        if(!attendantInfo.isEmpty() && attendantInfo.get("ticket_id").equals(ticket_id)){

            Toast.makeText(this.getApplicationContext(), "Login Successfull!"+ attendantInfo.get("name") , Toast.LENGTH_LONG);

            errorTextView.setText("");

            Intent loginSuccessfullIntent = new Intent(this, LoginSuccessfullActivity.class);
            startActivity(loginSuccessfullIntent);
        }
        else{
            errorTextView.setText("Invalid Credentials!");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attendants_login, menu);
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
