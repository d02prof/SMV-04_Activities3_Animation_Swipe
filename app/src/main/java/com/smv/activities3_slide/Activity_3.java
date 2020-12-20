package com.smv.activities3_slide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_3 extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        setTitle("Activity 3");

        //preberemo Intent
        Intent intent = getIntent();

        //če želimo, lahko preverimo, ali je kaj v parametrih
        if(intent.hasExtra("EXTRA_STRING"))
        {
            //preberemo parameter
            String textRead = intent.getStringExtra("EXTRA_STRING");
            //komponente iz xml so lahko povezane z lokalnimi spremenljivkami
            TextView text = findViewById(R.id.textVievText);
            text.setText(textRead);
        }

        //če želimo, lahko preverimo, ali je kaj v parametrih
        //if(intent.hasExtra("EXTRA_NUMBER"))
        {
            //preberemo parameter
            int numberRead = intent.getIntExtra("EXTRA_NUMBER",0);
            //komponente iz xml so lahko povezane z lokalnimi spremenljivkami
            TextView number = findViewById(R.id.textViewNumber);
            number.setText("" + numberRead);
        }

        //del za vračanje rezultata v prejšnji aktivnost
        Button buttonAdd, buttonSubtract;
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonSubtract = findViewById(R.id.buttonSubtract);
        buttonAdd.setOnClickListener(BothButtons_Click);
        buttonSubtract.setOnClickListener(BothButtons_Click);

    }

    //del za vračanje rezultata v prejšnji aktivnost
    public View.OnClickListener BothButtons_Click = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            TextView number = findViewById(R.id.textViewNumber);
            int no1 = Integer.parseInt(number.getText().toString());
            int result = 0;
            //preverimo, kateri gumb je bil kliknjen (v C# bi uporabili sender)
            if (v == findViewById(R.id.buttonAdd)) result = no1 + 1;
            else if(v == findViewById(R.id.buttonSubtract)) result = no1 - 1;
            //Toast.makeText(Activity_3.this, "Result = " + result, Toast.LENGTH_SHORT).show();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("RESULT", result);
            setResult(RESULT_OK, resultIntent);

            //zapremo (uničimo) aktivnost
            finish();
        }
    };

    //ANIMIRANI PREHODI MED AKTIVNOSTMI

    //da dobimo animiran prehod za HW gumb za nazaj lahko overridamo finish() ali onBackPressed()
    //ker se lahko aktivnost zaključi tudi programsko in ne le onBackPressed, je bolje overridati finish()
    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        //zgornjo kodo beremo kot pojdi v levo iz desne
    }

    //da dobimo animiran prehod za SW gumb za nazaj (UP BUTTON) lahko overridamo onOptionsItemSelected
    //gumb ima id android.R.id.home
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    //koda za swipe v prejšnjo aktivnost
    float x1, x2;
    @Override
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
                 if(x1 < x2)
                {
                    //pokličemo metodo, ki se kliče tudi, ko uporabnik pritisne HW gumb za nazaj in tako odpremo prejšnji Activity
                    onBackPressed();
                }
                break;
        }
        return true;
    }
}
