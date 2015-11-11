package mrgenco.leanmeetingapp;

/**
 * Created by mrgenco on 11/11/15.
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// SQLiteOpenHelper helps you open or create a database

public class DBTools extends SQLiteOpenHelper {

    // Context : provides access to application-specific resources and classes

    public DBTools(Context applicationcontext) {

        // Call use the database or to create it

        super(applicationcontext, "Attendants.db", null, 1);

    }

    // onCreate is called the first time the database is created

    public void onCreate(SQLiteDatabase database) {


        try {

            // How to create a table in SQLite
            // Make sure you don't put a ; at the end of the query

            String query = "CREATE TABLE IF NOT EXISTS attendants " +
                    "(id integer primary key, name VARCHAR, email VARCHAR, ticket_id VARCHAR);";

            // Executes the query provided as long as the query isn't a select
            // or if the query doesn't return any data

            database.execSQL(query);
        }
        catch(Exception e){
            Log.e("ATTENDANTS ERROR", "ERROR CREATING DATABASE");
        }

    }

    // onUpgrade is used to drop tables, add tables, or do anything
    // else it needs to upgrade
    // This is droping the table to delete the data and then calling
    // onCreate to make an empty table

    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query = "DROP TABLE IF EXISTS attendants";

        // Executes the query provided as long as the query isn't a select
        // or if the query doesn't return any data

        database.execSQL(query);
        onCreate(database);
    }

    public void insertAttendant(HashMap<String, String> queryValues) {

        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        // Stores key value pairs being the column name and the data
        // ContentValues data type is needed because the database
        // requires its data type to be passed

        ContentValues values = new ContentValues();

        values.put("name", queryValues.get("name"));
        values.put("email", queryValues.get("email"));
        values.put("ticket_id", queryValues.get("ticket_id"));

        // Inserts the data in the form of ContentValues into the
        // table name provided

        database.insert("attendants", null, values);

        // Release the reference to the SQLiteDatabase object

        database.close();
    }

    public int updateAttendant(HashMap<String, String> queryValues) {

        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        // Stores key value pairs being the column name and the data

        ContentValues values = new ContentValues();

        values.put("name", queryValues.get("name"));
        values.put("email", queryValues.get("email"));
        values.put("ticket_id", queryValues.get("ticket_id"));

        // update(TableName, ContentValueForTable, WhereClause, ArgumentForWhereClause)

        return database.update("attendants", values, "ticket_id" + " = ?", new String[] { queryValues.get("ticket_id") });
    }

    // Used to delete a contact with the matching contactId

    public void deleteAttendant(String id) {

        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        String deleteQuery = "DELETE FROM  attendants where ticket_id='"+ id +"'";

        // Executes the query provided as long as the query isn't a select
        // or if the query doesn't return any data

        database.execSQL(deleteQuery);
    }

    public ArrayList<HashMap<String, String>> getAllAttendants() {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> contactArrayList;

        contactArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT  * FROM attendants";

        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        // Cursor provides read and write access for the
        // data returned from a database query

        // rawQuery executes the query and returns the result as a Cursor

        Cursor cursor = database.rawQuery(selectQuery, null);

        // Move to the first row

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contactMap = new HashMap<String, String>();

                // Store the key / value pairs in a HashMap
                // Access the Cursor data by index that is in the same order
                // as used when creating the table

                contactMap.put("id", cursor.getString(0));
                contactMap.put("name", cursor.getString(1));
                contactMap.put("email", cursor.getString(2));
                contactMap.put("ticket_id", cursor.getString(3));


                contactArrayList.add(contactMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }

        // return contact list
        return contactArrayList;
    }

    public HashMap<String, String> getAttendantInfo(String id) {

        HashMap<String, String> contactMap = new HashMap<String, String>();

        // Open a database for reading only

        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM attendants where ticket_id='"+id+"'";

        // rawQuery executes the query and returns the result as a Cursor

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                contactMap.put("name", cursor.getString(1));
                contactMap.put("email", cursor.getString(2));
                contactMap.put("ticket_id", cursor.getString(3));

            } while (cursor.moveToNext());
        }
        return contactMap;
    }
}

