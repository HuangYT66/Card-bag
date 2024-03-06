package com.example.cardbag;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Locale;

public class AboutActivity extends Activity {
    ImageButton  button1,button2,button3,button4,button5,button6,button7;
    ImageView backButton;
    String language="en";
    TextView tx1,tx2,tx3,tx4,tx5,tx6,tx_reference_about,tx_permit_about;

    // 调用Actvity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);//关联activity.xml


        //tx
        tx1 = findViewById(R.id.main_name_about);
        System.out.println(tx1.getText()+"lllllllllllllldddddddddddddddddd");
        tx2 = findViewById(R.id.version_about);
        tx3 = findViewById(R.id.author_about);
        tx4 = findViewById(R.id.address_about);
        tx5 = findViewById(R.id.policy_about);
        tx6 = findViewById(R.id.subpolicy_about);
        tx_reference_about = findViewById(R.id.reference_about);
        tx_permit_about= findViewById(R.id.permit_about);

        //recieve data from home
        Intent intentFromHome = getIntent();
        if(intentFromHome.getStringExtra("language")!=null){
            language = intentFromHome.getStringExtra("language");
            //Set language

            setLocale(language);}








        //return
        backButton=findViewById(R.id.about_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, HomepageActivity.class);
                intent.putExtra("language",language);
                startActivity(intent);
            }
        });

        //button1
        button1=findViewById(R.id.more_button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                //    设置Title的内容
                builder.setTitle("Current Version");
                //    设置Content来显示一个信息
                builder.setMessage("1.0.0.2");
                //    设置一个PositiveButton
                builder.setPositiveButton("confirm", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(AboutActivity.this, "conform", Toast.LENGTH_SHORT).show();
                    }
                });
                //    显示出该对话框
                builder.show();
            }
            //which is the int value represented by the action button, which=-1 means that the OK button was clicked

        });

        //button2
        button2=findViewById(R.id.more_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                //    设置Title的内容
                builder.setTitle("Author");
                //    设置Content来显示一个信息
                builder.setMessage("YutingHuang @20205737");
                //    设置一个PositiveButton
                builder.setPositiveButton("confirm", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(AboutActivity.this, "conform", Toast.LENGTH_SHORT).show();
                    }
                });
                //    显示出该对话框
                builder.show();
            }
            //which is the int value represented by the action button, which=-1 means that the OK button was clicked

        });

        //button4
        button4=findViewById(R.id.more_button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                //    设置Title的内容
                builder.setTitle("Privacy Policy");
                //    设置Content来显示一个信息
                builder.setMessage("This app does not collect or transmit any personal information.\n" +
                        "To ensure that the application functions correctly, we need a camera: this application needs to access your camera to scan the barcode. When camera access is denied, the application can still be used, but you must manually type the barcode information.");
                //    设置一个PositiveButton
                builder.setPositiveButton("confirm", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(AboutActivity.this, "conform", Toast.LENGTH_SHORT).show();
                    }
                });
                //    显示出该对话框
                builder.show();
            }
            //which is the int value represented by the action button, which=-1 means that the OK button was clicked

        });

        //button3
        button3=findViewById(R.id.more_button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                //    设置Title的内容
                builder.setTitle("Address of code");
                //    设置Content来显示一个信息
                builder.setMessage("https://csmoodle.ucd.ie/moodle/mod/assign/view.php?id=73192");
                //    设置一个PositiveButton
                builder.setPositiveButton("confirm", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(AboutActivity.this, "conform", Toast.LENGTH_SHORT).show();
                    }
                });
                //    显示出该对话框
                builder.show();
            }
            //which is the int value represented by the action button, which=-1 means that the OK button was clicked

        });

        //button5
        button5=findViewById(R.id.more_button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                //    设置Title的内容
                builder.setTitle("Reference");
                //    设置Content来显示一个信息
                builder.setMessage("Android zxingLibrary/Android NFC（ https://github.com/yipianfengye/android-zxingLibrary.git ");
                //    设置一个PositiveButton
                builder.setPositiveButton("confirm", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(AboutActivity.this, "conform", Toast.LENGTH_SHORT).show();
                    }
                });
                //    显示出该对话框
                builder.show();
            }
            //which is the int value represented by the action button, which=-1 means that the OK button was clicked

        });

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
        tx1.setText(R.string.about_card_bag);
        tx2.setText(R.string.current_version_about);
        tx3.setText(R.string.author_about);
        tx4.setText(R.string.address_about);
        tx5.setText(R.string.privacy_about);
        tx6.setText(R.string.permission_about);
        tx_permit_about.setText(R.string.permission_about);
        tx_reference_about.setText(R.string.reference_about);


    }





}
