package com.shan.tecklaptop.taxcalculator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaCodec;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.regex.Pattern;

public class SlabEditorActivity extends AppCompatActivity implements View.OnClickListener {

    EditText row_one_lower_limit, row_one_uper_limit, row_one_income, row_one_percent,
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

    ImageView row_one_edit, row_two_edit, row_three_edit, row_four_edit, row_five_edit, row_six_edit,
            row_seven_edit, row_eight_edit, row_nine_edit, row_ten_edit, row_elev_edit, row_twe_edit;

    Button saveButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_table_view);

        LocalBroadcastManager.getInstance(this).registerReceiver( receiver , new IntentFilter("com.shan.tecklaptop.taxcalculator"));

        RegisterViews();
        setListeners();
        disableAllFields();

        SharedPreferences pref = getSharedPreferences("APP_RUN_FIRST_TIME",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putBoolean("run",true);
        editor.commit();


    }


    private void RegisterViews() {
        row_one_lower_limit = (EditText) findViewById(R.id.row_one_lower_limit);
        row_one_uper_limit = (EditText) findViewById(R.id.row_one_uper_limit);
        row_one_income = (EditText) findViewById(R.id.row_one_income);
        row_one_percent = (EditText) findViewById(R.id.row_one_percent);
        row_two_lower_limit = (EditText) findViewById(R.id.row_two_lower_limit);
        row_two_uper_limit = (EditText) findViewById(R.id.row_two_uper_limit);
        row_two_income = (EditText) findViewById(R.id.row_two_income);
        row_two_percent = (EditText) findViewById(R.id.row_two_percent);
        row_three_lower_limit = (EditText) findViewById(R.id.row_three_lower_limit);
        row_three_uper_limit = (EditText) findViewById(R.id.row_three_uper_limit);
        row_three_income = (EditText) findViewById(R.id.row_three_income);
        row_three_percent = (EditText) findViewById(R.id.row_three_percent);
        row_four_lower_limit = (EditText) findViewById(R.id.row_four_lower_limit);
        row_four_uper_limit = (EditText) findViewById(R.id.row_four_uper_limit);
        row_four_income = (EditText) findViewById(R.id.row_four_income);
        row_four_percent = (EditText) findViewById(R.id.row_four_percent);
        row_five_lower_limit = (EditText) findViewById(R.id.row_five_lower_limit);
        row_five_uper_limit = (EditText) findViewById(R.id.row_five_uper_limit);
        row_five_income = (EditText) findViewById(R.id.row_five_income);
        row_five_percent = (EditText) findViewById(R.id.row_five_percent);
        row_six_lower_limit = (EditText) findViewById(R.id.row_six_lower_limit);
        row_six_uper_limit = (EditText) findViewById(R.id.row_six_uper_limit);
        row_six_income = (EditText) findViewById(R.id.row_six_income);
        row_six_percent = (EditText) findViewById(R.id.row_six_percent);
        row_seven_lower_limit = (EditText) findViewById(R.id.row_seven_lower_limit);
        row_seven_uper_limit = (EditText) findViewById(R.id.row_seven_uper_limit);
        row_seven_income = (EditText) findViewById(R.id.row_seven_income);
        row_seven_percent = (EditText) findViewById(R.id.row_seven_percent);
        row_eight_lower_limit = (EditText) findViewById(R.id.row_eight_lower_limit);
        row_eight_uper_limit = (EditText) findViewById(R.id.row_eight_uper_limit);
        row_eight_income = (EditText) findViewById(R.id.row_eight_income);
        row_eight_percent = (EditText) findViewById(R.id.row_eight_percent);
        row_nine_lower_limit = (EditText) findViewById(R.id.row_nine_lower_limit);
        row_nine_uper_limit = (EditText) findViewById(R.id.row_nine_uper_limit);
        row_nine_income = (EditText) findViewById(R.id.row_nine_income);
        row_nine_percent = (EditText) findViewById(R.id.row_nine_percent);
        row_ten_lower_limit = (EditText) findViewById(R.id.row_ten_lower_limit);
        row_ten_uper_limit = (EditText) findViewById(R.id.row_ten_uper_limit);
        row_ten_income = (EditText) findViewById(R.id.row_ten_income);
        row_ten_percent = (EditText) findViewById(R.id.row_ten_percent);
        row_elev_lower_limit = (EditText) findViewById(R.id.row_elev_lower_limit);
        row_elev_uper_limit = (EditText) findViewById(R.id.row_elev_uper_limit);
        row_elev_income = (EditText) findViewById(R.id.row_elev_income);
        row_elev_percent = (EditText) findViewById(R.id.row_elev_percent);
        row_twe_lower_limit = (EditText) findViewById(R.id.row_twe_lower_limit);
        row_twe_uper_limit = (EditText) findViewById(R.id.row_twe_uper_limit);
        row_twe_income = (EditText) findViewById(R.id.row_twe_income);
        row_twe_percent = (EditText) findViewById(R.id.row_twe_percent);


        row_one_edit = (ImageView) findViewById(R.id.row_one_edit);
        row_two_edit = (ImageView) findViewById(R.id.row_two_edit);
        row_three_edit = (ImageView) findViewById(R.id.row_three_edit);
        row_four_edit = (ImageView) findViewById(R.id.row_four_edit);
        row_five_edit = (ImageView) findViewById(R.id.row_five_edit);
        row_six_edit = (ImageView) findViewById(R.id.row_six_edit);
        row_seven_edit = (ImageView) findViewById(R.id.row_seven_edit);
        row_eight_edit = (ImageView) findViewById(R.id.row_eight_edit);
        row_nine_edit = (ImageView) findViewById(R.id.row_nine_edit);
        row_ten_edit = (ImageView) findViewById(R.id.row_ten_edit);
        row_elev_edit = (ImageView) findViewById(R.id.row_elev_edit);
        row_twe_edit = (ImageView) findViewById(R.id.row_twe_edit);

        saveButton = (Button) findViewById(R.id.saveButton);
    }

    private void setListeners() {
        row_one_edit.setOnClickListener(this);
        row_two_edit.setOnClickListener(this);
        row_three_edit.setOnClickListener(this);
        row_four_edit.setOnClickListener(this);
        row_five_edit.setOnClickListener(this);
        row_six_edit.setOnClickListener(this);
        row_seven_edit.setOnClickListener(this);
        row_eight_edit.setOnClickListener(this);
        row_nine_edit.setOnClickListener(this);
        row_ten_edit.setOnClickListener(this);
        row_elev_edit.setOnClickListener(this);
        row_twe_edit.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    private void disableAllFields() {
        row_one_lower_limit.setEnabled(false);
        row_one_uper_limit.setEnabled(false);
        row_one_income.setEnabled(false);
        row_one_percent.setEnabled(false);
        row_two_lower_limit.setEnabled(false);
        row_two_uper_limit.setEnabled(false);
        row_two_income.setEnabled(false);
        row_two_percent.setEnabled(false);
        row_three_lower_limit.setEnabled(false);
        row_three_uper_limit.setEnabled(false);
        row_three_income.setEnabled(false);
        row_three_percent.setEnabled(false);
        row_four_lower_limit.setEnabled(false);
        row_four_uper_limit.setEnabled(false);
        row_four_income.setEnabled(false);
        row_four_percent.setEnabled(false);
        row_five_lower_limit.setEnabled(false);
        row_five_uper_limit.setEnabled(false);
        row_five_income.setEnabled(false);
        row_five_percent.setEnabled(false);
        row_six_lower_limit.setEnabled(false);
        row_six_uper_limit.setEnabled(false);
        row_six_income.setEnabled(false);
        row_six_percent.setEnabled(false);
        row_seven_lower_limit.setEnabled(false);
        row_seven_uper_limit.setEnabled(false);
        row_seven_income.setEnabled(false);
        row_seven_percent.setEnabled(false);
        row_eight_lower_limit.setEnabled(false);
        row_eight_uper_limit.setEnabled(false);
        row_eight_income.setEnabled(false);
        row_eight_percent.setEnabled(false);
        row_nine_lower_limit.setEnabled(false);
        row_nine_uper_limit.setEnabled(false);
        row_nine_income.setEnabled(false);
        row_nine_percent.setEnabled(false);
        row_ten_lower_limit.setEnabled(false);
        row_ten_uper_limit.setEnabled(false);
        row_ten_income.setEnabled(false);
        row_ten_percent.setEnabled(false);
        row_elev_lower_limit.setEnabled(false);
        row_elev_uper_limit.setEnabled(false);
        row_elev_income.setEnabled(false);
        row_elev_percent.setEnabled(false);
        row_twe_lower_limit.setEnabled(false);
        row_twe_uper_limit.setEnabled(false);
        row_twe_income.setEnabled(false);
        row_twe_percent.setEnabled(false);
    }

    public void setAllData(){
        SharedPreferences preferences = getSharedPreferences("GET_ALL_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("row_one", row_one_lower_limit.getText().toString() + "," +row_one_uper_limit.getText().toString() + "," + row_one_income.getText().toString() + "," +row_one_percent.getText().toString());
        editor.putString("row_two", row_two_lower_limit.getText().toString() + "," +row_two_uper_limit.getText().toString() + "," + row_two_income.getText().toString() + "," +row_two_percent.getText().toString());
        editor.putString("row_three", row_three_lower_limit.getText().toString() + "," +row_three_uper_limit.getText().toString() + "," + row_three_income.getText().toString() + "," +row_three_percent.getText().toString());
        editor.putString("row_four", row_four_lower_limit.getText().toString() + "," +row_four_uper_limit.getText().toString() + "," + row_four_income.getText().toString() + "," +row_four_percent.getText().toString());
        editor.putString("row_five", row_five_lower_limit.getText().toString() + "," +row_five_uper_limit.getText().toString() + "," + row_five_income.getText().toString() + "," +row_five_percent.getText().toString());
        editor.putString("row_six", row_six_lower_limit.getText().toString() + "," +row_six_uper_limit.getText().toString() + "," + row_six_income.getText().toString() + "," +row_six_percent.getText().toString());
        editor.putString("row_seven", row_seven_lower_limit.getText().toString() + "," +row_seven_uper_limit.getText().toString() + "," + row_seven_income.getText().toString() + "," +row_seven_percent.getText().toString());
        editor.putString("row_eight", row_eight_lower_limit.getText().toString() + "," +row_eight_uper_limit.getText().toString() + "," + row_eight_income.getText().toString() + "," +row_eight_percent.getText().toString());
        editor.putString("row_nine", row_nine_lower_limit.getText().toString() + "," +row_nine_uper_limit.getText().toString() + "," + row_nine_income.getText().toString() + "," +row_nine_percent.getText().toString());
        editor.putString("row_ten", row_ten_lower_limit.getText().toString() + "," +row_ten_uper_limit.getText().toString() + "," + row_ten_income.getText().toString() + "," +row_ten_percent.getText().toString());
        editor.putString("row_elev", row_elev_lower_limit.getText().toString() + "," +row_elev_uper_limit.getText().toString() + "," + row_elev_income.getText().toString() + "," +row_elev_percent.getText().toString());
        editor.putString("row_twe", row_twe_lower_limit.getText().toString() + "," +row_twe_uper_limit.getText().toString() + "," + row_twe_income.getText().toString() + "," +row_twe_percent.getText().toString());
        editor.commit();

    }

    private void getAllData(){
        SharedPreferences preferences = getSharedPreferences("GET_ALL_DATA", Context.MODE_PRIVATE);


        String row_one = preferences.getString("row_one","");
        String[] row_one_array = row_one.split(Pattern.quote(","));
        row_one_lower_limit.setText(row_one_array[0]);
        row_one_uper_limit.setText(row_one_array[1]);
        row_one_income.setText(row_one_array[2]);
        row_one_percent.setText(row_one_array[3]);


        String row_two = preferences.getString("row_two","");
        String[] row_two_array = row_two.split(Pattern.quote(","));
        row_two_lower_limit.setText(row_two_array[0]);
        row_two_uper_limit.setText(row_two_array[1]);
        row_two_income.setText(row_two_array[2]);
        row_two_percent.setText(row_two_array[3]);

        String row_three = preferences.getString("row_three","");
        String[] row_three_array = row_three.split(Pattern.quote(","));
        row_three_lower_limit.setText(row_three_array[0]);
        row_three_uper_limit.setText(row_three_array[1]);
        row_three_income.setText(row_three_array[2]);
        row_three_percent.setText(row_three_array[3]);

        String row_four = preferences.getString("row_four","");
        String[] row_four_array = row_four.split(Pattern.quote(","));
        row_four_lower_limit.setText(row_four_array[0]);
        row_four_uper_limit.setText(row_four_array[1]);
        row_four_income.setText(row_four_array[2]);
        row_four_percent.setText(row_four_array[3]);

        String row_five = preferences.getString("row_five","");
        String[] row_five_array = row_five.split(Pattern.quote(","));
        row_five_lower_limit.setText(row_five_array[0]);
        row_five_uper_limit.setText(row_five_array[1]);
        row_five_income.setText(row_five_array[2]);
        row_five_percent.setText(row_five_array[3]);

        String row_six = preferences.getString("row_six","");
        String[] row_six_array = row_six.split(Pattern.quote(","));
        row_six_lower_limit.setText(row_six_array[0]);
        row_six_uper_limit.setText(row_six_array[1]);
        row_six_income.setText(row_six_array[2]);
        row_six_percent.setText(row_six_array[3]);


        String row_seven = preferences.getString("row_seven","");
        String[] row_seven_array = row_seven.split(Pattern.quote(","));
        row_seven_lower_limit.setText(row_seven_array[0]);
        row_seven_uper_limit.setText(row_seven_array[1]);
        row_seven_income.setText(row_seven_array[2]);
        row_seven_percent.setText(row_seven_array[3]);


        String row_eight = preferences.getString("row_eight","");
        String[] row_eight_array = row_eight.split(Pattern.quote(","));
        row_eight_lower_limit.setText(row_eight_array[0]);
        row_eight_uper_limit.setText(row_eight_array[1]);
        row_eight_income.setText(row_eight_array[2]);
        row_eight_percent.setText(row_eight_array[3]);


        String row_nine = preferences.getString("row_nine","");
        String[] row_nine_array = row_nine.split(Pattern.quote(","));
        row_nine_lower_limit.setText(row_nine_array[0]);
        row_nine_uper_limit.setText(row_nine_array[1]);
        row_nine_income.setText(row_nine_array[2]);
        row_nine_percent.setText(row_nine_array[3]);


        String row_ten = preferences.getString("row_ten","");
        String[] row_ten_array = row_ten.split(Pattern.quote(","));
        row_ten_lower_limit.setText(row_ten_array[0]);
        row_ten_uper_limit.setText(row_ten_array[1]);
        row_ten_income.setText(row_ten_array[2]);
        row_ten_percent.setText(row_ten_array[3]);


        String row_elev = preferences.getString("row_elev","");
        String[] row_elev_array = row_elev.split(Pattern.quote(","));
        row_elev_lower_limit.setText(row_elev_array[0]);
        row_elev_uper_limit.setText(row_elev_array[1]);
        row_elev_income.setText(row_elev_array[2]);
        row_elev_percent.setText(row_elev_array[3]);

        String row_twe = preferences.getString("row_twe","");
        String[] row_twe_array = row_twe.split(Pattern.quote(","));
        row_twe_lower_limit.setText(row_twe_array[0]);
        row_twe_uper_limit.setText(row_twe_array[1]);
        row_twe_income.setText(row_twe_array[2]);
        row_twe_percent.setText(row_twe_array[3]);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.row_one_edit:
                row_one_lower_limit.setEnabled(true);
                row_one_uper_limit.setEnabled(true);
                row_one_income.setEnabled(true);
                row_one_percent.setEnabled(true);
                break;
            case R.id.row_two_edit:
                row_two_lower_limit.setEnabled(true);
                row_two_uper_limit.setEnabled(true);
                row_two_income.setEnabled(true);
                row_two_percent.setEnabled(true);
                break;
            case R.id.row_three_edit:
                row_three_lower_limit.setEnabled(true);
                row_three_uper_limit.setEnabled(true);
                row_three_income.setEnabled(true);
                row_three_percent.setEnabled(true);
                break;
            case R.id.row_four_edit:
                row_four_lower_limit.setEnabled(true);
                row_four_uper_limit.setEnabled(true);
                row_four_income.setEnabled(true);
                row_four_percent.setEnabled(true);
                break;
            case R.id.row_five_edit:
                row_five_lower_limit.setEnabled(true);
                row_five_uper_limit.setEnabled(true);
                row_five_income.setEnabled(true);
                row_five_percent.setEnabled(true);
                break;
            case R.id.row_six_edit:
                row_six_lower_limit.setEnabled(true);
                row_six_uper_limit.setEnabled(true);
                row_six_income.setEnabled(true);
                row_six_percent.setEnabled(true);
                break;
            case R.id.row_seven_edit:
                row_seven_lower_limit.setEnabled(true);
                row_seven_uper_limit.setEnabled(true);
                row_seven_income.setEnabled(true);
                row_seven_percent.setEnabled(true);
                break;
            case R.id.row_eight_edit:
                row_eight_lower_limit.setEnabled(true);
                row_eight_uper_limit.setEnabled(true);
                row_eight_income.setEnabled(true);
                row_eight_percent.setEnabled(true);
                break;
            case R.id.row_nine_edit:
                row_nine_lower_limit.setEnabled(true);
                row_nine_uper_limit.setEnabled(true);
                row_nine_income.setEnabled(true);
                row_nine_percent.setEnabled(true);
                break;
            case R.id.row_ten_edit:
                row_ten_lower_limit.setEnabled(true);
                row_ten_uper_limit.setEnabled(true);
                row_ten_income.setEnabled(true);
                row_ten_percent.setEnabled(true);
                break;
            case R.id.row_elev_edit:
                row_elev_lower_limit.setEnabled(true);
                row_elev_uper_limit.setEnabled(true);
                row_elev_income.setEnabled(true);
                row_elev_percent.setEnabled(true);
                break;
            case R.id.row_twe_edit:
                row_twe_lower_limit.setEnabled(true);
                row_twe_uper_limit.setEnabled(true);
                row_twe_income.setEnabled(true);
                row_twe_percent.setEnabled(true);
                break;
            case R.id.saveButton:
                disableAllFields();
                setAllData();
                SharedPreferences preferences = getSharedPreferences("DataInserted",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("ValuesInserted",true);
                editor.commit();
                break;
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            SharedPreferences preferences = getSharedPreferences("DataInserted",Context.MODE_PRIVATE);
            boolean check = preferences.getBoolean("ValuesInserted",false);

            if(check) {
                getAllData();
            }else{
                setAllData();
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
