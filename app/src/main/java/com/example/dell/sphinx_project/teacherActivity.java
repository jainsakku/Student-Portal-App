package com.example.dell.sphinx_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class teacherActivity extends AppCompatActivity {

    TextView profile;
    Button quiz,attend,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        profile=(TextView) findViewById(R.id.profile);
        quiz=(Button) findViewById(R.id.button2);
        attend=(Button) findViewById(R.id.button);
        logout=(Button) findViewById(R.id.logout);

        SharedPreferences pref = getSharedPreferences("User", MODE_PRIVATE);
        String s = pref.getString("user", "n");
        String s1 = pref.getString("id", "n");
        String s2 = pref.getString("pwd", "n");
        List<TeacherManager> list = TeacherManager.getRecords(getApplicationContext(), "SELECT * FROM teachermaster WHERE teacherid='"+s1+"'");
        profile.setText(list.get(0).getTname());

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),t_Attend.class);
                startActivity(i);
            }
        });

        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),t_Attend.class);
                startActivity(i);
            }
        });

    }
}
