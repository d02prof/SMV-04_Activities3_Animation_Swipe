package com.smv.activities3_slide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class Activity_1 extends AppCompatActivity
{
    private Button buttonOpenActivity2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        setTitle("Activity 1");
        buttonOpenActivity2 = findViewById(R.id.buttonOpenActivity2);
        buttonOpenActivity2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenActivity2();
            }
        });
    }

    public void OpenActivity2()
    {
        Intent intent = new Intent(Activity_1.this, Activity_2.class);
        startActivity(intent);

        //ANIMIRANI PREHODI MED AKTIVNOSTMI
        //da dobimo animiran prehod, moramo takoj za odpiranjem aktivnosti poklicati overridePendingTransition
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        //zgornjo kodo beremo kot pojdi v desno iz leve
    }

    //koda za swipe v naslednjo aktivnost
    float x1, x2;

    public boolean onTouchEvent(MotionEvent event)
    {
        //return super.onTouchEvent(event);

        //x1 in x2 sta definirana v razredu, če sta lokalni spremenljivki, ne dela ok
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                Intent intent;
                if (x1 > x2)
                {
                    intent = new Intent(getApplicationContext(), Activity_2.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
        return true;
    }
}