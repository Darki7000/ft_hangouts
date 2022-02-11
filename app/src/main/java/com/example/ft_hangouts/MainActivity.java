package com.example.ft_hangouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    static ContactDatabase db;
    ContactAdapter adapter;
    RecyclerView rvContacts;

    private IntentFilter intentFilter;

    private final BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            String phone = extras.getString("number");
            Contact c = MainActivity.findContactByPhone(phone);
            if (c != null) {
                Message m = new Message(0, c.getId(), extras.getString("message"), false);
                db.insertMessage(m);
                onResume();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.SEND_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.CALL_PHONE}, 1);

        db = new ContactDatabase(this);
        adapter = new ContactAdapter(db.listContacts(), this);

        rvContacts = (RecyclerView) findViewById(R.id.contactList);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        Button myButton = (Button) findViewById(R.id.add_button);
        myButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreateContactActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(intentReceiver, intentFilter);
        adapter = new ContactAdapter(db.listContacts(), this);
        rvContacts.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(intentReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.colour, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.colourGreen:
                Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4CAF50")));
                return true;
            case R.id.colourRed:
                Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Contact findContactByPhone(String phone) {
        ArrayList<Contact> contactsList = db.listContacts();
        for (Contact c: contactsList) {
            if (c.getPhone().equals(phone))
                return c;
        }
        return null;
    }
}