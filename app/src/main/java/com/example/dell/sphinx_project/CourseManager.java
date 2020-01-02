package com.example.dell.sphinx_project;

/**
 * Created by Dell on 16-01-2018.
 */
import java.util.ArrayList;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

public class CourseManager
{
    private String courseid,coursename,duration;
    private int fee,intake;

    public CourseManager(){}

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getIntake() {
        return intake;
    }

    public void setIntake(int intake) {
        this.intake = intake;
    }

    //insert Record

    public boolean insertRecord(Context context) throws Exception
    {
        String qry = "INSERT INTO coursemaster VALUES('"+courseid+"','"+coursename+"','"+duration+"',"+fee+","+intake+")";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // update Record

    public boolean updateRecord(Context context) throws Exception
    {
        String qry = "UPDATE coursemaster set coursename='"+coursename+"',duration='"+duration+"',fee="+fee+",intake="+intake+" WHERE courseidid='"+courseid+"'";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // delete Record

    public boolean deleteRecord(Context context) throws Exception
    {
        String qry = "DELETE FROM coursemaster WHERE courseid='"+courseid+"'";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // get All Record

    public static List<CourseManager> getRecords(Context context,String qry)
    {
        List<CourseManager> list = new ArrayList<CourseManager>();
        try{
            JSONArray arr = DataManager.executeQuery(context, Common.getUrl(), qry);
            if( arr != null )
            {
                for(int i=0;i<arr.length();i++)
                {
                    CourseManager row = new CourseManager();
                    JSONObject object = arr.getJSONObject(i);
                    row.setCourseid(object.getString("courseid"));
                    row.setCoursename(object.getString("Coursename"));
                    row.setDuration(object.getString("duration"));
                    row.setFee(object.getInt("fee"));
                    row.setIntake(object.getInt("intake"));
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

