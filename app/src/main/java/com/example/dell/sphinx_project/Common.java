package com.example.dell.sphinx_project;

/**
 * Created by Dell on 15-01-2018.
 */


import java.util.Calendar;
//http://nistkota.com/QueryProcessor_collegedb.php

public class Common
{
    public static String serverip;
    public static String getUrl()
    {
        return "http://zine.co.in/Query_Processor.php";
    }

//    public static String getDdmmyyyy(java.util.Date date)
//    {
//        String str = "";
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(date.getTime());
//        str = cal.get(Calendar.DATE)+"/"+cal.get(Calendar.MONTH+1)+"/"+cal.get(Calendar.YEAR);
//        return str;
//    }
//
    public static String getYyyymmdd(java.util.Date dt)
    {
        String str="";
        java.util.Calendar cal=java.util.Calendar.getInstance();
        cal.setTimeInMillis(dt.getTime());
        str=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE);
        return str;
    }
}

