package com.example.ft_hangouts;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateContactActivity extends AppCompatActivity {
    EditText name, phone, email, address, zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        name = findViewById(R.id.TextName);
        phone = findViewById(R.id.TextPhone);
        email = findViewById(R.id.TextEmail);
        address = findViewById(R.id.TextAddress);
        zip = findViewById(R.id.TextZip);

        ContactDatabase db = new ContactDatabase(getApplicationContext());

        Button myButton = (Button) findViewById(R.id.button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    Contact con = new Contact(name.getText().toString(), phone.getText().toString(),
                            email.getText().toString(), address.getText().toString(), zip.getText().toString());
                    Toast.makeText(getApplicationContext(), con.getContactName(), Toast.LENGTH_LONG).show();
                    if (db.InsertContact(con)) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });
    }

    public boolean checkInput() {
        if(TextUtils.isEmpty( name.getText().toString())) {
            Toast.makeText(this, "Name is required", Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.isEmpty( phone.getText().toString())) {
            Toast.makeText(this, "Phone is required", Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(email.getText().toString().trim()) ||
                !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_LONG).show();
            return false;
        }
        if(phone.getText().toString().length() < 10) {
            Toast.makeText(this, "Phone must be 10 digits", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
