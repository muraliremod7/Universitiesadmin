package com.indianservers.universitynotifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.Firebase;
import com.indianservers.universitynotifications.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class IBPSExamActivity extends AppCompatActivity {
    private Firebase firebase;
    HashMap<String,String> hashMap = new HashMap<>();
    private ArrayList<IbpsCommonClas> ibpsCommonClas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibpsexam);
        Firebase.setAndroidContext(IBPSExamActivity.this);
        firebase = new Firebase("https://universitesnotifications.firebaseio.com/JNTUK/GATE/ListofBranches");
        adddata("CE");
        adddata("ME");
        adddata("ag");
        adddata("ec1");
        adddata("ec2");
    }

    private void adddata(String ce) {
        IbpsCommonClas ibpsCommonClass = new IbpsCommonClas();
        ibpsCommonClass.setExamName(ce);
        ibpsCommonClas.add(ibpsCommonClass);
        firebase.push().child("").setValue(ibpsCommonClas);

    }

//    private void adddata(String s9,String s0,String s, String s1, String s2, String s3, String s4, String s5, String none, String s6) {
//        ModelSets modelSets = new ModelSets();
//        modelSets.setModelname(s);
//        modelSets.setQuestion(s1);
//        modelSets.setOpt1(s2);
//        modelSets.setOpt2(s3);
//        modelSets.setOpt3(s4);
//        modelSets.setOpt4(s5);
//        modelSets.setOpt5(none);
//        modelSets.setAnswer(s6);
//        firebase.child(s0).child(s).push().setValue(modelSets);
//    }


}
