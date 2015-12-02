package com.example.andrea.airportobs;

import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import junit.framework.TestCase;

import org.w3c.dom.Text;

import android.os.Handler;


public class MainActivity extends AppCompatActivity {

    private Handler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inizializzo UI
        final EditText icaoCode = (EditText) findViewById(R.id.editText_icao);
        final TextView clouds = (TextView) findViewById(R.id.textView_clouds);
        final TextView windDirection = (TextView) findViewById(R.id.textView_windDirection);
        final TextView elevation = (TextView) findViewById(R.id.textView_elevation);
        final TextView latitude = (TextView) findViewById(R.id.textView_latitude);
        final TextView longitude = (TextView) findViewById(R.id.textView_longitude);

        Button button = (Button) findViewById(R.id.getIcao);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = icaoCode.getText().toString();
                getJSONObservation(code);
            }
        });

        myHandler = new android.os.Handler() {
            @Override
            public void handleMessage (Message msg) {
                Bundle b = msg.getData();
                WeatherObservation obs = (WeatherObservation) b.getSerializable("observation");

                clouds.setText(obs.getClouds());
                windDirection.setText(Integer.toString(obs.getWindDirection()));
                elevation.setText(Integer.toString(obs.getElevation()));
                latitude.setText(Double.toString(obs.getLat()));
                longitude.setText(Double.toString(obs.getLng()));

            }
        };

    }

    private void getJSONObservation(String code) {
        Intent intent = new Intent(getApplicationContext(), WeatherObservationService.class);
        intent.putExtra("code", code);
        intent.putExtra("handler", new Messenger(myHandler));
        startService(intent);
    }
}
