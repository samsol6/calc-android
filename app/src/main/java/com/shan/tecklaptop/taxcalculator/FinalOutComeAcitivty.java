package com.shan.tecklaptop.taxcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FinalOutComeAcitivty extends AppCompatActivity {

    private String pdfname;
    RelativeLayout layout;
    TextView gross_salary, h_rent, elec_unit, unconsume_unit, other_income, donation, zakat, net_tax,
            income_tax, percentage, total_tax, rebate, net_tax_payable, tax_deducted, tax_deducted_otherIncome, balance, set_percent, set_income_tax;
    String S_gross_salary, S_h_rent, S_elec_unit, S_unconsume_unit, S_other_income, S_donation, S_zakat, S_net_tax,
            S_income_tax, S_percentage, S_total_tax, S_rebate, S_net_tax_payable, S_tax_deducted, S_tax_deducted_otherIncome, S_balance, S_set_percent, S_set_income_tax;

    boolean isPdfGenerated;

    TextView date , year , datelimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_out_come_acitivty);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_final_screen);
        RegisterViwes();
        layout = (RelativeLayout) findViewById(R.id.final_conclusion);
        isPdfGenerated = false;

        try {

            SharedPreferences datepreferences = getSharedPreferences("YEAR", Context.MODE_PRIVATE);
            String setYear = datepreferences.getString("year", "");
            int yearInt = 0;
            if(setYear.equals("Select year")) {
                yearInt = 0;
            }else{
                yearInt = Integer.parseInt(setYear);
            }
            int postYear = yearInt + 1;

            year.setText(String.valueOf(yearInt) + "-" + String.valueOf(postYear));
            date.setText("1.06." + String.valueOf(yearInt) + " - " + "30.06." + String.valueOf(postYear));
            datelimit.setText("07-" + String.valueOf(yearInt) + " to " + "06-" + String.valueOf(postYear));

        }catch (Exception e){
            e.getMessage();
        }

        SharedPreferences finalpref = getSharedPreferences("FINAL_CONCLUSION", Context.MODE_PRIVATE);

        S_gross_salary = finalpref.getString("gross_salary", "");
        S_h_rent = finalpref.getString("house_rent", "");
        S_elec_unit = finalpref.getString("elec_unit", "");
        S_unconsume_unit = finalpref.getString("uncusumed_unit", "");
        S_other_income = finalpref.getString("other_income", "");
        S_donation = finalpref.getString("donation", "");
        S_zakat = finalpref.getString("zakat", "");
        S_net_tax = finalpref.getString("final_result", "");
        S_income_tax = finalpref.getString("income_tax", "");
        S_percentage = finalpref.getString("add_percent", "");
        S_total_tax = finalpref.getString("total_tax", "");
        S_rebate = finalpref.getString("rebate", "");
        S_net_tax_payable = finalpref.getString("net_tax_pay", "");
        S_tax_deducted = finalpref.getString("tax_deducted", "");
        S_tax_deducted_otherIncome = finalpref.getString("other_income_deducted", "");
        S_balance = finalpref.getString("balance", "");
        S_set_percent =  finalpref.getString("set_percentage", "");
        S_set_income_tax =  finalpref.getString("lower_limit", "");



        gross_salary.setText(S_gross_salary);
        h_rent.setText(S_h_rent);
        elec_unit.setText(S_elec_unit);
        unconsume_unit.setText(S_unconsume_unit);
        other_income.setText(S_other_income);
        donation.setText(S_donation);
        zakat.setText(S_zakat);
        net_tax.setText(S_net_tax);
        income_tax.setText(S_income_tax);
        percentage.setText(S_percentage);
        total_tax.setText(S_total_tax);
        rebate.setText(S_rebate);
        net_tax_payable.setText(S_net_tax_payable);
        tax_deducted.setText(S_tax_deducted);
        tax_deducted_otherIncome.setText(S_tax_deducted_otherIncome);
        balance.setText(S_balance);
        set_income_tax.setText(S_set_income_tax);
        set_percent.setText(S_set_percent);


    }

    private void RegisterViwes() {
        gross_salary = (TextView) findViewById(R.id.gross_salary);
        h_rent = (TextView) findViewById(R.id.h_rent);
        elec_unit = (TextView) findViewById(R.id.elec_unit);
        unconsume_unit = (TextView) findViewById(R.id.unconsume_unit);
        other_income = (TextView) findViewById(R.id.other_income);
        donation = (TextView) findViewById(R.id.donation);
        zakat = (TextView) findViewById(R.id.zakat);
        net_tax = (TextView) findViewById(R.id.net_tax);
        income_tax = (TextView) findViewById(R.id.income_tax);
        percentage = (TextView) findViewById(R.id.percentage);
        total_tax = (TextView) findViewById(R.id.total_tax);
        rebate = (TextView) findViewById(R.id.rebate);
        net_tax_payable = (TextView) findViewById(R.id.net_tax_payable);
        tax_deducted = (TextView) findViewById(R.id.tax_deducted);
        tax_deducted_otherIncome = (TextView) findViewById(R.id.tax_deducted_otherIncome);
        balance = (TextView) findViewById(R.id.balance);
        set_percent = (TextView) findViewById(R.id.set_percent);
        set_income_tax = (TextView) findViewById(R.id.set_income_tax);
        date = (TextView) findViewById(R.id.date);
        year = (TextView) findViewById(R.id.year);
        datelimit = (TextView) findViewById(R.id.date_limit);
    }

    public void sendEmail(View v){
        if(isPdfGenerated) {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, "receiver_email_address");
            email.putExtra(Intent.EXTRA_SUBJECT, "subject");
            email.putExtra(Intent.EXTRA_TEXT, "email body");
            Uri uri = Uri.fromFile(new File(pdfname));
            email.putExtra(Intent.EXTRA_STREAM, uri);
            email.setType("application/pdf");
            email.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(email);
        }else {
            Toast.makeText(FinalOutComeAcitivty.this,"Please generate the PDF first" ,Toast.LENGTH_SHORT).show();
        }
    }

    public void generatePDF(View v) {
        saveViewImage(layout);
        isPdfGenerated = true;
    }

    public void viewPDF(View v){
        if(isPdfGenerated) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(new File(pdfname));
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }else{
            Toast.makeText(FinalOutComeAcitivty.this,"Please generate the PDF first" ,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
      /*  Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(FILE));
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);*/
        //  convertLayoutToImage();

    super.onBackPressed();


    }

    public void saveViewImage(View view) {
        File file = saveBitMap(this, view);    //which view you want to pass that view as parameter
        if (file != null) {
            Log.i("TAG", "Drawing saved to the gallery!");
        } else {
            Log.i("TAG", "Oops! Image could not be saved.");
        }
    }

    private File saveBitMap(Context context, View drawView) {
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myPDF");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if (!isDirectoryCreated) {
                Log.i("ATG", "Can't create directory to save the image");
                return null;
            }
        }

        String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap = getBitmapFromView(drawView);
        try {
            createPdf(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        //    scanGallery( context,pictureFile.getAbsolutePath());
        return pictureFile;
    }

    //create bitmap from view and returns it
    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    private void createPdf(Bitmap bitmap) throws IOException, DocumentException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image signature;
        signature = Image.getInstance(stream.toByteArray());
        signature.scaleToFit(1000f, 800f);
        signature.setAbsolutePosition(150f, 30f);
     //   signature.setScaleToFitHeight(true);
     //   signature.setScaleToFitLineWhenOverflow(true);
        signature.setWidthPercentage(60);
      //  signature.setSpacingBefore(500);
        //     signature.scalePercent(40f);
        //Image image = Image.getInstance(byteArray);
        File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "pdfdemo");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdirs();
            Log.i("Created", "Pdf Directory created");
        }

        //Create time stamp
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

        pdfname = pdfFolder + timeStamp + ".pdf";

        File myFile = new File(pdfFolder + timeStamp + ".pdf");

        OutputStream output = new FileOutputStream(myFile);
        //Step 1
        Document document = new Document();

        //Step 2
        PdfWriter.getInstance(document, output);

        //Step 3
        document.open();

        //Step 4 Add content
        document.add(signature);
        //document.add(new Paragraph(text.getText().toString()));
        //document.add(new Paragraph(mBodyEditText.getText().toString()));

        //Step 5: Close the document
        document.close();
    }
}
