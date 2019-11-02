package com.example.user.healthsupervisor;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.System.currentTimeMillis;


/**
 * A simple {@link Fragment} subclass.
 */
public class StpFragment extends Fragment {

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
    private View stpview;


    public StpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        stpview = inflater.inflate(R.layout.fragment_stp, container, false);
        super.onCreate(savedInstanceState);
       // stpview.(R.layout.fragment_stp);
        //return stpview;

        tv_time = (TextView) stpview.findViewById(R.id.tv_time);
        imageViewPlay = (ImageView) stpview.findViewById(R.id.imageViewPlay);
        imageViewLap = (ImageView) stpview.findViewById(R.id.imageViewLap);
        imageViewSecond = (ImageView) stpview.findViewById(R.id.imageViewSecond);

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
                    tv_time.setText(String.format("%02d:%02d:%03d", 0, 0, 0));
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
                    laps = laps + String.valueOf(lapscount) + ". " + tv_time.getText().toString() + "\n";
                }else{
                    if(laps.equalsIgnoreCase(""))
                    {
                        Toast.makeText(getActivity(),"Empty!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
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

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_stp, container, false);
        return stpview;
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
                long millis = (System.currentTimeMillis()- start_time);
                long seconds = millis/1000;
                tv_time.setText(String.format("%02d:%02d:%03d", seconds/60, seconds%60, millis%100));

                rotate(old_degree, millis*3/500);
                mHandler.postDelayed(mRunnable,10L);
                old_degree = millis*3/500;
            }
        }
    };

}
