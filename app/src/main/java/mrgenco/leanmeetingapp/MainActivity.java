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

public class MainActivity extends ActionBarActivity {

    SQLiteDatabase contactsDB = null;

    Button attendantsButton, moderatorButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attendantsButton = (Button) findViewById(R.id.attendantsButton);
        moderatorButton = (Button) findViewById(R.id.moderatorButton);


    }
    /*
    public void createDatabase(View view) {

        try{

            // Opens a current database or creates it
            // Pass the database name, designate that only this app can use it
            // and a DatabaseErrorHandler in the case of database corruption
            contactsDB = this.openOrCreateDatabase("MyContacts", MODE_PRIVATE, null);

            // Execute an SQL statement that isn't select
            contactsDB.execSQL("CREATE TABLE IF NOT EXISTS contacts " +
                    "(id integer primary key, name VARCHAR, email VARCHAR);");

            // The database on the file system
            File database = getApplicationContext().getDatabasePath("MyContacts.db");

            // Check if the database exists
            if (database.exists()) {
                Toast.makeText(this, "Database Created", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Database Missing", Toast.LENGTH_SHORT).show();
            }

        }

        catch(Exception e){

            Log.e("CONTACTS ERROR", "Error Creating Database");

        }

        // Make buttons clickable since the database was created
        addContactButton.setClickable(true);
        deleteContactButton.setClickable(true);
        getContactsButton.setClickable(true);
        deleteDBButton.setClickable(true);

    }*/


    public void moderatorLogin(View view){

        Intent moderatorActivity = new Intent(this, ModeratorActivity.class);

        startActivity(moderatorActivity);

    }

    public void attendantLogin(View view){

        Intent attendantActivity = new Intent(this, AttendantsLoginActivity.class);

        startActivity(attendantActivity);

    }
    /*public void addContact(View view) {

        // Get the contact name and email entered
        String contactName = nameEditText.getText().toString();
        String contactEmail = emailEditText.getText().toString();

        // Execute SQL statement to insert new data
        contactsDB.execSQL("INSERT INTO contacts (name, email) VALUES ('" +
                contactName + "', '" + contactEmail + "');");

    }*/

    public void getContacts(View view) {

        // A Cursor provides read and write access to database results
        Cursor cursor = contactsDB.rawQuery("SELECT * FROM ", null);

        // Get the index for the column name provided
        int idColumn = cursor.getColumnIndex("id");
        int nameColumn = cursor.getColumnIndex("name");
        int emailColumn = cursor.getColumnIndex("email");

        // Move to the first row of results
        cursor.moveToFirst();

        String contactList = "";

        // Verify that we have results
        if(cursor != null && (cursor.getCount() > 0)){

            do{
                // Get the results and store them in a String
                String id = cursor.getString(idColumn);
                String name = cursor.getString(nameColumn);
                String email = cursor.getString(emailColumn);

                contactList = contactList + id + " : " + name + " : " + email + "\n";

                // Keep getting results as long as they exist
            }while(cursor.moveToNext());

            //contactListEditText.setText(contactList);

        } else {

            Toast.makeText(this, "No Results to Show", Toast.LENGTH_SHORT).show();
            //contactListEditText.setText("");

        }

    }

   /* public void deleteContact(View view) {

        // Get the id to delete
        String id = idEditText.getText().toString();

        // Delete matching id in database
        contactsDB.execSQL("DELETE FROM contacts WHERE id = " + id + ";");

    }
*/
    public void deleteDatabase(View view) {

        // Delete database
        this.deleteDatabase("MyContacts");

    }

    @Override
    protected void onDestroy() {

        contactsDB.close();

        super.onDestroy();
    }

}