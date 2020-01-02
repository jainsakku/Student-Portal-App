package com.example.dell.sphinx_project;

/**
 * Created by Dell on 15-01-2018.
 */

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.widget.Toast;


public class AttendanceManager
{
    private String rollno,subjectid,att_date,att_status;

    public AttendanceManager(){}

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getAtt_date() {
        return att_date;
    }

    public void setAtt_date(String att_date) {
        this.att_date = att_date;
    }

    public String getAtt_status() {
        return att_status;
    }

    public void setAtt_status(String att_status) {
        this.att_status = att_status;
    }

    //insert Record

    public boolean insertRecord(Context context) throws Exception
    {
        String qry = "INSERT INTO attendancemaster VALUES('"+rollno+"','"+subjectid+"','"+att_date+"','"+att_status+"')";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // get All Record

    public static List<AttendanceManager> getRecords(Context context,String qry)
    {
        List<AttendanceManager> list = new ArrayList<AttendanceManager>();
        try{
            JSONArray arr = DataManager.executeQuery(context, Common.getUrl(), qry);
            if( arr != null )
            {
                for(int i=0;i<arr.length();i++)
                {
                    AttendanceManager row = new AttendanceManager();
                    JSONObject object = arr.getJSONObject(i);
                    row.setRollno(object.getString("rollno"));
                    row.setSubjectid(object.getString("subjectid"));
                    row.setAtt_date(object.getString("att_date"));
                    row.setAtt_status(object.getString("att_status"));
                    list.add(row);
                }
                return list;
            }
            else
            {
                return null;
            }
        }catch (Exception e) {
            // when array is there but have no data
            Toast.makeText(context, "Unable to retrive data || Not Found", Toast.LENGTH_LONG).show();
            // TODO: handle exception
            return null;
        }
    }
}
