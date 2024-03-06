package com.example.cardbag;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cardbag.Scan.ScanActivity;
import com.example.cardbag.Scan.Utils;

public class OldMainActivity extends Activity {
    String version = "old";
    ImageView add_button,card_cord,edit_button,backTo;
    EditText card_name;
//    TextView note_time;
    String id,card_time,card_note,card_type,input_time,card_id,note_time;
    Bitmap bitmap = null;


    //init from scan
    public static final String BUNDLE_CARDID = "cardId";
    public static final String BUNDLE_BARCODETYPE = "barcodeType";
    public static final String BUNDLE_CARDCODE = "cardCode";
    public static final String BUNDLE_ID = "id";
    public static final String BUNDLE_NAME = "name";
    public static final String BUNDLE_ENDTIME = "endtime";
    public static final String BUNDLE_TIME = "time";
    public static final String BUNDLE_CARDNOTE = "note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_mainpage);

        //init info
        id="";
        card_time="";
        card_note="";
        card_type="";
        card_id="";
        input_time = "";
        note_time="";




        //bind layout
        card_name = findViewById(R.id.old_main_card_name);
        card_cord = findViewById(R.id.old_main_code);

//        //accept data from edit
//        Intent intent_recieve = getIntent();
//        if (intent_recieve.getStringExtra("card_name")!=null){
//            card_name.setText(intent_recieve.getStringExtra("card_name"));
//        }

        //recieve data from
        Intent intentFromRecord = getIntent();
        if (intentFromRecord != null) {
            id = intentFromRecord.getStringExtra("id");
            if (id != null) {
                card_name.setText(intentFromRecord.getStringExtra("card_name"));
                version = intentFromRecord.getStringExtra("version");
                card_id = intentFromRecord.getStringExtra("card_id");
                System.out.println("version in oldman"+version);
                card_note = (intentFromRecord.getStringExtra("card_note"));
                card_time =(intentFromRecord.getStringExtra("card_time"));
                card_type=(intentFromRecord.getStringExtra("card_type"));
                note_time=(intentFromRecord.getStringExtra("time"));
            }
        }



        //scan
        //接受数据form scan
        String card_id_s = getIntent().getStringExtra(BUNDLE_CARDID);
        String type = getIntent().getStringExtra(BUNDLE_BARCODETYPE);


        if (getIntent().getStringExtra(BUNDLE_ID) != null) {
            id = getIntent().getStringExtra(BUNDLE_ID);
        }

        if (getIntent().getStringExtra(BUNDLE_TIME) != null) {
            input_time = getIntent().getStringExtra(BUNDLE_TIME);
        }

        if (getIntent().getStringExtra(BUNDLE_NAME) != null) {
            card_name.setText(getIntent().getStringExtra(BUNDLE_NAME));
        }

        if (card_id_s != null) {
            card_id = card_id_s;
        }

        if (getIntent().getStringExtra(BUNDLE_ENDTIME) != null) {
            card_time=getIntent().getStringExtra(BUNDLE_ENDTIME);
        }

        if (getIntent().getStringExtra(BUNDLE_CARDNOTE) != null) {
            card_note=getIntent().getStringExtra(BUNDLE_CARDNOTE);
        }

        if (type != null) {
            card_type=type;
        }


        if (Utils.saveBitmap != null) {
            bitmap = Utils.saveBitmap;
            card_cord.setImageBitmap(bitmap);
            card_cord.setMaxHeight(900);
            card_cord.setMaxWidth(900);
            card_cord.setAdjustViewBounds(true);
        }








        //add
        add_button = findViewById(R.id.old_add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("add_button");
                Intent intent = new Intent(OldMainActivity.this, ScanActivity.class);
                intent.putExtra("thisid", id);
                intent.putExtra("thename", card_name.getText().toString());
                intent.putExtra("thenote", card_note);
                intent.putExtra("theendtime", card_time);
                intent.putExtra("thetime", input_time);
                intent.putExtra("version", version);
                startActivity(intent);

                add_button.setVisibility(View.GONE);
            }
        });



        //edit
        edit_button = findViewById(R.id.old_main_edit);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("edit_button");
                Intent intent2 = new Intent(OldMainActivity.this, RecordActivity.class);
                intent2.putExtra("id","1" );
                intent2.putExtra("card_name", card_name.getText().toString());
                System.out.println("修改时传给修改class的"+card_name.getText().toString()+"   "+version);
                intent2.putExtra("card_note", card_note);
                intent2.putExtra("card_time", card_time);
                intent2.putExtra("note_time", input_time);
                intent2.putExtra("version",version);
                intent2.putExtra("card_id",card_id);
                intent2.putExtra("type", card_type);

                startActivity(intent2);
            }
        });



        //back to
        backTo=findViewById(R.id.old_setting);
        backTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(OldMainActivity.this, HomepageActivity.class);
                startActivity(intentBack);
            }
        });
    }



}
