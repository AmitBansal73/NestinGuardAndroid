package com.example.nestinguard.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utility {

    static final String INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    static final String DATE_TIME_Display_FORMAT = "dd,MMM' @ 'hh:mm a";

    public static Date DBStringToLocalDate(String date)
    {
        try {
            SimpleDateFormat idf = new SimpleDateFormat(INPUT_DATE_FORMAT);
            Date dateTime = idf.parse(date);
            Date localDate = new Date(dateTime.getTime() + TimeZone.getDefault().getRawOffset());

            return localDate;
        }
        catch (Exception ex)
        {
            return new Date();
        }

    }

    public static String DateToDisplayDateTime(Date date)
    {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_Display_FORMAT);

            String LocalTime = sdf.format(date);

            return  LocalTime;
        }
        catch (Exception ex)
        {
            return "";
        }
    }


    public  static  int GetYearOnly(Date date)
    {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

            String year = sdf.format(date);
            int intYear = Integer.parseInt(year);
            return  intYear;
        }
        catch (Exception ex)
        {
            return 2000;
        }
    }

    public static boolean IsConnected(Context context)
    {
        boolean isConnected = false;
        try
        {
            NetworkInfo networkInfo=null;

            ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = connManager.getActiveNetworkInfo();
            if(networkInfo!= null)
            {
                isConnected = true;
            }
            else
            {
                isConnected = false;
            }

            return  isConnected;
        }
        catch ( Exception ex)

        {
            return isConnected;
        }
    }
}
