package com.shan.tecklaptop.taxcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button slab_info;

    String row_one_lower_limit , row_one_uper_limit, row_one_income, row_one_percent,
            row_two_lower_limit, row_two_uper_limit, row_two_income, row_two_percent,
            row_three_lower_limit, row_three_uper_limit, row_three_income, row_three_percent,
            row_four_lower_limit, row_four_uper_limit, row_four_income, row_four_percent,
            row_five_lower_limit, row_five_uper_limit, row_five_income, row_five_percent,
            row_six_lower_limit, row_six_uper_limit, row_six_income, row_six_percent,
            row_seven_lower_limit, row_seven_uper_limit, row_seven_income, row_seven_percent,
            row_eight_lower_limit, row_eight_uper_limit, row_eight_income, row_eight_percent,
            row_nine_lower_limit, row_nine_uper_limit, row_nine_income, row_nine_percent,
            row_ten_lower_limit, row_ten_uper_limit, row_ten_income, row_ten_percent,
            row_elev_lower_limit, row_elev_uper_limit, row_elev_income, row_elev_percent,
            row_twe_lower_limit, row_twe_uper_limit, row_twe_income, row_twe_percent;

    EditText input_gross, input_house_rent, input_elec_unit, input_consume_unit, input_other_income, input_donation,
            input_zakat, rebate_input, tax_deduct_input, other_income_deducted_input;

    private static int SPLASH_TIME_OUT = 5000;
    private AdView mAdView;
    InterstitialAd  mInterstitialAd;
    Spinner spinner , spinnerto;
    String[]  yearsArray = new String[61] ;
    String[]  yearsArray2 = new String[61] ;
    int counter = 0 , counter2 = 0;
    ArrayAdapter<String> adapter , adapter2;
    String  year;
    String  year_to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_calulator);
        RegisterViews();
        setListeners();
        clearCache();

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener( new AdListener(){
                @Override
                public void onAdClosed(){
                    finish();
        }}
        );

        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);
        refresh();

        try {

            for (int i = 1990; i <= 2050; i++) {
                if(counter == 0){
                    yearsArray[counter] = "select";
                }else {
                yearsArray[counter] = String.valueOf(i);
                }
                ++counter;

            }
        }catch (Exception e){
            e.getMessage();
        }

        try {

            for (int i = 1990; i <= 2050; i++) {
                if(counter2 == 0){
                    yearsArray2[counter2] = "select";
                }else {
                    yearsArray2[counter2] = String.valueOf(i);
                }
                ++counter2;

            }
        }catch (Exception e){
            e.getMessage();
        }

        try {

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, yearsArray);
            spinner.setAdapter(adapter);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    year =  String.valueOf(parent.getItemAtPosition(position));
                //    Toast.makeText(MainActivity.this, year , Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getSharedPreferences("YEAR",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("year",year);
                    editor.commit();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, yearsArray2);
            spinnerto.setAdapter(adapter2);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    year_to =  String.valueOf(parent.getItemAtPosition(position));
                    //    Toast.makeText(MainActivity.this, year , Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getSharedPreferences("YEAR_TO",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("year_to",year_to);
                    editor.commit();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }catch (Exception e){
            e.getMessage();
        }

    }

    public void showInterstitialAdd(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
            finish();
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        try {

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }

        }catch (Exception e){
            e.getMessage();
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public void onBackPressed() {
     //   super.onBackPressed();
      /*  if(haveNetworkConnection()) {
            showInterstitialAdd();
        }else{
            finish();
        }*/
      super.onBackPressed();
    }

    private void refresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                getAllData();


            }
        }, SPLASH_TIME_OUT);

    }



    private void RegisterViews() {
        slab_info = (Button) findViewById(R.id.slab_info);
        input_gross = (EditText) findViewById(R.id.input_gross);
        input_house_rent = (EditText) findViewById(R.id.input_house_rent);
        input_elec_unit = (EditText) findViewById(R.id.input_elec_unit);
        input_consume_unit = (EditText) findViewById(R.id.input_consume_unit);
        input_other_income = (EditText) findViewById(R.id.input_other_income);
        input_donation = (EditText) findViewById(R.id.input_donation);
        input_zakat = (EditText) findViewById(R.id.input_zakat);
        rebate_input = (EditText) findViewById(R.id.rebate_label_input);
        tax_deduct_input = (EditText) findViewById(R.id.tax_deduct_input);
        other_income_deducted_input = (EditText) findViewById(R.id.other_income_label_input);
        spinner = (Spinner) findViewById(R.id.yearSpinner);
        spinnerto = (Spinner) findViewById(R.id.yearSpinnerto);

    }

    private void setListeners() {
        slab_info.setOnClickListener(this);
    }

    private void getAllData() {

        try {

            SharedPreferences preferences = getSharedPreferences("GET_ALL_DATA", Context.MODE_PRIVATE);


            String row_one = preferences.getString("row_one", "");
            String[] row_one_array = row_one.split(Pattern.quote(","));
            row_one_lower_limit = row_one_array[0];
            row_one_uper_limit = row_one_array[1];
            row_one_income = row_one_array[2];
            row_one_percent = row_one_array[3];


            String row_two = preferences.getString("row_two", "");
            String[] row_two_array = row_two.split(Pattern.quote(","));
            row_two_lower_limit = row_two_array[0];
            row_two_uper_limit = row_two_array[1];
            row_two_income = row_two_array[2];
            row_two_percent = row_two_array[3];

            String row_three = preferences.getString("row_three", "");
            String[] row_three_array = row_three.split(Pattern.quote(","));
            row_three_lower_limit = row_three_array[0];
            row_three_uper_limit = row_three_array[1];
            row_three_income = row_three_array[2];
            row_three_percent = row_three_array[3];

            String row_four = preferences.getString("row_four", "");
            String[] row_four_array = row_four.split(Pattern.quote(","));
            row_four_lower_limit = row_four_array[0];
            row_four_uper_limit = row_four_array[1];
            row_four_income = row_four_array[2];
            row_four_percent = row_four_array[3];

            String row_five = preferences.getString("row_five", "");
            String[] row_five_array = row_five.split(Pattern.quote(","));
            row_five_lower_limit = row_five_array[0];
            row_five_uper_limit = row_five_array[1];
            row_five_income = row_five_array[2];
            row_five_percent = row_five_array[3];

            String row_six = preferences.getString("row_six", "");
            String[] row_six_array = row_six.split(Pattern.quote(","));
            row_six_lower_limit = row_six_array[0];
            row_six_uper_limit = row_six_array[1];
            row_six_income = row_six_array[2];
            row_six_percent = row_six_array[3];


            String row_seven = preferences.getString("row_seven", "");
            String[] row_seven_array = row_seven.split(Pattern.quote(","));
            row_seven_lower_limit = row_seven_array[0];
            row_seven_uper_limit = row_seven_array[1];
            row_seven_income = row_seven_array[2];
            row_seven_percent = row_seven_array[3];


            String row_eight = preferences.getString("row_eight", "");
            String[] row_eight_array = row_eight.split(Pattern.quote(","));
            row_eight_lower_limit = row_eight_array[0];
            row_eight_uper_limit = row_eight_array[1];
            row_eight_income = row_eight_array[2];
            row_eight_percent = row_eight_array[3];


            String row_nine = preferences.getString("row_nine", "");
            String[] row_nine_array = row_nine.split(Pattern.quote(","));
            row_nine_lower_limit = row_nine_array[0];
            row_nine_uper_limit = row_nine_array[1];
            row_nine_income = row_nine_array[2];
            row_nine_percent = row_nine_array[3];


            String row_ten = preferences.getString("row_ten", "");
            String[] row_ten_array = row_ten.split(Pattern.quote(","));
            row_ten_lower_limit = row_ten_array[0];
            row_ten_uper_limit = row_ten_array[1];
            row_ten_income = row_ten_array[2];
            row_ten_percent = row_ten_array[3];


            String row_elev = preferences.getString("row_elev", "");
            String[] row_elev_array = row_elev.split(Pattern.quote(","));
            row_elev_lower_limit = row_elev_array[0];
            row_elev_uper_limit = row_elev_array[1];
            row_elev_income = row_elev_array[2];
            row_elev_percent = row_elev_array[3];

            String row_twe = preferences.getString("row_twe", "");
            String[] row_twe_array = row_twe.split(Pattern.quote(","));
            row_twe_lower_limit = row_twe_array[0];
            row_twe_uper_limit = row_twe_array[1];
            row_twe_income = row_twe_array[2];
            row_twe_percent = row_twe_array[3];

        }catch (Exception e){
            row_one_lower_limit = "0";
            row_one_uper_limit = "400000";
            row_one_income = "0";
            row_one_percent = "0";

            row_two_lower_limit = "400000";
            row_two_uper_limit = "500000";
            row_two_income = "0";
            row_two_percent = "2";

            row_three_lower_limit = "500000";
            row_three_uper_limit = "750000";
            row_three_income = "2000";
            row_three_percent = "5";

            row_four_lower_limit = "750000";
            row_four_uper_limit = "1400000";
            row_four_income = "14500";
            row_four_percent = "10";

            row_five_lower_limit = "1400000";
            row_five_uper_limit = "1500000";
            row_five_income = "79500";
            row_five_percent = "12.5";


            row_six_lower_limit = "1500000";
            row_six_uper_limit = "1800000";
            row_six_income = "92000";
            row_six_percent = "15";


            row_seven_lower_limit = "1800000";
            row_seven_uper_limit = "2500000";
            row_seven_income = "137000";
            row_seven_percent = "17.5";



            row_eight_lower_limit = "2500000";
            row_eight_uper_limit = "3000000";
            row_eight_income = "259500";
            row_eight_percent = "20";



            row_nine_lower_limit = "3000000";
            row_nine_uper_limit = "3500000";
            row_nine_income = "359500";
            row_nine_percent = "22.5";


            row_ten_lower_limit = "3500000";
            row_ten_uper_limit = "4000000";
            row_ten_income = "472000";
            row_ten_percent = "25";



            row_elev_lower_limit = "4000000";
            row_elev_uper_limit = "7000000";
            row_elev_income = "597000";
            row_elev_percent = "27.5";


            row_twe_lower_limit = "7000000";
            row_twe_uper_limit = "0";
            row_twe_income = "1422000";
            row_twe_percent = "30";

        }


    }

    private void calculation(){
        try {
            Double gross_salary = 0.0, house_rent_allownce = 0.0, elect_unit = 0.0, consume_unit = 0.0, other_income = 0.0,
                    donation = 0.0, zakat = 0.0, rebate = 0.0, tax_deducted = 0.0, other_income_deducted = 0.0;
            if (!(input_gross.getText().toString().equals(""))) {
                gross_salary = Double.valueOf(input_gross.getText().toString());
            }
            if (!(input_house_rent.getText().toString().equals(""))) {
                house_rent_allownce = Double.valueOf(input_house_rent.getText().toString());
            }
            if (!(input_elec_unit.getText().toString().equals(""))) {
                elect_unit = Double.valueOf(input_elec_unit.getText().toString());
            }
            if (!(input_consume_unit.getText().toString().equals(""))) {
                consume_unit = Double.valueOf(input_consume_unit.getText().toString());
            }
            if (!(input_other_income.getText().toString().equals(""))) {
                other_income = Double.valueOf(input_other_income.getText().toString());
            }
            if (!(input_donation.getText().toString().equals(""))) {
                donation = Double.valueOf(input_donation.getText().toString());
            }
            if (!(input_zakat.getText().toString().equals(""))) {
                zakat = Double.valueOf(input_zakat.getText().toString());
            }
            if (!(rebate_input.getText().toString().equals(""))) {
                rebate = Double.valueOf(rebate_input.getText().toString());
            }
            if (!(tax_deduct_input.getText().toString().equals(""))) {
                tax_deducted = Double.valueOf(tax_deduct_input.getText().toString());
            }
            if (!(other_income_deducted_input.getText().toString().equals(""))) {
                other_income_deducted = Double.valueOf(other_income_deducted_input.getText().toString());
            }

            Double total_income = gross_salary + house_rent_allownce + elect_unit - consume_unit + other_income;
       //     Double total_income = gross_salary + house_rent_allownce + elect_unit + consume_unit + other_income;
            Double income_after_deduction = total_income - donation - zakat;
            Double apply_slab = 0.0;
            Double add_percentage = 0.0;
            Double myIncomeTax = 0.0;
            String lower_limit = "";
            String set_percentage = "";


            if (income_after_deduction > Double.valueOf(row_one_lower_limit) && income_after_deduction <= Double.valueOf(row_one_uper_limit)) {

                Double result  = income_after_deduction - Double.valueOf(row_one_lower_limit);



                add_percentage = (result * Double.valueOf(row_one_percent)) / 100;
                apply_slab = add_percentage + Double.valueOf(row_one_income);

                myIncomeTax = Double.valueOf(row_one_income);
                lower_limit = row_one_lower_limit;
                set_percentage = row_one_percent;

                //       apply_slab = ((income_after_deduction + Double.valueOf(row_one_income)) *  Double.valueOf(row_one_percent)) / 100;

            } else if (income_after_deduction > Double.valueOf(row_two_lower_limit) && income_after_deduction <= Double.valueOf(row_two_uper_limit)) {

                Double result  = income_after_deduction - Double.valueOf(row_two_lower_limit);

                add_percentage = (result * Double.valueOf(row_two_percent)) / 100;
                apply_slab = add_percentage + Double.valueOf(row_two_income);

                myIncomeTax = Double.valueOf(row_two_income);
                lower_limit = row_two_lower_limit;
                set_percentage = row_two_percent;

                // apply_slab = ((income_after_deduction + Double.valueOf(row_two_income)) * Double.valueOf(row_two_percent)) / 100 ;

            } else if (income_after_deduction > Double.valueOf(row_three_lower_limit) && income_after_deduction <= Double.valueOf(row_three_uper_limit)) {

                Double result  = income_after_deduction - Double.valueOf(row_three_lower_limit);

                add_percentage = (result * Double.valueOf(row_three_percent)) / 100;
                apply_slab = add_percentage + Double.valueOf(row_three_income);

                myIncomeTax = Double.valueOf(row_three_income);
                lower_limit = row_three_lower_limit;
                set_percentage = row_three_percent;
                //      apply_slab = ((income_after_deduction + Double.valueOf(row_three_income)) * Double.valueOf(row_three_percent)) / 100 ;

            } else if (income_after_deduction > Double.valueOf(row_four_lower_limit) && income_after_deduction <= Double.valueOf(row_four_uper_limit)) {

                Double result  = income_after_deduction - Double.valueOf(row_four_lower_limit);

                add_percentage = (result * Double.valueOf(row_four_percent)) / 100;
                apply_slab = add_percentage + Double.valueOf(row_four_income);

                myIncomeTax = Double.valueOf(row_four_income);
                lower_limit = row_four_lower_limit;
                set_percentage = row_four_percent;
                //     apply_slab = ((income_after_deduction + Double.valueOf(row_four_income)) *  Double.valueOf(row_four_percent)) / 100;

            } else if (income_after_deduction > Double.valueOf(row_five_lower_limit) && income_after_deduction <= Double.valueOf(row_five_uper_limit)) {

                Double result  = income_after_deduction - Double.valueOf(row_five_lower_limit);

                add_percentage = (result * Double.valueOf(row_five_percent)) / 100;
                apply_slab = add_percentage + Double.valueOf(row_five_income);

                myIncomeTax = Double.valueOf(row_five_income);
                lower_limit = row_five_lower_limit;
                set_percentage = row_five_percent;

                //     apply_slab = ((income_after_deduction + Double.valueOf(row_five_income)) * Double.valueOf(row_five_percent)) / 100 ;

            } else if (income_after_deduction > Double.valueOf(row_six_lower_limit) && income_after_deduction <= Double.valueOf(row_six_uper_limit)) {

                Double result  = income_after_deduction - Double.valueOf(row_six_lower_limit);
                add_percentage = (result * Double.valueOf(row_six_percent)) / 100;
                apply_slab = add_percentage + Double.valueOf(row_six_income);

                myIncomeTax = Double.valueOf(row_six_income);
                lower_limit = row_six_lower_limit;
                set_percentage = row_six_percent;
                //      apply_slab = ((income_after_deduction + Double.valueOf(row_six_income)) * Double.valueOf(row_six_percent)) / 100 ;

            } else if (income_after_deduction > Double.valueOf(row_seven_lower_limit) && income_after_deduction <= Double.valueOf(row_seven_uper_limit)) {

                Double result  = income_after_deduction - Double.valueOf(row_seven_lower_limit);
                add_percentage = (result * Double.valueOf(row_seven_percent)) / 100;
                apply_slab = add_percentage + Double.valueOf(row_seven_income);

                myIncomeTax = Double.valueOf(row_seven_income);
                lower_limit = row_seven_lower_limit;
                set_percentage = row_seven_percent;

                //      apply_slab = ((income_after_deduction + Double.valueOf(row_seven_income)) * Double.valueOf(row_seven_percent)) / 100 ;

            } else if (income_after_deduction > Double.valueOf(row_eight_lower_limit) && income_after_deduction <= Double.valueOf(row_eight_uper_limit)) {

                Double result  = income_after_deduction - Double.valueOf(row_eight_lower_limit);

                add_percentage = (result * Double.valueOf(row_eight_percent)) / 100;
                apply_slab = add_percentage + Double.valueOf(row_eight_income);

                myIncomeTax = Double.valueOf(row_eight_income);
                lower_limit = row_eight_lower_limit;
                set_percentage = row_eight_percent;
                //   apply_slab = ((income_after_deduction + Double.valueOf(row_eight_income)) *  Double.valueOf(row_eight_percent)) / 100 ;

            } else if (income_after_deduction > Double.valueOf(row_nine_lower_limit) && income_after_deduction <= Double.valueOf(row_nine_uper_limit)) {

                Double result  = income_after_deduction - Double.valueOf(row_nine_lower_limit);

                add_percentage = (result * Double.valueOf(row_nine_percent)) / 100;
                apply_slab = add_percentage + Double.valueOf(row_nine_income);

                myIncomeTax = Double.valueOf(row_nine_income);
                lower_limit = row_nine_lower_limit;
                set_percentage = row_nine_percent;
                //        apply_slab = ((income_after_deduction + Double.valueOf(row_nine_income)) * Double.valueOf(row_nine_percent)) / 100 ;

            } else if (income_after_deduction > Double.valueOf(row_ten_lower_limit) && income_after_deduction <= Double.valueOf(row_ten_uper_limit)) {

                Double result  = income_after_deduction - Double.valueOf(row_ten_lower_limit);

                add_percentage = (result * Double.valueOf(row_ten_percent)) / 100;
                apply_slab = add_percentage + Double.valueOf(row_ten_income);


                myIncomeTax = Double.valueOf(row_ten_income);
                lower_limit = row_ten_lower_limit;
                set_percentage = row_ten_percent;
                //      apply_slab = ((income_after_deduction + Double.valueOf(row_ten_income)) * Double.valueOf(row_ten_percent)) / 100 ;

            } else if (income_after_deduction > Double.valueOf(row_elev_lower_limit) && income_after_deduction <= Double.valueOf(row_elev_uper_limit)) {

                Double result  = income_after_deduction - Double.valueOf(row_elev_lower_limit);

                add_percentage = (result * Double.valueOf(row_elev_percent)) / 100;
                apply_slab = add_percentage + Double.valueOf(row_elev_income);

                myIncomeTax = Double.valueOf(row_elev_income);
                lower_limit = row_elev_lower_limit;
                set_percentage = row_elev_percent;

                //      apply_slab = ((income_after_deduction + Double.valueOf(row_elev_income)) * Double.valueOf(row_elev_percent)) / 100 ;

            } else if (income_after_deduction > Double.valueOf(row_twe_lower_limit)) {

                Double result  = income_after_deduction - Double.valueOf(row_twe_lower_limit);

                add_percentage = (result * Double.valueOf(row_twe_percent)) / 100;
                apply_slab = add_percentage + Double.valueOf(row_twe_income);

                myIncomeTax = Double.valueOf(row_twe_income);
                lower_limit = row_twe_lower_limit;
                set_percentage = row_twe_percent;
                //       apply_slab = ((income_after_deduction + Double.valueOf(row_twe_income)) * Double.valueOf(row_twe_percent)) / 100 ;

            }

            Double finalResult = income_after_deduction;
      //      Double result = other_income_deducted - Double.valueOf(lower_limit);

      //      Double my_finalResult = finalResult - rebate - tax_deducted - other_income_deducted;

            SharedPreferences preferences = getSharedPreferences("RESULT", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("final_result", String.valueOf(finalResult));
            editor.putString("add_percent", String.valueOf(add_percentage));
            editor.putString("income_tax", String.valueOf(myIncomeTax));
            editor.putString("rebate", rebate_input.getText().toString());
            editor.putString("tax_deducted", tax_deduct_input.getText().toString());
            editor.putString("other_income_deducted", other_income_deducted_input.getText().toString());
            editor.putString("lower_limit", lower_limit);
            editor.putString("set_percentage", set_percentage);
            editor.commit();

            SharedPreferences finalpref = getSharedPreferences("FINAL_CONCLUSION", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = finalpref.edit();
            editor2.putString("gross_salary",input_gross.getText().toString());
            editor2.putString("house_rent",input_house_rent.getText().toString());
            editor2.putString("elec_unit",input_elec_unit.getText().toString());
            editor2.putString("uncusumed_unit",input_consume_unit.getText().toString());
            editor2.putString("other_income",input_other_income.getText().toString());
            editor2.putString("donation",input_donation.getText().toString());
            editor2.putString("zakat",input_zakat.getText().toString());
            editor2.putString("final_result", String.valueOf(finalResult));
            editor2.putString("add_percent", String.valueOf(add_percentage));
            editor2.putString("income_tax", String.valueOf(myIncomeTax));
            editor2.putString("rebate", rebate_input.getText().toString());
            editor2.putString("tax_deducted", tax_deduct_input.getText().toString());
            editor2.putString("other_income_deducted", other_income_deducted_input.getText().toString());
            editor2.putString("lower_limit", lower_limit);
            editor2.putString("set_percentage", set_percentage);
            editor2.commit();

        }catch (Exception e){
            e.getMessage();
        }
    }

    private void clearCache(){
        SharedPreferences preferences = getSharedPreferences("RESULT", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("final_result", "");
        editor.putString("add_percent","");
        editor.putString("income_tax","");
        editor.putString("rebate","");
        editor.putString("tax_deducted","");
        editor.putString("other_income_deducted","");
        editor.putString("lower_limit", "");
        editor.putString("set_percentage", "");
        editor.commit();



        SharedPreferences finalpref = getSharedPreferences("FINAL_CONCLUSION", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = finalpref.edit();
        editor2.putString("gross_salary","");
        editor2.putString("house_rent","");
        editor2.putString("elec_unit","");
        editor2.putString("uncusumed_unit","");
        editor2.putString("other_income","");
        editor2.putString("donation","");
        editor2.putString("zakat","");
        editor2.putString("final_result", "");
        editor2.putString("add_percent","");
        editor2.putString("income_tax","");
        editor2.putString("rebate","");
        editor2.putString("tax_deducted","");
        editor2.putString("other_income_deducted", "");
        editor2.putString("lower_limit", "");
        editor2.putString("set_percentage", "");
        editor2.putString("total_tax","");
        editor2.putString("net_tax_pay","");
        editor2.putString("balance","");
        editor2.commit();
    }

    public void calculate(View v) {

        if(year.equals("select") || year_to.equals("select")){

            Toast.makeText(MainActivity.this,"Please select the Years",Toast.LENGTH_SHORT).show();

        }else {

            calculation();

            startActivity(new Intent(MainActivity.this, ResultActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.slab_info:
                startActivity(new Intent(MainActivity.this, SlabEditorActivity.class));
                break;
        }
    }
}
