package com.example.dell.sphinx_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class t_Attend extends AppCompatActivity
{
    Button btn1,btn2,btn3,btn4,back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t__attend);
        btn1=(Button) findViewById(R.id.button6);
        btn2=(Button) findViewById(R.id.button3);
        btn3=(Button) findViewById(R.id.button4);
        btn4=(Button) findViewById(R.id.button5);
        back=(Button) findViewById(R.id.button7);

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),takeAttend.class);
                i.putExtra("session", "2014");
                startActivity(i);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),takeAttend.class);
                i.putExtra("session", "2015");
                startActivity(i);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),takeAttend.class);
                i.putExtra("session", "2016");
                startActivity(i);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),takeAttend.class);
                i.putExtra("session", "2017");
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),teacherActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

}

