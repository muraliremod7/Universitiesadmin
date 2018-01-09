package com.indianservers.universitynotifications;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private EditText editText, descr,imgeurl,weblink;
    private Spinner jntuTypes;
    public static String type;
    Firebase firebase;
    private RadioGroup radioGroup;
    private  String source;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.nsubject);
        descr = (EditText)findViewById(R.id.ndescription);
        imgeurl = (EditText)findViewById(R.id.nimage);
        weblink = (EditText)findViewById(R.id.nweblink);
        jntuTypes= (Spinner)findViewById(R.id.jntutype);
        Firebase.setAndroidContext(this);
        Button button = (Button)findViewById(R.id.submit);
        radioGroup = (RadioGroup)findViewById(R.id.jntuselection);

        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        jntuTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = jntuTypes.getSelectedItem().toString();
                if(type.equals("JNTUOLDQUESTIONPAPERS")){
                    editor = settings.edit();
                    editor.putString("type",type);
                    editor.commit();
                    startActivity(new Intent(MainActivity.this,AddDataActivity.class));
                }
                else if(type.equals("JNTUSYLLABUS")){
                    editor = settings.edit();
                    editor.putString("type",type);
                    editor.commit();
                    startActivity(new Intent(MainActivity.this,AddDataActivity.class));
                }
                else if(type.equals("IBPS")){
                    editor = settings.edit();
                    editor.putString("type",type);
                    editor.commit();
                    startActivity(new Intent(MainActivity.this,IBPSExamActivity.class));
                }
                if(type.equals("JOBUPDATES")){
                    radioGroup.setVisibility(View.GONE);
                }else{
                    radioGroup.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String nsubject = editText.getText().toString();
                String ndesc = descr.getText().toString();
                String imgUrl = imgeurl.getText().toString();
                String webUrl = weblink.getText().toString();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
                String date = mdformat.format(calendar.getTime());
                savedata(nsubject,date,ndesc,imgUrl,webUrl);
            }
        });
    }

    private void savedata(String nsubject, String date, String desc, String imgurl,String s) {
        CommonModel commonModel = new CommonModel();
        commonModel.setNotificationname(nsubject);
        commonModel.setNotificationdate(date);
        commonModel.setNotificationdesc(desc);
        commonModel.setImage(imgurl);
        commonModel.setWeblink(s);
        int selectedId = radioGroup .getCheckedRadioButtonId();
        RadioButton radioButton1 = (RadioButton)findViewById(selectedId);
        try {
            source = radioButton1.getText().toString();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        type = jntuTypes.getSelectedItem().toString();
        if(type.equals("JOBUPDATES")){
            firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+type+"/"+type);
        }
        else{
            firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+source+"/"+type+"/"+type);
        }
        firebase.push().setValue(commonModel);
        Toast.makeText(MainActivity.this,"Data Saved",Toast.LENGTH_SHORT).show();
        editText.setText("");
        descr.setText(" ");
        imgeurl.setText("");
    }
}
