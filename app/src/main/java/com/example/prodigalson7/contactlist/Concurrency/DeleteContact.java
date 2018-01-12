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

public class DeleteContact extends AsyncTask<Void,Void,Void> {

    private Context context;
    private DataBaseConnector contactsDB;
    private List<Contact> contactsList;
    private ListView mContactsLV;
    private int index;


    public DeleteContact(Context context, DataBaseConnector contactsDB, List<Contact> contactsList, ListView mContactsLV, int index) {
        super();
        this.context = context;
        this.contactsDB = contactsDB;
        this.contactsList = contactsList;
        this.mContactsLV = mContactsLV;
        this.index = index;
    }

    @Override
    protected Void doInBackground(Void... params) {

        //1. Remove all records from this.destinationsList
        this.contactsDB.deleteContact(index);
        this.contactsList.remove(index);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(null);
        //Draw the ListView
        this.mContactsLV.setAdapter(null);
        ContactsAdapter adapter = new ContactsAdapter(context, android.R.layout.simple_list_item_1, contactsList);
        this.mContactsLV.setAdapter(adapter);
        mContactsLV.invalidate();

    }


}
