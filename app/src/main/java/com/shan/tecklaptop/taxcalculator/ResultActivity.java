package com.shan.tecklaptop.taxcalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import  com.itextpdf.text.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Image;
import com.vipul.hp_hp.library.Layout_to_Image;

public class ResultActivity extends AppCompatActivity {

    TextView net_tax, tax_amount, exceed_amount_percent, total_tax, rebate, net_tax_payable, deducted_tax, other_income,
            balance , set_percent , set_income_tax;
    String add_percent, income_tax, rebate_s, tax_deducted, other_income_deducted;

    static Image image;
    ImageView img;
    Bitmap bmp;
    static Bitmap bt;
    private static String FILE = "/sdcard/FirstPdf.pdf";
  //  private static String FILE = "/sdcard/invoice.pdf";
  //  private static String FILE ="/sdcard/Recordings/GenPdf.pdf";
    RelativeLayout relativeLayout;
    private Bitmap myBitmap;
    Bitmap screen;
    File pdfDir;
    Layout_to_Image layout_to_image;
    final Context CONTEXT = this;
    File pictureFileDir;
    String pdfname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_result);

        RegisterView();

        img = (ImageView) findViewById(R.id.imageView);


        SharedPreferences preferences = getSharedPreferences("RESULT", Context.MODE_PRIVATE);
        String result = preferences.getString("final_result", "");
        add_percent = preferences.getString("add_percent", "");
        income_tax = preferences.getString("income_tax", "");
        rebate_s = preferences.getString("rebate", "");
        tax_deducted = preferences.getString("tax_deducted", "");
        other_income_deducted = preferences.getString("other_income_deducted", "");
        String lower_limit = preferences.getString("lower_limit","");
        String set_percentage = preferences.getString("set_percentage","");

        double percentagee;

        int final_percentage;
        if(add_percent.equals("")){
            percentagee = 0.0;
            final_percentage = (int) percentagee;
        }else {
            percentagee = Double.valueOf(add_percent);
            final_percentage = (int) percentagee;
        }

        double percentage, my_income_tax, my_rebate, tax_deduct, other_income_deduct;

        if(add_percent.equals("")){
            percentage = 0.0;
        }else{
            percentage = Double.valueOf(add_percent);
            final_percentage = (int) percentage;
        }



        if(income_tax.equals("")){
            my_income_tax = 0.0 ;
        }else{
            my_income_tax = Double.valueOf(income_tax);
        }
        if(rebate_s.equals("")){
            my_rebate = 0.0;
        }else{
            my_rebate = Double.valueOf(rebate_s);
        }
        if(tax_deducted.equals("")){
            tax_deduct = 0.0;
        }else{
            tax_deduct = Double.valueOf(tax_deducted);
        }
        if(other_income_deducted.equals("")){
            other_income_deduct = 0.0;
        }else{
            other_income_deduct = Double.valueOf(other_income_deducted);
        }



        String totalTax = String.valueOf(percentage + my_income_tax);
        Double net_tax_pay =  Double.valueOf(totalTax) - my_rebate ;


        Double bal =  net_tax_pay - tax_deduct - other_income_deduct ;
        Double truncatedDouble = BigDecimal.valueOf(bal)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        Double MOD_BALANCE = Math.abs(truncatedDouble);

        net_tax.setText(result);
        tax_amount.setText(income_tax);
        exceed_amount_percent.setText(String.valueOf(final_percentage));
        rebate.setText(rebate_s);
        net_tax_payable.setText(String.valueOf(net_tax_pay));
        deducted_tax.setText(tax_deducted);
        other_income.setText(other_income_deducted);

        SharedPreferences finalpref = getSharedPreferences("FINAL_CONCLUSION", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = finalpref.edit();
        editor2.putString("total_tax",totalTax);
        editor2.putString("net_tax_pay",String.valueOf(net_tax_pay));
        if(truncatedDouble < 0){
           editor2.putString("balance",String.valueOf(MOD_BALANCE));
        }else{
            editor2.putString("balance",String.valueOf(truncatedDouble));
        }
        editor2.commit();


        if(truncatedDouble < 0){
            balance.setText(String.valueOf(MOD_BALANCE));
        }else{
            balance.setText(String.valueOf(truncatedDouble));
        }

        set_percent.setText(set_percentage +"%");
        set_income_tax.setText(lower_limit);
        total_tax.setText(totalTax);
    }

    public void GeneratePDF(View view){


  //      relativeLayout = (RelativeLayout) findViewById(R.id.result_container);

 //       saveViewImage(relativeLayout);
        startActivity( new Intent(CONTEXT , FinalOutComeAcitivty.class));


    }


    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CONTEXT);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }



    public void saveViewImage(View view){
        File file = saveBitMap(this, view);    //which view you want to pass that view as parameter
        if (file != null) {
            Log.i("TAG", "Drawing saved to the gallery!");
        } else {
            Log.i("TAG", "Oops! Image could not be saved.");
        }
    }

    private File saveBitMap(Context context, View drawView){
        pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"myPDF");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated) {
                Log.i("ATG", "Can't create directory to save the image");
                return null;
            }
        }

        String filename = pictureFileDir.getPath() +File.separator+ System.currentTimeMillis()+".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap =getBitmapFromView(drawView);
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
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
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
        signature.setAbsolutePosition(50f, 100f);
        signature.scalePercent(60f);
        //Image image = Image.getInstance(byteArray);
        File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "pdfdemo");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdirs();
            Log.i("Created", "Pdf Directory created");
        }

        //Create time stamp
        Date date = new Date() ;
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



    private void RegisterView() {
        net_tax = (TextView) findViewById(R.id.net_tax);
        tax_amount = (TextView) findViewById(R.id.tax_amount);
        exceed_amount_percent = (TextView) findViewById(R.id.exceed_amount_percent);
        total_tax = (TextView) findViewById(R.id.total_tax);
        rebate = (TextView) findViewById(R.id.rebate);
        net_tax_payable = (TextView) findViewById(R.id.net_tax_payable);
        deducted_tax = (TextView) findViewById(R.id.deducted_tax);
        other_income = (TextView) findViewById(R.id.other_income);
        balance = (TextView) findViewById(R.id.balance);
        set_percent = (TextView) findViewById(R.id.set_percent);
        set_income_tax = (TextView) findViewById(R.id.set_income_tax);
    }
}
