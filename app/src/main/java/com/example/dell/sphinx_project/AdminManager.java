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

public class AdminManager
{
    private String adminid,adminname,password,contact;

    public AdminManager(){}

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
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

    //insert Record

    public boolean insertRecord(Context context) throws Exception
    {
        String qry = "INSERT INTO adminmaster VALUES( '"+adminid+"' ,'"+adminname+"'  , '"+password+"' , '"+contact+"' )";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // update Record

    public boolean updateRecord(Context context) throws Exception
    {
        String qry = "UPDATE FROM adminmaster set adminname='"+adminname+"',password='"+password+"',contact='"+contact+"' WHERE adminid='"+adminid+"'";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // delete Record

    public boolean deleteRecord(Context context) throws Exception
    {
        String qry = "DELETE adminmaster WHERE adminid='"+adminid+"'";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;

    }

    // get ALl Records

    public static List<AdminManager> getRecords(Context context,String qry)
    {
        List<AdminManager> list = new ArrayList<AdminManager>();
        try{
            JSONArray arr = DataManager.executeQuery(context, Common.getUrl(), qry);
            if( arr != null )
            {
                for(int i = 0;i<arr.length(); i++)
                {
                    AdminManager row = new AdminManager();
                    JSONObject object = arr.getJSONObject(i);
                    row.setAdminid(object.getString("adminid"));
                    row.setAdminname(object.getString("adminname"));
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
        List<AdminManager> list = AdminManager.getRecords(context, "SELECT * FROM adminmaster");
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getAdminid().equals(id) && list.get(i).getPassword().equals(pwd) )
            {
                return true;
            }
        }
        return false;
    }

}
