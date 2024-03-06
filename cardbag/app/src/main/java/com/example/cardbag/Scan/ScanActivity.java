package com.example.cardbag.Scan;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.example.cardbag.R;
import com.example.cardbag.RecordActivity;
import com.example.cardbag.databinding.CustomBarcodeScannerBinding;
import com.example.cardbag.databinding.ScanActivityBinding;
import com.example.cardbag.OldMainActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;

import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;


/**
 * Custom Scannner Activity extending from Activity to display a custom layout form scanner view.
 * <p>
 * Based on https://github.com/journeyapps/zxing-android-embedded/blob/0fdfbce9fb3285e985bad9971c5f7c0a7a334e7b/sample/src/main/java/example/zxing/CustomScannerActivity.java
 * originally licensed under Apache 2.0
 */
public class ScanActivity extends CatimaAppCompatActivity {
    String id,name,time,endTime,note;
    String version="";

    private ScanActivityBinding binding;
    private CustomBarcodeScannerBinding customBarcodeScannerBinding;
    private static final String TAG = "Catima";

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    private String cardId;
    private String addGroup;
    private boolean torch = false;

    private ActivityResultLauncher<Intent> manualAddLauncher;
    // can't use the pre-made contract because that launches the file manager for image type instead of gallery
    private ActivityResultLauncher<Intent> photoPickerLauncher;

    private void extractIntentFields(Intent intent) {
        final Bundle b = intent.getExtras();
//        cardId = b != null ? b.getString(LoyaltyCardEditActivity.BUNDLE_CARDID) : null;
//        addGroup = b != null ? b.getString(LoyaltyCardEditActivity.BUNDLE_ADDGROUP) : null;
//        Log.d(TAG, "Scan activity: id=" + cardId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ScanActivityBinding.inflate(getLayoutInflater());
        customBarcodeScannerBinding = CustomBarcodeScannerBinding.bind(binding.zxingBarcodeScanner);
        setTitle(R.string.scanCardBarcode);
        setContentView(binding.getRoot());
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


//        get id
        Intent intentTransform = getIntent();
        id = intentTransform.getStringExtra("thisid");
        name = intentTransform.getStringExtra("thename");
        time = intentTransform.getStringExtra("thetime");
        endTime = intentTransform.getStringExtra("theendtime");
        note = intentTransform.getStringExtra("thenote");

//        get from old
        Intent intentTransformOld = getIntent();
        if (intentTransformOld.getStringExtra("version")!=null){
            version=intentTransformOld.getStringExtra("version");
        }

        System.out.println("                                            读到了吗"+version);
        if (version =="old" ){
            id = intentTransformOld.getStringExtra("thisid");
            name = intentTransformOld.getStringExtra("thename");
            time = "";
            endTime = "";
            note = "";
        }






//        toolbar.
        extractIntentFields(getIntent());

//        manualAddLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> handleActivityResult(Utils.SELECT_BARCODE_REQUEST, result.getResultCode(), result.getData()));

        photoPickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->
                {
                    Intent intent = result.getData();
                    BarcodeValues barcodeValues = Utils.parseSetBarcodeActivityResult(Utils.BARCODE_IMPORT_FROM_IMAGE_FILE, result.getResultCode(), intent, this);
//在这里打印
                    if (!barcodeValues.isEmpty()) {
                        Intent newIntent ;
                        Intent newIntent2;

                        System.out.println("scanscanscanscan"+version);

                        if(version.equals( "old")){
                            System.out.println("''''''''''''''''''''''''scan用old传");
                             newIntent2 = new Intent(getApplicationContext(), OldMainActivity.class);
//                        Bundle newBundle = new Bundle();
                            newIntent2.putExtra(RecordActivity.BUNDLE_BARCODETYPE, barcodeValues.format());
                            newIntent2.putExtra(RecordActivity.BUNDLE_CARDID, barcodeValues.content());
                            newIntent2.putExtra(RecordActivity.BUNDLE_CARDCODE, String.valueOf(Utils.BARCODE_IMPORT_FROM_IMAGE_FILE));
                            newIntent2.putExtra(RecordActivity.BUNDLE_ID,id);
                            newIntent2.putExtra(RecordActivity.BUNDLE_NAME,name);
                            newIntent2.putExtra(RecordActivity.BUNDLE_TIME,time);
                            newIntent2.putExtra(RecordActivity.BUNDLE_ENDTIME,endTime);
                            newIntent2.putExtra(RecordActivity.BUNDLE_CARDNOTE,note);
                            startActivity(newIntent2);
                            System.out.println("''''''''''''''''''''''''scanold了");
                        }else {
                             newIntent = new Intent(getApplicationContext(), RecordActivity.class);
                            //                        Bundle newBundle = new Bundle();
                            newIntent.putExtra(RecordActivity.BUNDLE_BARCODETYPE, barcodeValues.format());
                            newIntent.putExtra(RecordActivity.BUNDLE_CARDID, barcodeValues.content());
                            newIntent.putExtra(RecordActivity.BUNDLE_CARDCODE, String.valueOf(Utils.BARCODE_IMPORT_FROM_IMAGE_FILE));
                            newIntent.putExtra(RecordActivity.BUNDLE_ID, id);
                            newIntent.putExtra(RecordActivity.BUNDLE_NAME, name);
                            newIntent.putExtra(RecordActivity.BUNDLE_TIME, time);
                            newIntent.putExtra(RecordActivity.BUNDLE_ENDTIME, endTime);
                            newIntent.putExtra(RecordActivity.BUNDLE_CARDNOTE, note);
                            startActivity(newIntent);
                            System.out.println("''''''''''''''''''''''''另外的");

                        }



//                        Bundle inputBundle = intent.getExtras();
//                        if (inputBundle != null && inputBundle.getString(RecordActivity.BUNDLE_ADDGROUP) != null) {
//                            newBundle.putString(LoyaltyCardEditActivity.BUNDLE_ADDGROUP, inputBundle.getString(LoyaltyCardEditActivity.BUNDLE_ADDGROUP));
//                        }
//                        newIntent.putExtras(newBundle);
//                        startActivity(newIntent);
                    }
//                    handleActivityResult(Utils.BARCODE_IMPORT_FROM_IMAGE_FILE, result.getResultCode(), result.getData());
                }
        );
        customBarcodeScannerBinding.addFromImage.setOnClickListener(this::addFromImage);
//        customBarcodeScannerBinding.addManually.setOnClickListener(this::addManually);

        barcodeScannerView = binding.zxingBarcodeScanner;

        // Even though we do the actual decoding with the barcodeScannerView
        // CaptureManager needs to be running to show the camera and scanning bar
        capture = new CatimaCaptureManager(this, barcodeScannerView);
        Intent captureIntent = new Intent();
        Bundle captureIntentBundle = new Bundle();
        captureIntentBundle.putBoolean(Intents.Scan.BEEP_ENABLED, false);
        captureIntent.putExtras(captureIntentBundle);
        capture.initializeFromIntent(captureIntent, savedInstanceState);

        barcodeScannerView.decodeSingle(new BarcodeCallback() {
            //读取后二维码中的内容
            @Override
            public void barcodeResult(BarcodeResult result) {
                BarcodeFormat barcodeFormat = result.getBarcodeFormat();
                Intent newIntent = new Intent(getApplicationContext(), RecordActivity.class);
//                Bundle newBundle = new Bundle();
                newIntent.putExtra(RecordActivity.BUNDLE_BARCODETYPE, barcodeFormat.name());
                newIntent.putExtra(RecordActivity.BUNDLE_CARDID, result.getText());
                Result a = result.getResult();


                startActivity(newIntent);

            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });

        // Monitor button
//        Button
//        button_code.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("点击了");
//                Intent intent = new Intent();
//                intent.setClass(ScanActivity.this, RecordActivity.class);
//
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
//            getMenuInflater().inflate(R.menu.scan_menu, menu);
//        }
//
//        barcodeScannerView.setTorchOff();
//
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(Activity.RESULT_CANCELED);
            finish();
            return true;
        }
//        else if (item.getItemId() == R.id.action_toggle_flashlight) {
//            if (torch) {
//                torch = false;
//                barcodeScannerView.setTorchOff();
//                item.setTitle(R.string.turn_flashlight_on);
//                item.setIcon(R.drawable.ic_flashlight_off_white_24dp);
//            } else {
//                torch = true;
//                barcodeScannerView.setTorchOn();
//                item.setTitle(R.string.turn_flashlight_off);
//                item.setIcon(R.drawable.ic_flashlight_on_white_24dp);
//            }
//        }

        return super.onOptionsItemSelected(item);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        handleActivityResult(requestCode, resultCode, data);
//    }

    private void handleActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        BarcodeValues barcodeValues;

        try {
            barcodeValues = Utils.parseSetBarcodeActivityResult(requestCode, resultCode, intent, this);
        } catch (NullPointerException e) {
            Toast.makeText(this, R.string.errorReadingImage, Toast.LENGTH_LONG).show();
            return;
        }

        if (!barcodeValues.isEmpty()) {
//            Intent manualResult = new Intent();
//            Bundle manualResultBundle = new Bundle();
//            manualResultBundle.putString(BarcodeSelectorActivity.BARCODE_CONTENTS, barcodeValues.content());
//            manualResultBundle.putString(BarcodeSelectorActivity.BARCODE_FORMAT, barcodeValues.format());
//            if (addGroup != null) {
//                manualResultBundle.putString(LoyaltyCardEditActivity.BUNDLE_ADDGROUP, addGroup);
//            }
//            manualResult.putExtras(manualResultBundle);
//            ScanActivity.this.setResult(RESULT_OK, manualResult);
//            finish();
        }
    }

    public void addManually(View view) {
//        Intent i = new Intent(getApplicationContext(), BarcodeSelectorActivity.class);
//        if (cardId != null) {
//            final Bundle b = new Bundle();
//            b.putString("initialCardId", cardId);
//            i.putExtras(b);
//        }
//        manualAddLauncher.launch(i);
    }

    public void addFromImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        try {
            photoPickerLauncher.launch(photoPickerIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), R.string.failedLaunchingPhotoPicker, Toast.LENGTH_LONG).show();
            Log.e(TAG, "No activity found to handle intent", e);
        }
    }
}
