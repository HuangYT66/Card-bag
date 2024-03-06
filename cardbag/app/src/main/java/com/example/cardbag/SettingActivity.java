package com.example.cardbag;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Locale;

public class SettingActivity extends Activity {
    ImageView backButton,goButton;
    TextView version,subVersion,title,languageText,theme;

    RadioGroup rgLanguage;
    RadioButton rbEnglish,rbChinese,rbWhite,rbDark;
    Button button;
    String language="en";
    // 调用Actvity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);//关联activity.xml


        //assign variable
        title = findViewById(R.id.setting_name);
        version = findViewById(R.id.setting_version);
        subVersion = findViewById(R.id.setting_subVer);
        languageText = findViewById(R.id.setting_language);
//        theme = findViewById(R.id.setting_theme);

        rbEnglish = findViewById(R.id.rb_english);
        rbChinese = findViewById(R.id.rb_chinese);
//        rbWhite = findViewById(R.id.rb_white);
//        rbDark = findViewById(R.id.rb_dark);

        rgLanguage = findViewById(R.id.rg_language);



        //recieve data from home
        Intent intentFromHone = getIntent();
        if(intentFromHone.getStringExtra("language")!=null){
        language = intentFromHone.getStringExtra("language");
        //Set language
        setLocale(language);}

        //Set listener on radio group
        rgLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //Check condition
                switch(i){
                    case R.id.rb_english:
                        //when english selected
                        //initialize string
                        language = "en";
                        //set locale
                        setLocale(language);
                        break;
                    case R.id.rb_chinese:
                        //when chinese selected
                        //set locale
                        language = "zh";
                        setLocale("zh");
                        break;
                }
            }
        });



//        // Monitor button
//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                //monitor button, if clink, then jump
//                Intent intent = new Intent(MainActivity.this,MenuActivity.class);
//                intent.putExtra("language",language);
//                //the parameter setClass, former is current page; later is next page
//                startActivity(intent);
//            }
//        });


        //go old
        goButton=findViewById(R.id.more_button1);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, OldMainActivity.class);
                intent.putExtra("language",language);
                startActivity(intent);
            }
        });


        //return
        backButton = findViewById(R.id.setting_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(SettingActivity.this, HomepageActivity.class);
                intent_back.putExtra("language",language);
                startActivity(intent_back);
            }
        });
    }


    private void setLocale(String language) {
        //initialize resource
        Resources resources = getResources();
        //initialize metrics
        DisplayMetrics metrics = resources.getDisplayMetrics();
        //initialize configuration
        Configuration configuration = resources.getConfiguration();
        //initialize locale
        configuration.locale = new Locale(language);
        // update configuration
        resources.updateConfiguration(configuration,metrics);
        //notify configuration
        onConfigurationChanged(configuration);
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Set strings from resource
        title.setText(R.string.setting);
        version.setText(R.string.version);
        subVersion.setText(R.string.deVersion_text);
        languageText.setText(R.string.language);
        rbEnglish.setText(R.string.english);
        rbChinese.setText(R.string.chinese);

    }


}
