package com.anvisys.nestinguard.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private  static String SessionName = "NestinGate";

    public static boolean AddUser(Context context, Profile myProfile)
    {
        try {
            SharedPreferences prefs = context.getSharedPreferences(SessionName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("UserID", myProfile.UserID);
            editor.putString("FirstName",myProfile.NAME);
            editor.putString("Email", myProfile.E_MAIL);
            editor.putString("MobileNumber", myProfile.MOB_NUMBER);
            editor.putString("Password",myProfile.password);
            editor.putString("Loation",myProfile.LOCATION);
            editor.putString("RegistrationID",myProfile.REG_ID);
            editor.putString("Address",myProfile.Address);
            editor.putString("Gender",myProfile.Gender);
            editor.putString("ParentName",myProfile.ParentName);
            editor.commit();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public static Profile GetUser(Context context)
    {
        Profile mProfile = new Profile();
        try {
            SharedPreferences prefs = context.getSharedPreferences(SessionName, Context.MODE_PRIVATE);
            mProfile.UserID =  prefs.getString("UserID","");
            mProfile.NAME = prefs.getString("FirstName","");
            mProfile.E_MAIL =  prefs.getString("Email","");
            mProfile.MOB_NUMBER =  prefs.getString("MobileNumber","");
            mProfile.password = prefs.getString("Password","");
            mProfile.LOCATION = prefs.getString("Loation","");
            mProfile.REG_ID = prefs.getString("RegistrationID","");
            mProfile.Address= prefs.getString("Address","");
            mProfile.Gender = prefs.getString("Gender","");
            mProfile.ParentName= prefs.getString("ParentName","");
            mProfile.strImage= prefs.getString("strImage","");
            return mProfile;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static void LogOff(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(SessionName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public static boolean AddSociety(Context context, Society society)
    {
        try {
            SharedPreferences prefs = context.getSharedPreferences(SessionName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("SocietyId", society.SocietyId);
            editor.putString("SocietyName",society.SocietyName);
            editor.putString("SocietyCity", society.SocietyCity);

            editor.commit();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public static Society GetSociety(Context context)
    {
        Society society = new Society();
        try {
            SharedPreferences prefs = context.getSharedPreferences(SessionName, Context.MODE_PRIVATE);
            society.SocietyId =  prefs.getInt("SocietyId",0);
            society.SocietyName = prefs.getString("SocietyName","");
            society.SocietyCity =  prefs.getString("SocietyCity","");

            return society;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}
