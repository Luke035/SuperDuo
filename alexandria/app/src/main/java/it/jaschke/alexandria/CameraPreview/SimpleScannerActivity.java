package it.jaschke.alexandria.CameraPreview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import it.jaschke.alexandria.services.Utility;
import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by lucagrazioli on 23/01/16.
 */
public class SimpleScannerActivity extends Activity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;

    static final String SCANNER_TAG = "SCANNER_TAG";
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);// Set the scanner view as the content view

        mScannerView.setAutoFocus(true);
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.ISBN13);
        formats.add(BarcodeFormat.EAN13);
        mScannerView.setFormats(formats);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        Utility.vibrate(getBaseContext(), 50);

        String retrievedISBN = "";
        if(result.getBarcodeFormat().getName().equals("ISBN10")){
            retrievedISBN = "978"+result.getContents();
        }else{
            retrievedISBN = result.getContents();
        }


        // Do something with the result here
        Log.v(SCANNER_TAG, result.getContents()); // Prints scan results
        Log.v(SCANNER_TAG, result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        Log.v(SCANNER_TAG, "Sended ISBN: "+retrievedISBN);
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("ean", retrievedISBN);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }
}
