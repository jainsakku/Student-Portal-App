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

public class BatchManager
{
    private String batchid,session,courseid;

    public BatchManager(){}

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
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
        String qry = "INSERT INTO batchmaster VALUES('"+batchid+"','"+session+"','"+courseid+"')";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // update Record

    public boolean updateRecord(Context context) throws Exception
    {
        String qry = "UPDATE batchmaster set session='"+session+"',courseid='"+courseid+"' WHERE batchid='"+batchid+"'";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // delete Record

    public boolean deleteRecord(Context context) throws Exception
    {
        String qry = "DELETE FROM batchmaster WHERE batchid='"+batchid+"'";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // get All Record

    public static List<BatchManager> getRecords(Context context,String qry)
    {
        List<BatchManager> list = new ArrayList<BatchManager>();
        try{
            JSONArray arr = DataManager.executeQuery(context, Common.getUrl(), qry);
            if( arr != null )
            {
                for(int i=0;i<arr.length();i++)
                {
                    BatchManager row = new BatchManager();
                    JSONObject object = arr.getJSONObject(i);
                    row.setBatchid(object.getString("batchid"));
                    row.setSession(object.getString("session"));
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
