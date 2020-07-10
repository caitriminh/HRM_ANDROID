package com.example.myapplication.ui.ScanQR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class ScanQR_Activity extends AppCompatActivity implements BarcodeRetriever {

    String resultScan="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scan_qr_activity);
        BarcodeCapture barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);
        barcodeCapture.setRetrieval(this);

        barcodeCapture.setShowDrawRect(true)
                .setSupportMultipleScan(false)
                .setTouchAsCallback(false)
                .shouldAutoFocus(true)
                .setShowFlash(false)
                .setBarcodeFormat(Barcode.QR_CODE)
                .setCameraFacing( CameraSource.CAMERA_FACING_BACK)
                .setShouldShowText(true);
        barcodeCapture.refresh();

        //Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRetrieved(Barcode barcode) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(getApplicationContext(), barcode.displayValue, Toast.LENGTH_LONG).show();
//            }
//        });
        resultScan= barcode.displayValue;
        Intent returnIntent = getIntent();
        returnIntent.putExtra("result", resultScan);
        setResult(101, returnIntent);
//        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
//        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
        PlayBeep(true);
        finish();

    }



    public void PlayBeep(boolean hasVIBRATOR) {
        try {
            String uri = "android.resource://" + getPackageName() + "/" + R.raw.beep;
            Uri notification = Uri.parse(uri);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
                    notification);
            r.play();
            if (hasVIBRATOR) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
            }
        } catch (Exception e) {
            Log.w("beep error", e.getMessage() + "");
        }
    }

    @Override
    public void onRetrievedMultiple(Barcode closetToClick, List<BarcodeGraphic> barcode) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onRetrievedFailed(String reason) {

    }

    @Override
    public void onPermissionRequestDenied() {

    }
}