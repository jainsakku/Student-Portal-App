package com.example.dell.sphinx_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class takeAttend extends AppCompatActivity
{
    TextView tv1,tv2;
    String session;
    ListView lv1;
    List<SubjectManager> list;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attend);
        SharedPreferences pref = getSharedPreferences("User", MODE_PRIVATE);
        String s = pref.getString("user", "n");
        String s1 = pref.getString("id", "n");
        String s2 = pref.getString("pwd", "n");

        tv2 = (TextView) findViewById(R.id.no);
        tv1 = (TextView) findViewById(R.id.session);
        Intent i =	getIntent();
        session = i.getStringExtra("session");
        tv1.setText(session);

        lv1  = (ListView) findViewById(R.id.subjectlist);
        list = SubjectManager.getRecords(getApplicationContext(), "SELECT * FROM subjectmaster WHERE teacherid = '"+s1+"' AND batchid IN(SELECT batchid FROM batchmaster WHERE session ='"+session+"')");

        if( list == null || list.size()==0)
        {
            tv2.setText("NO Subjects");
            return;
        }

        SubAdapter aa = new SubAdapter(getApplicationContext(), R.layout.teachersubjectview, list);
        lv1.setAdapter(aa);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent i = new Intent(getApplicationContext(),FunctionBatchActivity.class);
                i.putExtra("bid", list.get(position).getBatchid());
                i.putExtra("cid",list.get(position).getCourseid());
                i.putExtra("sid",list.get(position).getSubjectid());
                startActivity(i);
            }
        });
    }
}
