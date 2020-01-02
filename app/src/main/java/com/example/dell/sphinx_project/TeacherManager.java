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

public class TeacherManager
{
    private String teacherid,tname,gender,password,contact;

    public TeacherManager(){}

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    // insert Record

    public boolean insertRecord(Context context) throws Exception
    {
        String qry = "INSERT INTO teachermaster VALUES( '"+teacherid+"' ,'"+tname+"' , '"+gender+"' , '"+password+"' , '"+contact+"' )";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // update Record

    public boolean updateRecord(Context context) throws Exception
    {
        String qry = "UPDATE teachermaster set tname='"+tname+"',gender='"+gender+"',password='"+password+"' , contact='"+contact+"' WHERE teacherid='"+teacherid+"'";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // delete Record

    public boolean deleteRecord(Context context) throws Exception
    {
        String qry = "DELETE FROM teachermaster WHERE teacherid='"+teacherid+"'";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;

    }

    // get ALl Records

    public static List<TeacherManager> getRecords(Context context,String qry)
    {
        List<TeacherManager> list = new ArrayList<TeacherManager>();
        try{
            JSONArray arr = DataManager.executeQuery(context, Common.getUrl(), qry);
            if( arr != null )
            {
                for(int i = 0;i<arr.length(); i++)
                {
                    TeacherManager row = new TeacherManager();
                    JSONObject object = arr.getJSONObject(i);
                    row.setTeacherid(object.getString("teacherid"));
                    row.setTname(object.getString("tname"));
                    row.setGender(object.getString("gender"));
                    row.setPassword(object.getString("password"));
                    row.setContact(object.getString("contact"));
                    list.add(row);
                }
                return list;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(context,"Unable To Retrive Data || Not Found ",Toast.LENGTH_LONG).show();
            return null;
        }
    }

    // validate

    public static boolean validate(Context context,String id,String pwd) throws Exception
    {
        List<TeacherManager> list = TeacherManager.getRecords(context, "SELECT * FROM teachermaster");

        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getTeacherid().equals(id) && list.get(i).getPassword().equals(pwd) )
            {
                return true;
            }
        }

        return false;
    }
}
