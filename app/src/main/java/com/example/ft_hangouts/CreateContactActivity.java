package com.example.ft_hangouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CreateContactActivity extends AppCompatActivity {
    EditText name, phone, email, address, zip;
    ContactDatabase db;
    int id = -1;
    private IntentFilter intentFilter;

    private final BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            String phone = extras.getString("number");
            Contact c = MainActivity.findContactByPhone(phone);
            if (c != null) {
                Message m = new Message(0, c.getId(), extras.getString("message"), false);
                MainActivity.db.insertMessage(m);
                onResume();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        id = getIntent().getIntExtra("id", -1);
        name = findViewById(R.id.TextName);
        phone = findViewById(R.id.TextPhone);
        email = findViewById(R.id.TextEmail);
        address = findViewById(R.id.TextAddress);
        zip = findViewById(R.id.TextZip);

        db = new ContactDatabase(getApplicationContext());

        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        getInput();

        Button myButton = (Button) findViewById(R.id.button);
        myButton.setOnClickListener(v -> {
            if (checkInput()) {
                Contact con = new Contact(0, name.getText().toString(), phone.getText().toString(),
                        email.getText().toString(), address.getText().toString(), zip.getText().toString());
                if (id != -1) {
                    con.setId(id);
                    if (db.updateContact(con)) {
                        Toast.makeText(getApplicationContext(), R.string.contact_changed, Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    if (db.insertContact(con)) {
                        Toast.makeText(getApplicationContext(), R.string.contact_created, Toast.LENGTH_LONG).show();
                    }
                }
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(intentReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(intentReceiver);
    }

    private boolean checkInput() {
        if(TextUtils.isEmpty( name.getText().toString())) {
            Toast.makeText(this, R.string.name_empty, Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.isEmpty( phone.getText().toString())) {
            Toast.makeText(this, R.string.phone_empty, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void getInput() {
        if (id != -1) {
            ArrayList<Contact> con = db.listContacts();
            int i = 0;
            while (con.get(i).getId() != id) {
                i++;
            }
            name.setText(con.get(i).getName());
            phone.setText(con.get(i).getPhone());
            email.setText(con.get(i).getEmail());
            address.setText(con.get(i).getAddress());
            zip.setText(con.get(i).getZip());
        }
    }
}
