package com.volkangurbuz.bluetoothbuldemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

public class BluetoothActivity extends AppCompatActivity {

    ToggleButton tButton;
    BluetoothAdapter bluetoothAdapter;
    static final int REQUEST_ENABLE_BT = 0;
    Button cihazBul;
    ListView listView;
    ArrayAdapter adapter;
    ProgressBar pBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);


        cihazBul = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);
        tButton = (ToggleButton) findViewById(R.id.toggleButton);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        pBar = (ProgressBar) findViewById(R.id.progressBar);
        pBar.setVisibility(View.INVISIBLE);

        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tButton.isChecked()) {

                    if (cihazKullanilabilir()) {

                        if (!bluetoothAdapter.isEnabled()) {
                            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(i, REQUEST_ENABLE_BT);
                        } else {
                            Toast.makeText(getApplicationContext(), "Bluetooth zaten açık", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else
                        Toast.makeText(getApplicationContext(), "Cihazın Bluetooth Özelliği Yok", Toast.LENGTH_SHORT).show();

                } else {
                    bluetoothAdapter.disable();
                    Toast.makeText(getApplicationContext(), "Bluetooth Kapandı", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cihazBul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pBar.setVisibility(View.VISIBLE);
                taramayaBasla();

            }
        });


    }

    private boolean cihazKullanilabilir() {
        if (bluetoothAdapter == null)
            return false;
        else
            return true;
    }


    private void taramayaBasla() {
        registerReceiver(bulunanSonuc,
                new IntentFilter(BluetoothDevice.ACTION_FOUND));
        if (bluetoothAdapter.isEnabled() && !bluetoothAdapter.isDiscovering()) {
            adapter.clear();
            bluetoothAdapter.startDiscovery();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth açık konumda olmalı", Toast.LENGTH_SHORT).show();


        }
    }

    BroadcastReceiver bulunanSonuc = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {


                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                adapter.add(bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress());

            }
            pBar.setVisibility(View.INVISIBLE);


        }


    };

}


