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

public class SubjectManager
{
    private String subjectid,subjectname,batchid,teacherid,courseid;

    public SubjectManager(){}

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    //insert Record

    public boolean insertRecord(Context context) throws Exception
    {
        String qry = "INSERT INTO subjectmaster VALUES('"+subjectid+"','"+subjectname+"','"+batchid+"','"+teacherid+"','"+courseid+"')";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // update Record

    public boolean updateRecord(Context context) throws Exception
    {
        String qry = "UPDATE subjectmaster set subjectname='"+subjectname+"',batchid='"+batchid+"',teacherid='"+teacherid+"',courseid='"+courseid+"' WHERE subjectid='"+subjectid+"'";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // delete Record

    public boolean deleteRecord(Context context) throws Exception
    {
        String qry = "DELETE FROM subjectmaster WHERE subjectid='"+subjectid+"'";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // get All Record

    public static List<SubjectManager> getRecords(Context context,String qry)
    {
        List<SubjectManager> list = new ArrayList<SubjectManager>();
        try{
            JSONArray arr = DataManager.executeQuery(context, Common.getUrl(), qry);
            if( arr != null )
            {
                for(int i=0;i<arr.length();i++)
                {
                    SubjectManager row = new SubjectManager();
                    JSONObject object = arr.getJSONObject(i);
                    row.setSubjectid(object.getString("subjectid"));
                    row.setSubjectname(object.getString("subjectname"));
                    row.setBatchid(object.getString("batchid"));
                    row.setTeacherid(object.getString("teacherid"));
                    row.setCourseid(object.getString("courseid"));
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
