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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cardbag.adapter.HomepageAdapter;
import com.example.cardbag.bean.HomepageBean;
import com.example.cardbag.database.SQLiteHelper;
import com.example.cardbag.utils.DBUtils;

import java.util.List;
import java.util.Locale;

public class HomepageActivity extends Activity {
    ListView listView;
    ImageView sort,about,setting,search,disSearch;
    TextView name;
    EditText searchArea;
    List<HomepageBean> list;
    SQLiteHelper mSQLiteHelper;
    HomepageAdapter adapter;
    String order = "id";
    String selectName = null;
    String[] column = new String[]{};
    String language = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        //用于显示便签的列表
        listView = (ListView) findViewById(R.id.listview);
        ImageView add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, RecordActivity.class);
//                startActivityForResult(intent, 1);
                System.out.println("tiaozhuantiaozhuantiaozhuantiaozhuan");
                startActivity(intent);
            }
        });




        //recieve data from setting
        Intent intentFromHome = getIntent();
        if(intentFromHome.getStringExtra("language")!=null){
            language = intentFromHome.getStringExtra("language");
            //Set language
            setLocale(language);}



        //about
        about=findViewById(R.id.main_about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToAbout = new Intent(HomepageActivity.this, AboutActivity.class);
                intentToAbout.putExtra("language",language);
                startActivity(intentToAbout);
            }
        });


        //setting
        setting=findViewById(R.id.main_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, SettingActivity.class);
                intent.putExtra("language",language);
                startActivity(intent);
            }
        });


        //sort
        sort=findViewById(R.id.sort);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });

        initData();

        //Search
        searchArea = findViewById(R.id.searchArea);
        search = findViewById(R.id.search);
        disSearch = findViewById(R.id.disSearch);

        //search
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了");
                selectName = "name like " +searchArea.getText().toString().trim();
                column = new String[]{};
//                column = DBUtils.HOMEPAGE_CARD_NAME;
                initData();
            }
        });

        disSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectName = null;
//                column;
                initData();
            }
        });
    }


    protected void initData() {
        mSQLiteHelper= new SQLiteHelper(this); //创建数据库
//        mSQLiteHelper.onCreate();
        showQueryData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                HomepageBean homepageBean = list.get(position);
                Intent intent = new Intent(HomepageActivity.this, RecordActivity.class);
                intent.putExtra("id", homepageBean.getId());
                intent.putExtra("time", homepageBean.getHomepageTime()); //记录的时间
                intent.putExtra("card_name", homepageBean.getHomepageName());//卡的名字
                intent.putExtra("card_id", homepageBean.getHomepageCardID());//卡的id
                intent.putExtra("card_type", homepageBean.getHomepageCardType());//卡的type
                intent.putExtra("card_time", homepageBean.getHomepageCardTime());//卡的due time
                intent.putExtra("card_image", homepageBean.getHomepageCardImage());//卡的图片
                intent.putExtra("card_note", homepageBean.getHomepageNote());//卡的备注
                intent.putExtra("version", "young");//卡的备注
                intent.putExtra("language", language);//语言


                HomepageActivity.this.startActivityForResult(intent, 1);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int
                    position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder( HomepageActivity.this)
                        .setMessage("Are you sure to delete？")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                HomepageBean homepageBean = list.get(position);
                                if(mSQLiteHelper.deleteData(homepageBean.getId())){
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(HomepageActivity.this,"success",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog =  builder.create();
                dialog.show();
                return true;
            }
        });


    }
    private void showQueryData(){
        if (list!=null){
            list.clear();
        }
        //从数据库中查询数据(保存的标签)
        list = mSQLiteHelper.query(order,column,selectName);
        adapter = new HomepageAdapter(this, list);
        listView.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==2){
            showQueryData();
        }
    }



    //    设置sort的dialog
    private void showListDialog(){
        final String[] items = {"add time","name" ,"card id","due time","card type"};
        AlertDialog.Builder dialog3 = new AlertDialog.Builder (this).setTitle ("choose one")
                .setItems (items, new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText (HomepageActivity.this,items[which],Toast.LENGTH_LONG).show ();
                        if(items[which]=="add time"){
                            order="id";
                        }else if(items[which]=="card id") {
                            order = "card_id";
//                        }else if(items[which]=="due time") {
//                            order = items[which];
                        }else if(items[which]=="card type") {
                            order = "type";
                        }else{
                            order = items[which];
                        }
                        initData();
                    }
                });
        dialog3.show ();

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
        name = findViewById(R.id.note_name);
        name.setText(R.string.card_bag);
    }




}
