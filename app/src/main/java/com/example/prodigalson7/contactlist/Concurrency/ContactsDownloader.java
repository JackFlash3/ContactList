package com.example.prodigalson7.contactlist.Concurrency;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prodigalson7.contactlist.DetailsActivity;
import com.example.prodigalson7.contactlist.MainActivity;
import com.example.prodigalson7.contactlist.business.ConnectionDetails;
import com.example.prodigalson7.contactlist.business.Contact;
import com.example.prodigalson7.contactlist.business.Phone;
import com.example.prodigalson7.contactlist.dbl.DataBaseConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

/**
 * Created by ProdigaLsON7 on 11/01/2018.
 */

public class ContactsDownloader extends AsyncTask<Void,String,Boolean> {
    private static final int CONNECTION_TIMEOUT = 1000;
    private final String URL = "https://api.androidhive.info/contacts/";

    long initTime = 0;
    private ConnectionDetails connectionDetails;
    private DataBaseConnector contactsDB;
    private Context context;
    private TextView title;

    public ContactsDownloader(Context context, DataBaseConnector contactsDB, TextView title)
    {
        super();
        this.connectionDetails = ConnectionDetails.getInstance();
        this.contactsDB = contactsDB;
        this.context = context;
        this.title = title;
    }

    @Override
    protected Boolean doInBackground(Void... params)
    {
        //2. check if downloading time is higher than 2secs then continue
        this.initTime = System.currentTimeMillis();

        boolean connectionResult = false;       //SUccess to obtain results from the server
        //1 check if the returned GeoPoint is null
        try {

            connectionResult = applyToServerForContacts();
            if (!connectionResult)
                ConnectionDetails.getInstance().setResult(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //make sure 2 seconds have passed
        while (initTime + DateUtils.SECOND_IN_MILLIS*2 > System.currentTimeMillis()) {}
        ConnectionDetails.getInstance().setConnection(false);


        //3. redirect to 2nd Activity
        return connectionResult;
    }


    protected void onPostExecute(Boolean aResult) {
        super.onPostExecute(aResult);
        //2. wait until state of the flag changes to false

        if (!aResult) {
            this.title.setText("Connection To InternetFailed  ");
        } else {
             redirectTo();
        }
    }

    //////////////////////<<<<<Service Functions>>>>>//////////////////////////
    boolean applyToServerForContacts() throws JSONException
    {
        JSONObject serverResponse = null;   //Result from the server
        InputStream mInputStream;
        Contact contact;

        try {
            mInputStream = getStream(URL);                                                  //create an inputStream pipe
            serverResponse = convertStreamToJSON(mInputStream);     //convert stream to a JSONObject
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        //Receive the Array of results from the received JSON object

        try {
            if (serverResponse != null) {

                JSONArray results =  serverResponse.getJSONArray("contacts");

                // loop among all results
                for (int i = 0; i < results.length(); i++) {
                    // loop among all addresses within this result
                    JSONObject result = results.getJSONObject(i);
                    if (result.has("id")) {
                        JSONObject jPhone = result.getJSONObject("phone");
                        if (jPhone.has("mobile")) {
                            contact = new Contact(1,
                                    result.getString("id"),
                                    result.getString("name"),
                                    result.getString("email"),
                                    result.getString("address"),
                                    result.getString("gender"),
                                    new Phone(jPhone.getString("mobile"), jPhone.getString("home"), jPhone.getString("office")));

                                //insert a contact to the DB
                                fillContactRecordInDB(contact);

                        }
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




        return false;
    }

    //append a Contact record to the DB
    private void fillContactRecordInDB(Contact contact)
    {
        contactsDB.insertContact(contact.getId(),
                contact.getName(),
                contact.getEmail(),
                contact.getAddress(),
                contact.getGender(),
                contact.getPhone().getMobile(),
                contact.getPhone().getHome(),
                contact.getPhone().getOffice());
    }

//Download the JSONObject using URLConnection
    private InputStream getStream(String mURL) {
        try {
            InputStream is = new URL(mURL).openStream();
            return is;
        } catch (Exception ex) {
            return null;
        }
    }


//Convert InputStream to JSONObject
    private JSONObject convertStreamToJSON(InputStream inputStreamObject) throws IOException, JSONException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStreamObject, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);

        JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
        return jsonObject;

    }

    private void redirectTo() {
        Intent intent = new Intent(context,DetailsActivity.class);
        context.startActivity(intent);
    }

    ///////////////////////<<<<<END service Functions>>>>>///////////////////////


}
