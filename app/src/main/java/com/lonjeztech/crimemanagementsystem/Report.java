package com.lonjeztech.crimemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Report extends AppCompatActivity {

    Spinner countySpinner,subCountySpinner;
    TextInputEditText textInputEditTextCrimeDescription,textInputEditTextLocation,textInputEditTextStreet;
    Button buttonSubmit;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        countySpinner = findViewById(R.id.spinner_county);
        subCountySpinner =findViewById(R.id.spinner_sub_county);
        textInputEditTextCrimeDescription =findViewById(R.id.crime_description);
        textInputEditTextLocation = findViewById(R.id.specific_location);
        textInputEditTextStreet = findViewById(R.id.street_name);
        buttonSubmit = findViewById(R.id.button_submit);
        progressBar = findViewById(R.id.progress);
        // Create an ArrayAdapter using the string array and
        // a default spinner layout
        ArrayAdapter<CharSequence> county_list
                = ArrayAdapter.createFromResource(
                this, R.array.County,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of
        // choices appears
        county_list.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        countySpinner.setAdapter(county_list);
        countySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if (adapterView.getSelectedItem()
                        .toString()
                        .equals("Mombasa")) {
                    ArrayAdapter<CharSequence> ad_name
                            = ArrayAdapter.createFromResource(
                            getApplicationContext(),
                            R.array.mombasa_subcounty,
                            android.R.layout
                                    .simple_spinner_item);
                    subCountySpinner.setAdapter(ad_name);
                }
                else if (adapterView.getSelectedItem()
                        .toString()
                        .equals("Nakuru")) {
                    ArrayAdapter<CharSequence> ad_name
                            = ArrayAdapter.createFromResource(
                            getApplicationContext(),
                            R.array.nakuru_subcounty,
                            android.R.layout
                                    .simple_spinner_item);
                    subCountySpinner.setAdapter(ad_name);
                }
                else if (adapterView.getSelectedItem()
                        .toString()
                        .equals("Baringo")) {
                    ArrayAdapter<CharSequence> ad_name
                            = ArrayAdapter.createFromResource(
                            getApplicationContext(),
                            R.array.baringo_subcounty,
                            android.R.layout
                                    .simple_spinner_item);
                    subCountySpinner.setAdapter(ad_name);
                }
                else if (adapterView.getSelectedItem()
                        .toString()
                        .equals("Nairobi")){
                    ArrayAdapter<CharSequence> ad_name
                            = ArrayAdapter.createFromResource(
                            getApplicationContext(),
                            R.array.nairobi_subcounty,
                            android.R.layout
                                    .simple_spinner_item);
                    subCountySpinner.setAdapter(ad_name);
                }
                else{
                    ArrayAdapter<CharSequence> ad_name
                            = ArrayAdapter.createFromResource(
                            getApplicationContext(),
                            R.array.kajiado_subcounty,
                            android.R.layout
                                    .simple_spinner_item);
                    subCountySpinner.setAdapter(ad_name);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            });

        subCountySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String county,sub_county;
                county = countySpinner.getSelectedItem().toString();
                sub_county =subCountySpinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "item selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String crime_description,specific_location,county,sub_county,street_name;
                crime_description = String.valueOf(textInputEditTextCrimeDescription.getText());
                specific_location = String.valueOf(textInputEditTextLocation.getText());
                street_name = String.valueOf(textInputEditTextStreet.getText());
                county = countySpinner.getSelectedItem().toString();
                sub_county =subCountySpinner.getSelectedItem().toString();

                if(!crime_description.equals("") && !specific_location.equals("") && !county.isEmpty() && !sub_county.isEmpty() && !street_name.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "crime_description";
                            field[1] = "specific_location";
                            field[2] = "county";
                            field[3] = "sub_county";
                            field[4] = "street_name";

                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = crime_description;
                            data[1] = specific_location;
                            data[2] = county;
                            data[3] = sub_county;
                            data[4] = street_name;
                            PutData putData = new PutData("http://192.168.0.21/loginregister/report.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Report Sent successfully")) {
                                        Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}