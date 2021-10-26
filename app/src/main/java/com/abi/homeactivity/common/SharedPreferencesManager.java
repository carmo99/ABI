package com.abi.homeactivity.common;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreferencesManager {

    private static final String APP_SETTINGS_FILE = "APP_SETTINGS";

    private SharedPreferencesManager(){}

    private static SharedPreferences getSharedPreferences()
    {
        return MyApp.getContext()
                .getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    //UN VALOR DE STRING
    public static void setSomeStringValue( String dataLabel, String dataValue)
    {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(dataLabel, dataValue);
        editor.commit();
    }

    public static String getSomeStringValue( String dataLabel )
    {
        return getSharedPreferences().getString(dataLabel, null);
    }

    //LISTA DE STRINGS
    public static void setSomeArray( String dataLabel, List<String> list)
    {
        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String s : list)
        {
            sb.append(delim);
            sb.append(s);;
            delim = ",";
        }
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(dataLabel, sb.toString());
        editor.commit();
    }

    public static List<String> getSomeArray( String dataLabel )
    {
        String data = getSharedPreferences().getString(dataLabel, null);
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(data.split(",")));
        return list;
    }


}
