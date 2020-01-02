package com.example.dell.sphinx_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FunctionBatchActivity extends AppCompatActivity {

    TextView tv1;
    ListView lv1;
    String bid,cid,sid;
    List<StudentManager> list;
    List<AttendanceManager> attendList;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_batch);

        Intent i = getIntent();
        bid = i.getStringExtra("bid");
        cid = i.getStringExtra("cid");
        sid = i.getStringExtra("sid");

        lv1 = (ListView) findViewById(R.id.listView1);

        submit = (Button) findViewById(R.id.Submit);
        list = StudentManager.getRecords(getApplicationContext(), "SELECT * FROM studentmaster WHERE batchid = '"+bid+"' AND courseid='"+cid+"'");




        if( list == null || list.size()== 0)
        {
            Toast.makeText(getApplicationContext(), "No Student", Toast.LENGTH_LONG).show();
            return;
        }

        attendList = new ArrayList<AttendanceManager>();

        for(int j=0;j<list.size();j++)
        {
            AttendanceManager row = new AttendanceManager();
            row.setRollno(list.get(j).getRollno());
            row.setAtt_status("P");
            row.setSubjectid(sid);
            row.setAtt_date(Common.getYyyymmdd(new java.util.Date()));
            attendList.add(row);
        }

        StudListAdapter aa = new StudListAdapter(getApplicationContext(), R.layout.studlist, list,attendList);
        lv1.setAdapter(aa);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try{
                    List<AttendanceManager> list = AttendanceManager.getRecords(getApplicationContext(), "SELECT * FROM attendancemaster WHERE att_date = '"+Common.getYyyymmdd(new java.util.Date())+"' ");
                    if(list != null || list.size() >0 )
                    {
                        if(DataManager.executeUpdate(getApplicationContext(), Common.getUrl(), "DELETE FROM attendancemaster WHERE att_date = '"+Common.getYyyymmdd(new java.util.Date())+"' AND subjectid='"+sid+"' "))
                        {
                            for(int i=0;i<attendList.size();i++)
                            {
                                AttendanceManager row = new AttendanceManager();
                                row.setAtt_date(Common.getYyyymmdd(new java.util.Date()));
                                row.setAtt_status(attendList.get(i).getAtt_status());
                                row.setRollno(attendList.get(i).getRollno());
                                row.setSubjectid(attendList.get(i).getSubjectid());
                                if(row.insertRecord(getApplicationContext()))
                                {
                                    Toast.makeText(getApplicationContext(), "Attendance Successfull", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Attendance UnSuccessfull", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                    else
                    {
                        for(int i=0;i<attendList.size();i++)
                        {
                            AttendanceManager row = new AttendanceManager();
                            row.setAtt_date(Common.getYyyymmdd(new java.util.Date()));
                            row.setAtt_status(attendList.get(i).getAtt_status());
                            row.setRollno(attendList.get(i).getRollno());
                            row.setSubjectid(attendList.get(i).getSubjectid());
                            if(row.insertRecord(getApplicationContext()))
                            {
                                Toast.makeText(getApplicationContext(), "Attendance Successfull", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Attendance UnSuccessfull", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
                catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
