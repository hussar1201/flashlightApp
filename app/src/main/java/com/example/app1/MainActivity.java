package com.example.app1;

import android.content.Context;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    SensorManager manager;
    boolean flag_SOS   = false;
    boolean flag_Light   = false;
    Thread th;
    Switch sw1,sw2;

    void On()
    {
        flag_Light = true;
        if(Build.VERSION.SDK_INT>=23)
        {
            CameraManager cam = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
            try
            {
                String id = cam.getCameraIdList()[0];
                cam.setTorchMode(id,true);

            }
            catch(Exception e)
            { }
        }
    }

    void Off()
    {
        flag_Light=false;
        if(Build.VERSION.SDK_INT>=23)
        {
            CameraManager cam = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
            try
            {
                String id = cam.getCameraIdList()[0];
                cam.setTorchMode(id,false);
            }
            catch(Exception e)
            {  }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img=(ImageView) findViewById(R.id.imageView);
        sw1 = (Switch)findViewById(R.id.sw1);
        sw2 = (Switch)findViewById(R.id.sw2);

        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_Light) {
                    img.setImageResource(R.drawable.bulboff);
                    Off();
                }
                else {
                    On();
                    img.setImageResource(R.drawable.bulbon);
                }
            }
        });


        sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickBtnSOS();
            }
        });
    }

    public void OnClickBtnSOS()
    {
        Off();
        if(flag_SOS) {
            flag_SOS= false;
            img.setImageResource(R.drawable.bulboff);
            if(th!=null) {
                th.interrupt();
                Off();
            }
        }
        else {
            flag_SOS= true;
            img.setImageResource(R.drawable.alert);
        }

            Log.e("TAG", "SOS On");

            //Thread Code Start
             th = new Thread(new Runnable()
             {
                  @Override
                  public void run() {
                      while(true)
                        {
                            if(!flag_SOS) break;
                            try {
                                for(int i= 0;i<3;i++)
                                {
                                    On();
                                    Thread.sleep(100);
                                    Off();
                                    Thread.sleep(200);
                                }
                                for(int i= 0;i<3;i++)
                                {
                                    On();
                                    Thread.sleep(1000);
                                    Off();
                                    Thread.sleep(200);
                                }
                                for(int i= 0;i<3;i++)
                                {
                                    On();
                                    Thread.sleep(100);
                                    Off();
                                    Thread.sleep(200);
                                }
                                Thread.sleep(1000);
                            }catch(Exception e)
                            {
                                break;
                            }
                        }
                  }
              });
             th.start();
             //Thread Code End
    }

}













