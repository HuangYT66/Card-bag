package com.example.cardbag;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cardbag.Scan.ScanActivity;
import com.example.cardbag.Scan.Utils;
import com.example.cardbag.database.SQLiteHelper;
import com.example.cardbag.utils.DBUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

public class RecordActivity extends Activity implements View.OnClickListener {
    ImageView note_back;
    TextView note_time;
    EditText card_name, card_id, card_time, card_note, card_type;
    ImageView card_code;
    ImageView delete;
    ImageView note_save;
    SQLiteHelper mSQLiteHelper;
    TextView noteName;
    String id;
    String version = "young";
    Button button_code;
    Bitmap bitmap = null;
    TextView tx1,tx2,tx3,tx4,tx5,tx6;
    String language="en";

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
        setContentView(R.layout.activity_record);
        note_back = (ImageView) findViewById(R.id.main_back);
        note_time = (TextView) findViewById(R.id.tv_time);
        card_name = (EditText) findViewById(R.id.main_card_name);
        card_id = (EditText) findViewById(R.id.main_card_id);
        card_time = (EditText) findViewById(R.id.main_card_expiration_time);
        card_note = (EditText) findViewById(R.id.main_card_note);
        card_code = findViewById(R.id.main_card_code);
        card_type = findViewById(R.id.main_card_type);
        delete = (ImageView) findViewById(R.id.delete);
        note_save = (ImageView) findViewById(R.id.note_save);
        noteName = (TextView) findViewById(R.id.main_name_edit);
        note_back.setOnClickListener(this);
        delete.setOnClickListener(this);
        note_save.setOnClickListener(this);
        button_code = findViewById(R.id.add_code);

        //tx
        //tx
        tx2 = findViewById(R.id.card_name_edit);
        tx3 = findViewById(R.id.card_id_edit);
        tx4 = findViewById(R.id.due_time_edit);
        tx5 = findViewById(R.id.note_edit);
        tx6 = findViewById(R.id.type_edit);

        //recieve data from home
        Intent intentFromHome = getIntent();
        if(intentFromHome.getStringExtra("language")!=null){
            language = intentFromHome.getStringExtra("language");
            //Set language
            setLocale(language);}



        initData();







        //接受数据 from scan
        String card_id_s = getIntent().getStringExtra(BUNDLE_CARDID);
        String type = getIntent().getStringExtra(BUNDLE_BARCODETYPE);


        if (getIntent().getStringExtra(BUNDLE_ID) != null) {
            id = getIntent().getStringExtra(BUNDLE_ID);
        }

        if (getIntent().getStringExtra(BUNDLE_TIME) != null) {
            note_time.setText(getIntent().getStringExtra(BUNDLE_TIME));
        }

        if (getIntent().getStringExtra(BUNDLE_NAME) != null) {
            card_name.setText(getIntent().getStringExtra(BUNDLE_NAME));
        }

        if (card_id_s != null) {
            card_id.setText(card_id_s);
        }

        if (getIntent().getStringExtra(BUNDLE_ENDTIME) != null) {
            card_time.setText(getIntent().getStringExtra(BUNDLE_ENDTIME));
        }

        if (getIntent().getStringExtra(BUNDLE_CARDNOTE) != null) {
            card_note.setText(getIntent().getStringExtra(BUNDLE_CARDNOTE));
        }

        if (type != null) {
            card_type.setText(type);
        }


        if (Utils.saveBitmap != null) {
            bitmap = Utils.saveBitmap;
            card_code.setImageBitmap(bitmap);
        }


        // Monitor button
        button_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecordActivity.this, ScanActivity.class);
                intent.putExtra("thisid", id);
                intent.putExtra("thename", card_name.getText().toString());
                intent.putExtra("thenote", card_note.getText().toString());
                intent.putExtra("theendtime", card_time.getText().toString());
                intent.putExtra("thetime", note_time.getText().toString());
                startActivity(intent);
            }
        });
    }


    protected void initData() {

        mSQLiteHelper = new SQLiteHelper(this);
        if(language=="en"){
            noteName.setText("add information");
        }else {
            noteName.setText("添加");
        }
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            card_name.setText(id);
            System.out.println("idididiidididididididi      "+id);
            if (id != null) {
                System.out.println("initData"+language);
                if(language.equals("en")){
                    System.out.println("edit information");
                    noteName.setText("edit information");
                }else {
                    System.out.println("edi修改信息t information");
                    noteName.setText("修改信息");
                }
                card_name.setText(intent.getStringExtra("card_name"));
                version = intent.getStringExtra("version");
                card_id.setText(intent.getStringExtra("card_id"));
                card_note.setText(intent.getStringExtra("card_note"));
                card_time.setText(intent.getStringExtra("card_time"));
                card_type.setText(intent.getStringExtra("card_type"));
                note_time.setText(intent.getStringExtra("time"));
                note_time.setVisibility(View.VISIBLE);
                readImage();
            }
        }
    }

    //    设置点击不同图标时的反应
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //返回主页面
            case R.id.main_back:
                Intent intent = new Intent();
                intent.setClass(RecordActivity.this, HomepageActivity.class);
                intent.putExtra("language",language);
                startActivity(intent);
                break;
            //删除
            case R.id.delete:
                card_name.setText("");
                card_id.setText("");
                card_time.setText("");
                card_note.setText("");
                card_type.setText("");
//                bitmap..setText("");
                break;
            //保存数据
            case R.id.note_save:
                String cardName = card_name.getText().toString().trim();
                String cardID = card_id.getText().toString().trim();
                String cardNote = card_note.getText().toString().trim();
                String cardTime = card_time.getText().toString().trim();
                String cardType = card_type.getText().toString().trim();
                System.out.println("“保存数据id");
                if (id != null) {//修改操作
                    if (cardName.length() > 0) {
                        //图片转为二进制 bitmabToBytes(this))
                        if(version==null){
                            version = "young";
                        }
                        if (version.equals("old")) {
                            System.out.println("在recordactivity中记录用old跳转");
                            Intent intent4 = new Intent(RecordActivity.this, OldMainActivity.class);
                            intent4.putExtra("card_name", card_name.getText().toString());
                            intent4.putExtra("id","1" );
//                            System.out.println("修改时传给修改class的"+card_name.getText().toString()+"   "+version);
                            intent4.putExtra("card_note", card_note.getText().toString());
                            intent4.putExtra("card_time", card_time.getText().toString());
                            intent4.putExtra("note_time", note_time.getText().toString());
                            intent4.putExtra("version",version);
                            intent4.putExtra("card_id",card_id.getText().toString());
                            intent4.putExtra("type", card_type.getText().toString());
                            intent4.putExtra("language",language);
                            startActivity(intent4);
                            System.out.println("用old跳转");
                            return;
                        } else {
                            if (mSQLiteHelper.updateData(id, cardName, cardID, cardNote, DBUtils.getTime(), cardTime, cardType, bitmabToBytes(this))) {
//                            if (mSQLiteHelper.updateData(id, cardName, cardID, cardNote, DBUtils.getTime(), cardTime, cardType, null)) {
                                showToast("modify successfully");

                                setResult(2);
                                Intent intent2 = new Intent(RecordActivity.this, HomepageActivity.class);
                                intent2.putExtra("language",language);
                                System.out.println("tiaozhuanl "+language);
                                startActivity(intent2);
                            } else {
                                showToast("change failed");
                                System.out.println("id为空");
                            }

                        }

                    } else {
                        showToast("修改内容不能为空!");
                        System.out.println("修改内容为空");
                    }
                } else {
                    System.out.println("在修改数据库时"+cardName);
                    //向数据库中添加数据
                    if (cardName.length() > 0) {
                        if (mSQLiteHelper.insertData(cardName, cardID, cardNote, DBUtils.getTime(), cardTime, cardType, bitmabToBytes(this))) {
//                            if (mSQLiteHelper.insertData(cardName, cardID, cardNote, DBUtils.getTime(), cardTime, cardType, null)) {
                            showToast("save successfully ");
                            setResult(2);
                            finish();
                        } else {
                            showToast("save failed");
                            System.out.println("修改失败");
                        }
                    } else {
                        showToast("修改内容不能为空!");
                        System.out.println("修改内容为空");
                    }
                }
                Intent intent3 = new Intent(RecordActivity.this, HomepageActivity.class);
                intent3.putExtra("language",language);
                startActivity(intent3);


//
        }
    }

    public void showToast(String message) {
        Toast.makeText(RecordActivity.this, message, Toast.LENGTH_SHORT).show();
    }


//    图片转为二进制数据
    public byte[] bitmabToBytes(Context context) {
        if (bitmap != null) {
            //将图片转化为位图
            System.out.println("将图片转化为位图");
            int size = bitmap.getWidth() * bitmap.getHeight() * 4;
            System.out.println("            将图片转化为位图\ndaxiao ");
            //创建一个字节数组输出流,流的大小为size
            ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
            try {
                //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                //将字节数组输出流转化为字节数组byte[]
                byte[] imagedata = baos.toByteArray();
                return imagedata;
            } catch (Exception e) {
            } finally {
                try {
                    bitmap.recycle();
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new byte[0];
        } else {
            return null;
        }
    }

    private void readImage() {
        byte[] imgData = SQLiteHelper.readImage(id);
        if (imgData != null) {
            //将字节数组转化为位图
            Bitmap imagebitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
            //将位图显示为图片
            card_code.setImageBitmap(imagebitmap);
        } else {
            card_code.setBackgroundResource(android.R.drawable.menuitem_background);
        }
    }




    //language
    private void setLocale(String language) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(language);
        resources.updateConfiguration(configuration,metrics);
        onConfigurationChanged(configuration);
    }
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Set strings from resource
        System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        noteName.setText(R.string.card_bag);
        tx2.setText(R.string.card_name_edit);
        tx3.setText(R.string.card_id_edit);
        tx4.setText(R.string.due_time_edit);
        tx5.setText(R.string.note_edit);
        tx6.setText(R.string.type_edit);
        button_code.setText(R.string.add_code_edit);


    }
}
