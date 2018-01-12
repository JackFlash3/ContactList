package com.example.prodigalson7.contactlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.TextView;

import com.example.prodigalson7.contactlist.Concurrency.ContactsDownloader;
import com.example.prodigalson7.contactlist.business.ConnectionDetails;
import com.example.prodigalson7.contactlist.business.Contact;
import com.example.prodigalson7.contactlist.dbl.DataBaseConnector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Local variables
    //===============
    TextView title;
    private DataBaseConnector contactsDB = new DataBaseConnector(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.title = (TextView)findViewById(R.id.title);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //1. start downloader
        ConnectionDetails.getInstance().setConnection(true);
        ContactsDownloader downloader = new ContactsDownloader(this, contactsDB,  title);
        downloader.execute();

    }

    ///<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<TOOLS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//



    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END TOOLS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
}
