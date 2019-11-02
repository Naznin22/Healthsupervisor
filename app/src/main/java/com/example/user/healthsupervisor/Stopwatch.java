package com.example.user.healthsupervisor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

//import java.util.logging.Handler;
import android.os.Handler;
import android.widget.Toast;

import java.util.logging.LogRecord;

import static java.lang.System.currentTimeMillis;

/**
 * Created by USER on 11/24/2017.
 */

public class Stopwatch extends AppCompatActivity {
    TextView tv_time;
    ImageView imageViewPlay, imageViewLap;
    ImageView imageViewSecond;
    int buttonState;
    String laps;
    int lapscount;

    private Handler mHandler;
    private boolean mStarted;

    long start_time;
    long old_degree;

    @Override
    protected void onCreate(Bundle savedInstanceState)
   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabstopwatch);

        tv_time = (TextView) findViewById(R.id.tv_time);
        imageViewPlay = (ImageView) findViewById(R.id.imageViewPlay);
        imageViewLap = (ImageView) findViewById(R.id.imageViewLap);
        imageViewSecond = (ImageView) findViewById(R.id.imageViewSecond);

       mHandler = new Handler() ;


       imageViewPlay.setEnabled(true);
       buttonState = 1;
       laps="";
       lapscount=0;

       imageViewPlay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(buttonState == 1)
               {
                    mStarted=true;

                   mHandler.postDelayed(mRunnable,10L);
                   start_time = currentTimeMillis();
                   laps="";

                   imageViewPlay.setImageResource(R.drawable.pause);
                   buttonState = 2;
               }
               else if(buttonState == 2)
               {
                   mStarted=false;
                   mHandler.removeCallbacks(mRunnable);
                   imageViewPlay.setImageResource(R.drawable.reload);
                   buttonState=3;
               }
               else if(buttonState == 3){
                   rotate(old_degree,360);
                   old_degree=0;
                   tv_time.setText(String.format("%02d:%02d%02d", 0, 0, 0));
                    laps="";
                    lapscount=0;

                    imageViewPlay.setImageResource(R.drawable.play);
                    buttonState=1;
               }
           }
       });

       imageViewLap.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(mStarted)
               {
                   lapscount++;
                   laps = laps+String.valueOf(lapscount)+". "+tv_time.getText().toString()+"\n";
               }else{
                   if(laps.equalsIgnoreCase(""))
                   {
                       Toast.makeText(Stopwatch.this,"Empty!",Toast.LENGTH_SHORT).show();
                   }
                   else
                   {
                       AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Stopwatch.this);
                       alertDialogBuilder.setMessage(laps);
                       alertDialogBuilder.setCancelable(true);
                       alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int which) {
                               dialogInterface.dismiss();
                           }
                       });

                       AlertDialog alertDialog = alertDialogBuilder.create();
                       alertDialog.show();
                   }
               }

           }
       });

   }

    private void rotate(float from_degree,float to_degree)
    {
        RotateAnimation rotateAnimation = new RotateAnimation(from_degree,to_degree,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);
        imageViewSecond.startAnimation(rotateAnimation);
    }

    private  final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mStarted){
                long millis = System.currentTimeMillis();
                long seconds = millis/1000;
                tv_time.setText(String.format("%02d:%02d%02d", seconds/60, seconds%60, millis%100));

                rotate(old_degree, millis*3/500);
                mHandler.postDelayed(mRunnable,10L);
                old_degree = millis*3/500;
            }
        }
    };

}
