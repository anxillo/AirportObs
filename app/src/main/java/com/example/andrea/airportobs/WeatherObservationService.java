package com.example.andrea.airportobs;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by andrea on 01.12.15.
 *
 * Intent service per richiamare i dati JSON dal servizio Rest
 */
public class WeatherObservationService extends IntentService {

    private static String JSONURL ="http://api.geonames.org/weatherIcaoJSON?username=supsi&ICAO=";

    public WeatherObservationService() {
        super("WeatherObservationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle b = intent.getExtras();
        String code = b.getString("code");
        Messenger messenger = (Messenger) b.get("handler");

        String myURL = JSONURL + code;
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            URL url = new URL(myURL);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if(responseCode !=200)
                return; // se non va a buon fine, esco

            is = connection.getInputStream(); // ottengo il mio stream

            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8")); //inizializzato il JSON reader

            //leggo l'oggetto
            reader.beginObject();
            while(reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("weatherObservation")) {
                    WeatherObservation observation = new WeatherObservation();
                    reader.beginObject();
                    while(reader.hasNext()) {
                        String objectName = reader.nextName();
                        if(objectName.equals("clouds")) {
                            String clouds = reader.nextString();
                            observation.setClouds(clouds);
                        } else if (objectName.equals("windDirection")) {
                            int windDirection = reader.nextInt();
                            observation.setWindDirection(windDirection);
                        } else if (objectName.equals("elevation")) {
                            int elevation = reader.nextInt();
                            observation.setElevation(elevation);
                        } else if (objectName.equals("lng")) {
                            double lng = reader.nextDouble();
                            observation.setLng(lng);
                        } else if (objectName.equals("lat")) {
                            double lat = reader.nextDouble();
                            observation.setLat(lat);
                        } else {
                            reader.skipValue();
                        }
                    }
                    reader.endObject();

                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("observation", observation);
                    message.setData(bundle);
                    try {
                        messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }
            }
            reader.endObject();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            if(is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }




    }
}
