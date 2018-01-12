package com.example.prodigalson7.contactlist.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.prodigalson7.contactlist.R;
import com.example.prodigalson7.contactlist.business.Contact;

import java.util.List;

/**
 * Created by ProdigaLsON7 on 25/12/2017.
 */

public class ContactsAdapter extends ArrayAdapter<Contact> {

    //Variables
    Context context;    //receiving the DetailsActivity
    List<Contact> contactsList;
    Contact currentContact;

    public ContactsAdapter(Context context, int resource, List<Contact> contactsList)
    {
        super(context, resource);
        this.context = context;
        this.contactsList = contactsList;
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return contactsList.size();
    }

    @Override
    public Contact getItem(int position)
    {
        return contactsList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /*private view holder class*/
    private class ViewHolder
    {
        EditText name;
        EditText phone;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        convertView = mInflater.inflate(R.layout.contact_list_item, null);
        ViewHolder holder = new ViewHolder();

        holder.name = (EditText) convertView.findViewById(R.id.name_ET);
        holder.phone = (EditText) convertView.findViewById(R.id.phone_ET);

        currentContact = getItem(position);
        //set values for the EditText
        holder.name.setText(currentContact.getName());
        holder.phone.setText(currentContact.getPhone().getMobile());

        return convertView;
    }

}
