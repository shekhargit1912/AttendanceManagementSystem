package com.example.tuitioproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScren extends AppCompatActivity {
    String std,div;
    private GifImageView gifImageView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scren);
        gifImageView = (GifImageView)findViewById(R.id.gifImageView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(progressBar.VISIBLE);
        try{
            InputStream inputStream = getAssets().open("school.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }


        Timer timer=new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (new SharedPref(SplashScren.this).getName().equals("staff"))
                {
                    std=new SharedPref(SplashScren.this).getStd().toString();
                    div=new SharedPref(SplashScren.this).getDiv().toString();
                    Intent intent = new Intent(SplashScren.this, StaffAccess.class);
                    intent.putExtra("std", std);
                    intent.putExtra("div", div);
                    startActivity(intent);
                    finish();
                }
                else if (new SharedPref(SplashScren.this).getName().equals("admin"))
                {
                    Intent intent = new Intent(SplashScren.this, Add.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(SplashScren.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },5000);
    }
}
