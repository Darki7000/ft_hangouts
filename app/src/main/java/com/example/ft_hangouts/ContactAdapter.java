package com.example.ft_hangouts;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText, phoneText;
        public ImageView deleteContact;
        public ImageView callContact;
        public ImageView messContact;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView)itemView.findViewById(R.id.nameText);
            phoneText = (TextView)itemView.findViewById(R.id.phoneText);
            deleteContact = (ImageView)itemView.findViewById(R.id.deleteButton);
            callContact = (ImageView)itemView.findViewById(R.id.callButton);
            messContact = (ImageView)itemView.findViewById(R.id.messageButton);
        }
    }
    Context _context;
    Activity _activity;
    private ContactDatabase db;
    private final ArrayList<Contact> mContacts;

    public ContactAdapter(ArrayList<Contact> contacts, Activity activity) {
        mContacts = contacts;
        _activity = activity;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        _context = parent.getContext();
        db = new ContactDatabase(_context);
        LayoutInflater inflater = LayoutInflater.from(_context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.contact_list_layout, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder holder, int pos) {
        // Get the data model based on position
        Contact contact = mContacts.get(pos);

        // Set item views based on your views and data model
        holder.nameText.setText(contact.getName());
        holder.phoneText.setText(contact.getPhone());

        holder.deleteContact.setOnClickListener(view -> {
            //delete row from database
            db.deleteContact(mContacts.get(pos).getId());
            mContacts.remove(pos);
            notifyItemRemoved(pos);
        });

        holder.nameText.setOnClickListener((v -> {
            Intent intent = new Intent(_context, CreateContactActivity.class);
            intent.putExtra("id", mContacts.get(pos).getId());
            _context.startActivity(intent);
        }));

        holder.messContact.setOnClickListener(v -> mess(pos));

        holder.callContact.setOnClickListener(v -> call(pos));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public void call(int pos) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mContacts.get(pos).getPhone()));
        final int REQUEST_PHONE_CALL = 1;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(_context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(_activity, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            }
            else {
                _context.startActivity(intent);
            }
        }
        else {
            _context.startActivity(intent);
        }
    }

    public void mess(int pos) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + mContacts.get(pos).getPhone()));
        intent.putExtra("sms_body", "");
        _context.startActivity(intent);
    }
}
