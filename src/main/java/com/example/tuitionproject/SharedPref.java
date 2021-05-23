package com.example.tuitioproject;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    Context context;
    private String name;
    private String std;
    private String div;
    SharedPreferences sharedPreferences;
    public SharedPref(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
    }

    public String getStd() {
        std=sharedPreferences.getString("std","");
        return std;
    }

    public void setStd(String std) {
        this.std = std;
        sharedPreferences.edit().putString("std",std).commit();
    }

    public String getDiv() {
        div=sharedPreferences.getString("div","");
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
        sharedPreferences.edit().putString("div",div).commit();
    }

    public String getName() {
        name=sharedPreferences.getString("name","");
        return name;

    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("name",name).commit();
    }
    public void removeUser()
    {
        sharedPreferences.edit().clear().commit();
    }
}
