package com.example.xfoodz.ledexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String name = "BCM4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PeripheralManager pioManager = PeripheralManager.getInstance();
        try {
            final Gpio ledPin = pioManager.openGpio(name);
            ledPin.setEdgeTriggerType(Gpio.EDGE_NONE);
            ledPin.setActiveType(Gpio.ACTIVE_HIGH);
            ledPin.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            final Button button = findViewById(R.id.button_id);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try{
                        boolean status = ledPin.getValue();
                        if(status) {
                            ledPin.setValue(false);
                        }
                        else{
                            ledPin.setValue(true);
                        }
                    }
                    catch (IOException e){
                        Log.e(TAG, "Error getting GPIO status", e);
                    }
                }
            });
        }
        catch (IOException e) {
            Log.e(TAG, "Error initializing GPIO: " + name, e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
