package com.bluerizon.transport.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Helpers {

    public static String currentDate(){
//        DateFormat date = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, new Locale("FR","fr"));
        Date date = new Date();
        SimpleDateFormat datej = new SimpleDateFormat("yyyy-MM-dd");
        String datejour = datej.format(date);
//        SimpleDateFormat heure = new SimpleDateFormat("HH:mm:ss");
//        String heurejour = heure.format(date);
//        System.out.println(datejour+"-"+heurejour);
        return datejour;
    }

    public static String convertDate(Date date){
//        DateFormat date = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, new Locale("FR","fr"));
//        Date date = new Date();
        SimpleDateFormat datej = new SimpleDateFormat("yyyy-MM-dd");
        String datejour = datej.format(date);
//        SimpleDateFormat heure = new SimpleDateFormat("HH:mm:ss");
//        String heurejour = heure.format(date);
//        System.out.println(datejour+"-"+heurejour);
        return datejour;
    }

    public static Date convertDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return formatter.parse(date);
    }

    public static String generatRef(String classe, Long lastId){
//        DateFormat date = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, new Locale("FR","fr"));
        Date date = new Date();
        SimpleDateFormat datej = new SimpleDateFormat("yyyy-MM-dd");
        String datejour = datej.format(date);
        SimpleDateFormat heure = new SimpleDateFormat("HH:mm:ss");
        String heurejour = heure.format(date);
        System.out.println(datejour+"-"+heurejour);
        return classe.substring(0, 3).toUpperCase()+"-"+lastId+"-"+datejour+"-"+heurejour;
    }

    public static String generatRef(String classe, Long lastId, Long idB){
//        DateFormat date = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, new Locale("FR","fr"));
        Date date = new Date();
        SimpleDateFormat datej = new SimpleDateFormat("yyyy-MM-dd");
        String datejour = datej.format(date);
//        SimpleDateFormat heure = new SimpleDateFormat("HH:mm:ss");
//        String heurejour = heure.format(date);
//        System.out.println(datejour+"-"+heurejour);
        return classe.substring(0, 3).toUpperCase()+"-"+lastId+"-"+datejour+"-"+idB;
    }

    public static String generat(){
        Date date = new Date();
        SimpleDateFormat datej = new SimpleDateFormat("yyyy_MM_dd");
        String datejour = datej.format(date);
        SimpleDateFormat heure = new SimpleDateFormat("HH_mm_ss");
        String heurejour = heure.format(date);
        Calendar calendar = Calendar.getInstance();
        return "AR"+datejour+"_"+heurejour+"_"+calendar.getTimeInMillis();
    }

}
