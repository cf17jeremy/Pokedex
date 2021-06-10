package com.example.pokedex;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class fetchType extends AsyncTask<Void, Void, Void> {

    protected String data = "";
    protected String results = "";
    protected static ArrayList<String> pokList; // Create an ArrayList object
    protected String typeSearch;


    public fetchType(String typeSearch) {
        this.typeSearch = typeSearch;
        pokList = new ArrayList<String>();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Make API connection
            URL url = new URL("https://pokeapi.co/api/v2/type/" + typeSearch);
            Log.i("logtest", "https://pokeapi.co/api/v2/type/" + typeSearch);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Read API results
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sBuilder = new StringBuilder();

            // Build JSON String
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            data = sBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        JSONObject jObject = null;

        try {
            jObject = new JSONObject(data);

            //Get pokemons
            //Posicion
            JSONArray pokemon = new JSONArray(jObject.getString("pokemon"));
            //Tipo
            JSONObject pokemon0 = new JSONObject(pokemon.getString(0));
            //Nombre
            JSONObject pokemon02 = new JSONObject(pokemon0.getString("pokemon"));
            //Nombre Tipo
            fetchData fetchData = new fetchData(pokemon02.getString("name"));
            fetchData.execute();

            for(int i=0; i<pokemon.length(); i++){
                JSONObject pokemonItem = new JSONObject(pokemon.getString(i));
                JSONObject pokemonItem2 = new JSONObject(pokemonItem.getString("pokemon"));
                String pokemonToList = pokemonItem2.getString("name");
                Log.i("logtest", pokemonToList);
                pokList.add(pokemonToList);
                MainActivity.pokList = pokList;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}