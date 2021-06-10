package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    public static Activity act;
    public static TextView txtDisplay;
    public static ImageView imgPok;
    public static ImageView imgpokshiny;
    public static int pokId = 0;
    public static boolean ByType = false;
    public int cont = 0;
    public static ArrayList<String> pokList = new ArrayList<String>();


    public static ImageView [] imgType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        act = this;
        imgType = new ImageView[2];

        txtDisplay = findViewById(R.id.txtDisplay);
        imgPok = findViewById(R.id.imgPok);
        imgpokshiny = findViewById(R.id.shiny);
        imgType[0] = findViewById(R.id.imgType0);
        imgType[1] = findViewById(R.id.imgType1);

        showResult("1");

        ImageButton btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTxtSearch();
            }
        });

        ImageButton btnTypes = findViewById(R.id.btnTypes);
        btnTypes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchByType();
            }
        });

        ImageButton btnRight = findViewById(R.id.btnRight);
        btnRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(ByType) {
                    cont++;
                    showResult(pokList.get(cont));
                } else {
                    pokId++;
                    showResult(String.valueOf(pokId));
                }
            }
        });

        ImageButton btnLeft = findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(ByType) {
                    cont--;
                    if(cont<0) {
                        cont = 0;
                    }
                    showResult(pokList.get(cont));
                } else {
                    pokId--;
                    if(pokId<=0) {
                        pokId = 1;
                    }
                    showResult(String.valueOf(pokId));
                }
            }
        });
    }

    public void showTxtSearch(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Search a Pokemon");

        final EditText input = new EditText(this);
        input.setHint("Pokemon");
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String pokSearch = input.getText().toString();
                showResult(pokSearch);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    private void showResult(String pokemon){
        fetchData process = new fetchData(pokemon);
        process.execute();
    }

    private void searchByType(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a type")
                .setItems(R.array.pokTypes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){
                        ListView lw = ((AlertDialog)dialog).getListView();
                        Object checkedItem = lw.getAdapter().getItem(which);
                        String type = String.valueOf(checkedItem).toLowerCase();

                        fetchType process = new fetchType(type);
                        process.execute();

                        ByType = true;
                    }
                });

        builder.show();
    }


}