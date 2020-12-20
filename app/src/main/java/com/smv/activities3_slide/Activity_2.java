package com.smv.activities3_slide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_2 extends AppCompatActivity
{
    Button buttonOpenActivity3;
    EditText text, number;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        setTitle("Activity 2");

        buttonOpenActivity3 = findViewById(R.id.buttonOpenActivity3);
        text = findViewById(R.id.editTextText);
        number = findViewById(R.id.editTextNumber);

        buttonOpenActivity3.setOnClickListener(OpenActivity3_OnClickListener);
    }

    //del za vračanje rezultata v prejšnjo aktivnost

    //v OnCLickListenerju, ki odpre naslednjo aktivnost moramo aktivnost odpreti drugače
    //odpremo Activity tako, da pričakujemo rezultat
    //ker bi lahko odpirali več aktivnosti, moramo vedeti, kateri rezultat bomo dobili nazaj,
    //zato rabimo reguestCode
    // namesto startActivity(intent); uporabimo  startActivityForResult(intent, 1);
    public View.OnClickListener OpenActivity3_OnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //celotna koda je v svoji metodi, ki jo lahko pokličemo tudi iz drugih dogodkov
            OpenActivity3();
        }
    };

    public void OpenActivity3()
    {
        //preberemo besedilo iz obeh polj
        String textRead = text.getText().toString();
        int numberRead;
        try
        {
            numberRead = Integer.parseInt(number.getText().toString());
        }
        catch(Exception ex)
        {
            //sicer EditView dovoljuje le številke, a do izjeme pride tudi, če je prazen
            numberRead = -1;
        }
        Intent intent = new Intent(Activity_2.this, Activity_3.class);
        intent.putExtra("EXTRA_STRING", textRead);
        intent.putExtra("EXTRA_NUMBER", numberRead);
        //startActivity(intent);

        //del, ki se tiče prenosa parametrov
        //odpremo Activity tako, da pričakujemo rezultat
        //ker bi lahko odpirali več aktivnosti, moramo vedeti, kateri rezultat bomo dobili nazaj,
        //zato rabimo reguestCode
        //requestCode uporabimo tisti, ki ga izberemo sami
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        //zgornjo kodo beremo kot pojdi v desno iz leve
    }

    //metoda, ki se izvede, ko se v aktivnost vrne rezultat
    //začnemo tipkati in jo izberemo, ali pa pritisnemo Ctrl+o (override) in jo izberemo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //ko smo poklicali Activity3, smo za requestCode posredovali vrednost 1, ki jo dobimo tudi nazaj
        if (requestCode == 1){
            //če je uporabnik uporabil katerega od gumbov za izračun in imamo rezultat
            if (resultCode == RESULT_OK){
                int result = data.getIntExtra("RESULT",0);
                Toast.makeText(this, "The result is " + result, Toast.LENGTH_LONG).show();
            }
            //če smo se vrnili brez rezultata (HW gumb, lahko tudi SW gumb za nazaj)
            //da v Activity2 dobimo puščico za nazaj, je treba v manifest dodati android:parentActivityName
            //če ga nastavimo na MainActivity, s puščico nazaj ne dobimo rezultata aktivnosti, prikaže se prazen Activity
            //če ga nastavimo na samo sebe (.Activity2) pa je obnašanje enako kot pri HW gumbu za nazaj: reultat je RESULT_CANCELED, vpisani podatki se ohranijo
            else if(resultCode == RESULT_CANCELED)
                Toast.makeText(this, "Nothing to display", Toast.LENGTH_SHORT).show();
        }
    }


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

    //da dobimo animiran prehod za HW gumb za nazaj lahko overridamo finish() ali onBackPressed()
//    @Override
//    public void onBackPressed()
//    {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }

    //da dobimo animiran prehod za SW gumb za nazaj (UP BUTTON) overridamo onOptionsItemSelected
    //gumb ima id android.R.id.home
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //koda za swipe v naslednjo ali prejšnjo aktivnost
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
                if(x1 > x2)
                {
                    //odpremo naslednji Activity
                    OpenActivity3();
                }
                else if(x1 < x2)
                {
                    //pokličemo metodo, ki se kliče tudi, ko uporabnik pritisne HW gumb za nazaj in tako odpremo prejšnji Activity
                    onBackPressed();
                }
                break;
        }
        return true;
    }
}