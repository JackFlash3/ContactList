package com.example.prodigalson7.contactlist.Concurrency;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.prodigalson7.contactlist.Adapters.ContactsAdapter;
import com.example.prodigalson7.contactlist.business.Contact;
import com.example.prodigalson7.contactlist.business.Phone;
import com.example.prodigalson7.contactlist.dbl.DataBaseConnector;

import java.util.List;

/**
 * Created by ProdigaLsON7 on 11/01/2018.
 */


public class LoadContactsFromDB extends AsyncTask<Void, Void, Void> {


    private Context context;
    private DataBaseConnector contactsDB;
    private List<Contact> contactsList;
    private ListView contactsLV;


    public LoadContactsFromDB(Context context, DataBaseConnector contactsDB, List<Contact> contactsList, ListView contactsLV) {
        super();
        this.context = context;
        this.contactsDB = contactsDB;
        this.contactsList = contactsList;
        this.contactsLV = contactsLV;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Cursor c;
        //1. Remove all records from this.destinationsList
        this.removeRecordsFromList();
        //2. Extract records from the Data Base
        c = this.contactsDB.getAllCONTACS();

        //3. append to the ListView only active and pending records
        retrieveDataFromSqlite(c);
        this.contactsDB.close();

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(null);
        //Draw the ListView
        ContactsAdapter adapter = new ContactsAdapter(context, android.R.layout.simple_list_item_1, contactsList);
        this.contactsLV.setAdapter(adapter);
        contactsLV.invalidate();

    }

    /////////////////////////////Service functions//////////////////////////////
    private void removeRecordsFromList() {
        Contact contact;

        int listSize = this.contactsList.size();
        for (int i = 0; i < listSize; i++) {
            contact = this.contactsList.remove(0);
        }
    }


    public void retrieveDataFromSqlite(Cursor c) {
        if (c.getCount() > 0) {
            while (c.moveToNext() == true) {
                Contact contact = new Contact(c.getInt(c.getColumnIndex("m_id")),
                        c.getString(c.getColumnIndex("_id")),
                        c.getString(c.getColumnIndex("name")),
                        c.getString(c.getColumnIndex("email")),
                        c.getString(c.getColumnIndex("address")),
                        c.getString(c.getColumnIndex("gender")),
                        new Phone(c.getString(c.getColumnIndex("mobile")), c.getString(c.getColumnIndex("home")),
                                c.getString(c.getColumnIndex("office"))));
                this.contactsList.add(contact);
            }
        }

    }
}