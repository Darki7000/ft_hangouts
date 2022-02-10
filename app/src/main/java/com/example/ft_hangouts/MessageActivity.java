package com.example.ft_hangouts;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {
    private Contact contact;
    private ArrayList<Message> messList;
    private ImageButton sendButton;
    private TextView messageText;
    private LinearLayout messagesList;
    SmsManager smsManager;
    private IntentFilter intentFilter;

    private final BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onResume();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);

        sendButton = (ImageButton) findViewById(R.id.sendButton);
        messageText = (TextView) findViewById(R.id.messageText);
        messagesList = (LinearLayout) findViewById(R.id.messageLinearLayout);

        String phone = getIntent().getStringExtra("num");
        contact = MainActivity.findContactByPhone(phone);
        messList = MainActivity.db.listMessages(contact.getId());
        smsManager = SmsManager.getDefault();
        setTitle(contact.getName());
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        if (!messList.isEmpty()) {
            displayAllMessages();
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    sendMessage(new Message(0, contact.getId(), messageText.getText().toString(), true));
                    messageText.setText("");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(intentReceiver, intentFilter);

        messList = MainActivity.db.listMessages(contact.getId());
        messagesList.removeAllViews();
        displayAllMessages();
    }

    private boolean checkInput() {
        if(TextUtils.isEmpty(messageText.getText().toString())) {
            Toast.makeText(this, "Message is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void sendMessage(Message message) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == -1) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},
                    1);
            return;
        }
        String SENT="SMS_SENT";
        String DELIVERED="SMS_DELIVERED";

        PendingIntent sentPI= PendingIntent.getBroadcast(this,0,
                new Intent(SENT),0);

        PendingIntent deliveredPI= PendingIntent.getBroadcast(this,0,
                new Intent(DELIVERED),0);

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1){
                switch(getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(),R.string.sms_sent,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(),R.string.generic_failure,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(),R.string.no_service,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(),R.string.null_pdu,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(),R.string.radio_off,
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        },new IntentFilter(SENT));

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1){
                switch(getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(),R.string.sms_delivered,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(),R.string.sms_not_delivered,
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        },new IntentFilter(DELIVERED));

        smsManager.sendTextMessage(contact.getPhone(), null, message.getMessage(), sentPI, deliveredPI);
        MainActivity.db.insertMessage(message);
        messList.add(message);
        displayMessage(message);
    }

    private void displayAllMessages() {
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (190 * scale + 0.5f);

        for (Message m : messList) {
            if (!m.getIsFromMe()) {
                messagesList.addView(getMessageFromTextView(pixels, m.getMessage()));
            }
            else {
                messagesList.addView(getMessageToTextView(pixels, m.getMessage()));
            }
        }
    }

    private void displayMessage(Message m) {
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (190 * scale + 0.5f);
        if (!m.getIsFromMe()) {
            messagesList.addView(getMessageFromTextView(pixels, m.getMessage()));
        }
        else {
            messagesList.addView(getMessageToTextView(pixels, m.getMessage()));
        }
    }

    private TextView getMessageFromTextView(int pixels, String text) {
        LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(pixels, LinearLayout.LayoutParams.WRAP_CONTENT);
        messageParams.gravity = Gravity.LEFT;
        TextView messageFrom = new TextView(this);
        messageFrom.setLayoutParams(messageParams);
        messageFrom.setText(text);
        messageFrom.setTextSize(14);
        messageFrom.setBackgroundColor(Color.parseColor("#00BCD4"));
        return messageFrom;
    }

    private TextView getMessageToTextView(int pixels, String text) {
        LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(pixels, LinearLayout.LayoutParams.WRAP_CONTENT);
        messageParams.gravity = Gravity.RIGHT;
        TextView messageTo = new TextView(this);
        messageTo.setLayoutParams(messageParams);
        messageTo.setText(text);
        messageTo.setTextSize(14);
        messageTo.setBackgroundColor(Color.parseColor("#8BC34A"));
        return messageTo;
    }
}
