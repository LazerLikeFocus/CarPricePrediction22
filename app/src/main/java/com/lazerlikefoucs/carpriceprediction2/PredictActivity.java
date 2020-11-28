package com.lazerlikefoucs.carpriceprediction2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.Arrays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PredictActivity extends AppCompatActivity implements View.OnClickListener{


    TextView fuel, transmission, owners, price, seller,  seats;
    EditText years, driveKMS, mileage, engine;
    public int fuel_item, tranmission_item, owner_item, year_item, drive_item, mileage_item, engine_item,seller_item, seat_item;
    ImageButton predict;
    public String[] items;
    private String stringToPython;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);

        fuel = findViewById(R.id.fueltype);
        transmission = findViewById(R.id.geartrans);
        owners = findViewById(R.id.ownersbefore);
        years = findViewById(R.id.yearsold);
        driveKMS = findViewById(R.id.kmdriven);
        mileage = findViewById(R.id.mileageT);
        engine = findViewById(R.id.engineT);
        predict = findViewById(R.id.imageButton);
        price = findViewById(R.id.textView_predict);
        seats = findViewById(R.id.seatstotal);
        seller = findViewById(R.id.sellertype);

        fuel.setOnClickListener(this);
        transmission.setOnClickListener(this);
        owners.setOnClickListener(this);
        predict.setOnClickListener(this);
        seats.setOnClickListener(this);
        seller.setOnClickListener(this);


        if (!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        //
    }

    @Override
    public void onClick(View view) {
        if (view==fuel){showNotableDialog(1);}
        if (view==transmission){showNotableDialog(2);}
        if (view==owners){showNotableDialog(3);}
        if (view==seats){showNotableDialog(4);}
        if (view==seller){showNotableDialog(5);}

        if (view==predict){

            //stringToPython = newstext.getText().toString();

            pythonInitialize();}
    }


    //python
    private void pythonInitialize() {

        int[][] label = new int[1][9];

/*        //test
        label[0][0] = 90000;
        label[0][1] = 1;
        label[0][2] = 1;
        label[0][3] = 1;
        label[0][4] = 1;
        label[0][5] = 19;
        label[0][6] = 1248;
        label[0][7] = 5;
        label[0][8] = 9;
        */

        label[0][0] = Integer.parseInt(driveKMS.getText().toString());
        label[0][1] = fuel_item;
        label[0][2] = seat_item;
        label[0][3] = tranmission_item;
        label[0][4] = owner_item;
        label[0][5] = Integer.parseInt(mileage.getText().toString());
        label[0][6] = Integer.parseInt(engine.getText().toString());
        label[0][7] = seat_item;
        label[0][8] = Integer.parseInt(years.getText().toString());

        System.out.print("Label : " + Arrays.deepToString(label));

        Python py = Python.getInstance();
        final PyObject pyObj = py.getModule("clean_string");
        PyObject obj = pyObj.callAttr("find", label);
        stringToPython = obj.toString();

        price.setText(stringToPython);


        //testing
        //Log.d("the probability is: ",stringToPython);
        //textView_AI.setText(stringToPython);
    }



    private void showNotableDialog(int i) {
        AlertDialog.Builder mBuilder =  new AlertDialog.Builder(com.lazerlikefoucs.carpriceprediction.PredictActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.notable_dialog_spinner, null);
        mBuilder.setTitle("");
        final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);

        if (i==1){ items = new String[]{"Petrol", "Diesel", "CNG"};}
        if (i==2){ items = new String[]{"Manual", "Auto"};}
        if (i==3){ items = new String[]{"None: New CAR", "1", "2", "3", "4"};}
        if (i==4){ items = new String[]{"2", "4", "6", "8"};}
        if (i==5){ items = new String[]{"Dealer", "Individual"};}


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(com.lazerlikefoucs.carpriceprediction.PredictActivity.this,
                android.R.layout.simple_spinner_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //String notable_item = mSpinner.getSelectedItem().toString();

                if (i==1) fuel_item = mSpinner.getSelectedItemPosition();
                if (i==2) tranmission_item = mSpinner.getSelectedItemPosition();
                if (i==3) owner_item = mSpinner.getSelectedItemPosition();
                if (i==4) seat_item = mSpinner.getSelectedItemPosition();
                if (i==5) seller_item = mSpinner.getSelectedItemPosition();

                dialogInterface.dismiss();
            }
        });
        mBuilder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}