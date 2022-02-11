package com.example.ft_hangouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class MessageMonitor extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle myBundle = intent.getExtras();
        SmsMessage[] messages;
        StringBuilder strNumber = new StringBuilder();
        StringBuilder strMessage = new StringBuilder();

        if (myBundle != null) {
            Object [] pdus = (Object[]) myBundle.get("pdus");
            messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++)
            {
                String format = myBundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                strNumber.append(messages[i].getOriginatingAddress());
                strMessage.append(messages[i].getMessageBody());
            }
            //Toast.makeText(context, "Message from: " + strNumber, Toast.LENGTH_SHORT).show();
            Intent broadcastReceiver = new Intent();
            broadcastReceiver.setAction("SMS_RECEIVED_ACTION");
            broadcastReceiver.putExtra("number", strNumber.toString());
            broadcastReceiver.putExtra("message", strMessage.toString());
            context.sendBroadcast(broadcastReceiver);
            abortBroadcast();
        }
    }
}