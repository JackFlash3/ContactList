package com.example.prodigalson7.contactlist.dbl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseConnector 
{// DAL - Data Access Layer
	// database name
	private static final String DATABASE_NAME = "contacts";

	///////////////////Tags TABLE////////////////////////
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


	private MyDataBase databaseOpenHelper = null; // database helper
	private SQLiteDatabase database = null; // database object

	// public constructor for DatabaseConnector
	public DataBaseConnector(Context context)
	{
		databaseOpenHelper = new MyDataBase(context, DATABASE_NAME, null, 1);
	}

	// open the database connection
	public void open() throws SQLException
	{
		// create or open a database for reading/writing
		database = databaseOpenHelper.getWritableDatabase();
	}

	// close the database connection
	public void close() 
	{
		if (database != null)
			database.close(); // close the database connection
	}

	//===============================CONTACTS TABLE queries=============================//
	// inserts a new Contact into the Contacts table
	public void insertContact(String id, String name, String email, String address,
												String gender, String mobile, String home, String office)
	{
		ContentValues newContact = new ContentValues();
		newContact.put(CONTACT_ID, id);
		newContact.put(NAME, name);
		newContact.put(EMAIL, email);
		newContact.put(ADDRESS, address);
		newContact.put(GENDER, gender);
		newContact.put(PHONE_MOBILE, mobile);
		newContact.put(PHONE_HOME, home);
		newContact.put(PHONE_OFFICE, office);

		open();
		// null to inform android that there is no col id to be null
		database.insert(CONTACTS_TABLE	, null, newContact);
		close();
	}

	// return a Cursor with all open tags information in the database
	public Cursor getAllCONTACS() {

		open();
		Cursor c = database.rawQuery("SELECT * FROM "+
				CONTACTS_TABLE +" ORDER BY "+ NAME,  null);
		return c;
	}

	// Delete a contact specified by the given id
	public void deleteContact(int id)
	{
		open(); // open the database
		database.delete(CONTACTS_TABLE,
				ID +"=" + id,//where
				null);//where argument for where placeholder's
		close();
	}



	//===========================END TAGS TABLE queries=========================//





}
