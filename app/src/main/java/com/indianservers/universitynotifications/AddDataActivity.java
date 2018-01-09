package com.indianservers.universitynotifications;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddDataActivity extends AppCompatActivity implements View.OnClickListener{
    private Spinner getcourses,getbranches,getsemisters,getregulations;
    private ImageView newcourse, newbranch, newsemister, newregulation;
    private EditText subject, eyear,pdflink,examname,examtype;
    Firebase firebase;
    private RadioGroup radioGroup;
    private String jntuType;
    private Button save;
    private ArrayList<String> courses = new ArrayList<>();
    private ArrayList<String> branches = new ArrayList<>();
    private ArrayList<String> semisters = new ArrayList<>();
    private ArrayList<String> regulations = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        newcourse = (ImageView)findViewById(R.id.addcourses);
        newcourse.setOnClickListener(this);
        newbranch = (ImageView)findViewById(R.id.addbranches);
        newbranch.setOnClickListener(this);
        newsemister = (ImageView)findViewById(R.id.addsemister);
        newsemister.setOnClickListener(this);
        newregulation = (ImageView)findViewById(R.id.addregulation);
        newregulation.setOnClickListener(this);
        save = (Button)findViewById(R.id.saveDATA);
        save.setOnClickListener(this);

        subject = (EditText)findViewById(R.id.subjectname);
        eyear = (EditText)findViewById(R.id.year);
        pdflink = (EditText)findViewById(R.id.pdflink);
        examname = (EditText)findViewById(R.id.examname);
        examtype = (EditText)findViewById(R.id.examtype);

        getcourses = (Spinner)findViewById(R.id.listofcourses);
        getbranches = (Spinner)findViewById(R.id.listofbranches);
        getsemisters = (Spinner)findViewById(R.id.listofsemisters);
        getregulations = (Spinner)findViewById(R.id.listofregulation);
        radioGroup = (RadioGroup)findViewById(R.id.addjntuselection);
        Firebase.setAndroidContext(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup,int i) {
                try {
                    courses.clear();
                    branches.clear();
                    semisters.clear();
                    regulations.clear();
                    int selectedId = radioGroup .getCheckedRadioButtonId();
                    final RadioButton radioButton1 = (RadioButton)findViewById(selectedId);
                    jntuType = radioButton1.getText().toString();
                    getCourses(jntuType);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
        getcourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getBranches(jntuType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getbranches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getSemisters(jntuType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getsemisters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getRegulations(jntuType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void getSemisters(String jntuType) {
        semisters.clear();
        String coursetype = getcourses.getSelectedItem().toString();
        String branchtype = getbranches.getSelectedItem().toString();
        firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntuType+"/"+"listofsemesters"+"/"+coursetype+"/"+branchtype);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        refreshdatasemisters();
    }
    private void getRegulations(String jntuType) {
        regulations.clear();
        String coursetype = getcourses.getSelectedItem().toString();
        String branchtype = getbranches.getSelectedItem().toString();
        String semestertype = getsemisters.getSelectedItem().toString();
        firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntuType+"/"+"listofregulations"+"/"+coursetype+"/"+branchtype+"/"+semestertype);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        refreshdataregulations();
    }
    private void getBranches(String jntuType) {
        branches.clear();
        String coursetype = getcourses.getSelectedItem().toString();
        firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntuType+"/"+"listofbranches"+"/"+coursetype);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        refreshdatabranches();
    }
    private void getCourses(String jntuType) {
        courses.clear();
        firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntuType+"/"+"listofcourses");
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        refreshdatacourses();
    }

    public  void refreshdatacourses() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getupdatescourses(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getupdatescourses(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    public  void refreshdatabranches() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getupdatesbranches(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getupdatesbranches(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    public  void refreshdatasemisters() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getupdatessemisters(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getupdatessemisters(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    public  void refreshdataregulations() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getupdatesregulations(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getupdatesregulations(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void getupdatescourses(DataSnapshot dataSnapshot){
        courses.add((String) dataSnapshot.getValue());
        if(courses.size()>0)
        {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, courses);
            getcourses.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

        }else
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    public void getupdatesbranches(DataSnapshot dataSnapshot){
        branches.add((String) dataSnapshot.getValue());
        if(branches.size()>0)
        {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, branches);
            getbranches.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

        }else
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    public void getupdatessemisters(DataSnapshot dataSnapshot){
        semisters.add((String) dataSnapshot.getValue());
        if(semisters.size()>0)
        {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, semisters);
            getsemisters.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

        }else
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    public void getupdatesregulations(DataSnapshot dataSnapshot){
        regulations.add((String) dataSnapshot.getValue());
        if(regulations.size()>0)
        {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, regulations);
            getregulations.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

        }else
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addcourses:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.alertdialog, null);
                builder.setTitle("Add New Course");
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                final EditText newCourse = (EditText)dialogView.findViewById(R.id.addcourse);
                Button button = (Button)dialogView.findViewById(R.id.savedata);
                int selectedId = radioGroup .getCheckedRadioButtonId();
                RadioButton radioButton1 = (RadioButton)findViewById(selectedId);
                try {
                    jntuType = radioButton1.getText().toString();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String coursename = newCourse.getText().toString();
                        firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntuType+"/"+"listofcourses");
                        firebase.push().setValue(coursename);
                        Toast.makeText(AddDataActivity.this,"Data Saved",Toast.LENGTH_SHORT).show();
                        newCourse.setText("");
                        alertDialog.dismiss();
                        getCourses(jntuType);
                    }
                });
                alertDialog.show();

                break;
            case R.id.addbranches:
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                LayoutInflater inflater1 = this.getLayoutInflater();
                final View dialogView1 = inflater1.inflate(R.layout.alertdialog, null);
                builder1.setTitle("Add New Branch");
                builder1.setView(dialogView1);
                final AlertDialog alertDialog1 = builder1.create();
                final EditText newCourse1 = (EditText)dialogView1.findViewById(R.id.addcourse);
                Button button1 = (Button)dialogView1.findViewById(R.id.savedata);
                int selectedId1 = radioGroup .getCheckedRadioButtonId();
                RadioButton radioButton11 = (RadioButton)findViewById(selectedId1);
                try {
                    jntuType = radioButton11.getText().toString();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String coursename = newCourse1.getText().toString();
                        String coursetype = getcourses.getSelectedItem().toString();
                        firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntuType+"/"+"listofbranches"+"/"+coursetype);
                        firebase.push().setValue(coursename);
                        Toast.makeText(AddDataActivity.this,"Data Saved",Toast.LENGTH_SHORT).show();
                        newCourse1.setText("");
                        getBranches(jntuType);
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1.show();
                break;
            case R.id.addsemister:
                final AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                LayoutInflater inflater2 = this.getLayoutInflater();
                final View dialogView2 = inflater2.inflate(R.layout.alertdialog, null);
                builder2.setTitle("Add New Semister");
                builder2.setView(dialogView2);
                final AlertDialog alertDialog2 = builder2.create();
                final EditText newCourse2 = (EditText)dialogView2.findViewById(R.id.addcourse);
                Button button2 = (Button)dialogView2.findViewById(R.id.savedata);
                int selectedId2 = radioGroup .getCheckedRadioButtonId();
                RadioButton radioButton12 = (RadioButton)findViewById(selectedId2);
                try {
                    jntuType = radioButton12.getText().toString();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String coursename = newCourse2.getText().toString();
                        String coursetype = getcourses.getSelectedItem().toString();
                        String branchtype = getbranches.getSelectedItem().toString();
                        firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntuType+"/"+"listofsemesters"+"/"+coursetype+"/"+branchtype);
                        firebase.push().setValue(coursename);
                        Toast.makeText(AddDataActivity.this,"Data Saved",Toast.LENGTH_SHORT).show();
                        newCourse2.setText("");
                        getSemisters(jntuType);
                        alertDialog2.dismiss();
                    }
                });
                alertDialog2.show();
                break;
            case R.id.addregulation:
                final AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                LayoutInflater inflater3 = this.getLayoutInflater();
                final View dialogView3 = inflater3.inflate(R.layout.alertdialog, null);
                builder3.setTitle("Add New Regulation");
                builder3.setView(dialogView3);
                final AlertDialog alertDialog3 = builder3.create();
                final EditText newCourse3 = (EditText)dialogView3.findViewById(R.id.addcourse);
                Button button3 = (Button)dialogView3.findViewById(R.id.savedata);
                int selectedId3 = radioGroup .getCheckedRadioButtonId();
                RadioButton radioButton13 = (RadioButton)findViewById(selectedId3);
                try {
                    jntuType = radioButton13.getText().toString();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String coursename = newCourse3.getText().toString();
                        String coursetype = getcourses.getSelectedItem().toString();
                        String branchtype = getbranches.getSelectedItem().toString();
                        String semestertype = getsemisters.getSelectedItem().toString();
                        firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntuType+"/"+"listofregulations"+"/"+coursetype+"/"+branchtype+"/"+semestertype);
                        firebase.push().setValue(coursename);
                        Toast.makeText(AddDataActivity.this,"Data Saved",Toast.LENGTH_SHORT).show();
                        newCourse3.setText("");
                        getRegulations(jntuType);
                        alertDialog3.dismiss();
                    }
                });
                alertDialog3.show();
                break;
            case R.id.saveDATA:
                String course = getcourses.getSelectedItem().toString();
                String branch = getbranches.getSelectedItem().toString();
                String semister = getsemisters.getSelectedItem().toString();
                String regulation = getregulations.getSelectedItem().toString();
                String sub  = subject.getText().toString();
                String year= this.eyear.getText().toString();
                String pdf = pdflink.getText().toString();
                String exname = examname.getText().toString();
                String extype = examtype.getText().toString();
                saveData(course,branch,semister,regulation,sub,year,pdf,exname,extype);
                break;
        }

    }

    private void saveData(String course, String branch, String semister, String regulation,String sub,String year,String pdf,String exname,String extype) {
        OldQuestionsModel oldQuestionsModel = new OldQuestionsModel();
        oldQuestionsModel.setSubject(sub);
        oldQuestionsModel.setYear(year);
        oldQuestionsModel.setPdflink(pdf);
        oldQuestionsModel.setExamname(exname);
        oldQuestionsModel.setExamtype(extype);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String typeofcategory = sharedPrefs.getString("type","0");
        firebase=new Firebase("https://universitesnotifications.firebaseio.com/"+jntuType+"/"+typeofcategory+"/"+course+"/"+branch+"/"+semister+"/"+regulation+"/"+regulation);
        firebase.push().setValue(oldQuestionsModel);
        Toast.makeText(AddDataActivity.this,"Data Saved",Toast.LENGTH_SHORT).show();
        subject.setText("");
        eyear.setText("");
        pdflink.setText("");
        examname.setText("");
        examtype.setText("");
    }
}
