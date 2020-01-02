package com.example.dell.sphinx_project;

/**
 * Created by Dell on 15-01-2018.
 */
import android.content.Context;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class StudentManager
{
    private String rollno,sname,fname,gender,password,contact,courseid,batchid;

    public StudentManager(){}

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
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

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    // insert Record

    public boolean insertRecord(Context context) throws Exception
    {
        String qry = "INSERT INTO studentmaster VALUES( '"+rollno+"' , '"+sname+"' , '"+fname+"' , '"+gender+"' ,'"+password+"' , '"+contact+"' , '"+courseid+"' , '"+batchid+"')";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // update Record

    public boolean updateRecord(Context context) throws Exception
    {
        String qry = "UPDATE studentmaster set sname='"+sname+"' , fanme='"+fname+"', gender='"+gender+"', password='"+password+"' ,contact='"+contact+"',courseid='"+courseid+"',batchid='"+batchid+"' WHERE rollno='"+rollno+"'";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    //delete Record

    public boolean deleteRecord(Context context) throws Exception
    {
        String qry = "DELETE FROM studentmaster WHERE rollno='rollno'";
        if(DataManager.executeUpdate(context, Common.getUrl(), qry))
            return true;
        else
            return false;
    }

    // get All Records

    public static List<StudentManager> getRecords(Context context,String qry)
    {
        List<StudentManager> list = new ArrayList<StudentManager>();
        try
        {
            JSONArray arr =  DataManager.executeQuery(context, Common.getUrl(),qry);
            if( arr != null&&arr.length()!=0 )
            {
                for(int i=0;i<arr.length();i++)
                {
                    JSONObject object = arr.getJSONObject(i);
                    StudentManager row = new StudentManager();
                    row.setRollno(object.getString("rollno"));
                    row.setSname(object.getString("sname"));
                    row.setFname(object.getString("fname"));
                    row.setGender(object.getString("gender"));
                    row.setContact(object.getString("contact"));
                    row.setCourseid(object.getString("courseid"));
                    row.setBatchid(object.getString("batchid"));
                    list.add(row);
                }
                return list;
            }
            else{
                return null;
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(context,"Unable To Retrive Data || No Data",Toast.LENGTH_LONG).show();
            return null;
        }
    }

    // validate

    public static boolean validate(Context context, String id, String pwd) throws Exception
    {
        List<StudentManager> list = StudentManager.getRecords(context, "SELECT * FROM studentmaster where rollno='"+id+"' and password='"+pwd+"'");
        if(list!=null&&list.size()!=0)
            return true;
        else
            return false;
//		for(int i=0;i<list.size();i++)
//		{
//			if(list.get(i).getRollno().equals(id) && list.get(i).getPassword().equals(pwd) )
//			{
//				return true;
//			}
//		}
        //return false;
    }
}
