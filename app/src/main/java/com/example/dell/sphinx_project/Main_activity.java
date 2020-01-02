package com.example.dell.sphinx_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main_activity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_activity);
        ActionBar action = getSupportActionBar();
        action.hide();
        Thread loading = new Thread()
        {
            public void run() {
                try
                {
                    sleep(3000);
                    Intent i = new Intent(Main_activity.this, login_activity.class);
                    startActivity(i);
                    finish();
//                    SharedPreferences pref = getSharedPreferences("TIME",MODE_PRIVATE);
//                    String s = pref.getString("show","true");
//                    if(s.equals("true")) {
//                        Intent i = new Intent(Main_activity.this, login_activity.class);
//                        startActivity(i);
//                        finish();
//                    }
//                    else
//                    {
//                        Intent i = new Intent(Main_activity.this, LoginActivity.class);
//                        startActivity(i);
//                        finish();
//                    }
                }
                catch (Exception ex)
                {
                    finish();
                }
            }
        };
        loading.start();
    }
}
