package com.example.prodigalson7.contactlist;
//****https://github.com/JackFlash3/ContactList.git
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.prodigalson7.contactlist.Concurrency.DeleteContact;
import com.example.prodigalson7.contactlist.Concurrency.LoadContactsFromDB;
import com.example.prodigalson7.contactlist.business.Contact;
import com.example.prodigalson7.contactlist.dbl.DataBaseConnector;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    //local variables and Views
    List<Contact> contactsList = new ArrayList<Contact>();
    DataBaseConnector contactsDB = new DataBaseConnector(DetailsActivity.this);
    ListView mContactsLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //assign views
        this.mContactsLV = (ListView)findViewById(R.id.contactsLV);

        //assign Delegations of ListView
        this.mContactsLV.setOnItemClickListener(mItemClickListener);
        this.mContactsLV.setOnItemLongClickListener(mItemLongClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadContactsFromDB loader = new LoadContactsFromDB(this, contactsDB, contactsList, mContactsLV);
        loader.execute();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            this.finishAffinity();
        }
        return super.onKeyDown(keyCode, event);
    }

    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+contactsList.get(i).getPhone().getMobile()));
            startActivity(intent);
        }
    };

    AdapterView.OnItemLongClickListener mItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            DeleteContact deleteContact = new DeleteContact(DetailsActivity.this, contactsDB, contactsList, mContactsLV, i);
            deleteContact.execute();
            return true;
        }
    };

}
