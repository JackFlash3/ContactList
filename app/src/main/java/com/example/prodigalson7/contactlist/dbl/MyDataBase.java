package com.example.prodigalson7.contactlist.dbl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBase extends SQLiteOpenHelper
{


	///////////////////CONTACTS TABLE////////////////////////

	private static final String CONTACTS_TABLE = "contacts";
	private static final String ID = "m_id";
	private static final String CONTACT_ID = "_id";
	private static final String NAME = "name";
	private static final String EMAIL = "email";
	private static final String ADDRESS = "address";
	private static final String GENDER = "gender";
	private static final String PHONE_MOBILE= "mobile";
	private static final String PHONE_HOME= "home";
	private static final String PHONE_OFFICE= "office";

	private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE "+ CONTACTS_TABLE
			+"("+ ID+" INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "+ CONTACT_ID + " TEXT, "
			+ NAME +" TEXT, "+ EMAIL + " TEXT,"+ ADDRESS +" TEXT, "
			+ GENDER +" TEXT, " + PHONE_MOBILE + " TEXT, "
			+ PHONE_HOME + " TEXT, " + PHONE_OFFICE + " TEXT);";


	public MyDataBase(Context context, String name, CursorFactory factory,
                      int version)
	{
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_TABLE_CONTACTS); 	// create CONTACTS_TABLE

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{


	}

}
