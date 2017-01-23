package com.volkangurbuz.smsmanagerdemo;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SmsActivity extends AppCompatActivity {

    EditText numara, mesaj;
    Button yolla;

    private static final int SMS_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        numara = (EditText) findViewById(R.id.numaraID);
        mesaj = (EditText) findViewById(R.id.mesajID);
        yolla = (Button) findViewById(R.id.button);

        if (!izinVar()) {
            kullaniciIzni();
        }

        yolla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mesajYolla();
            }
        });

    }

    public void mesajYolla() {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(numara.getText().toString(), null, mesaj.getText().toString(), null, null);

    }


    private void kullaniciIzni() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_REQUEST);

    }


    private boolean izinVar() {

        int resultSms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if (resultSms == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }



}
