package com.abms.af.projeversion02;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.abms.af.projeversion02.Adapters.Paylasimtumverileradapter;
import com.abms.af.projeversion02.Models.Homesayfasitumpaylasimveritabani;

import java.util.List;


public class anasayfa_pop_up_arama extends AppCompatActivity {

    private static final String[] OKULLAR = new String[]{
            "okul1","okul2","okul3"
    };

    EditText arama_dersadi;
    Button arama_buton;
    String universite,bolum,dersadi;
    ArrayAdapter universite_adapter, bolum_adapter;
    String[] universite_listesi, bolum_listesi;
    Activity activity;
    List<Homesayfasitumpaylasimveritabani> tum_veriler_liste;
    Paylasimtumverileradapter paylasimtumverileradapter;
    ListView listView_homesayfasi;
    ProgressBar progressBar;
    AutoCompleteTextView arama_universite,arama_bolum;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa_pop_up_arama);

        DisplayMetrics d = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);

        int genislik = d.widthPixels;
        int yukseklik = d.heightPixels;

        getWindow().setLayout((int)(genislik),(int)(yukseklik*.6));

        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.gravity = Gravity.TOP;
        p.x = 0;
        p.y= 100;

        getWindow().setAttributes(p);

        tanımla();
        islevver();
    }

    public void tanımla()
    {

        arama_universite=findViewById(R.id.arama_universite);
        arama_bolum=findViewById(R.id.arama_bolum);
        arama_buton=findViewById(R.id.arama_buton);
        arama_dersadi=findViewById(R.id.arama_dersadi);
        listView_homesayfasi=findViewById(R.id.listview_homesayfasi);
        progressBar=findViewById(R.id.anasayfa_progress_bar);


        universite_listesi = getResources().getStringArray(R.array.universite_listesi_arama_için);
        bolum_listesi = getResources().getStringArray(R.array.Bolum_listesi);
    }

    public void islevver()
    {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.okullar,R.id.okultextitem,universite_listesi);
        arama_universite.setAdapter(adapter);

        ArrayAdapter<String> adapterbolum = new ArrayAdapter<String>(this,R.layout.bolumler,R.id.bolumtextitem,bolum_listesi);
        arama_bolum.setAdapter(adapterbolum);

        arama_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                universite=arama_universite.getText().toString();
                bolum=arama_bolum.getText().toString();
                dersadi=arama_dersadi.getText().toString();

                if (universite.equals(""))
                {
                    universite=getString(R.string.universite_listesi__arama_hepsi);
                }

                if(bolum.matches(""))
                {
                    arama_bolum.setError("Bölüm bilgisi gereklidir");
                    arama_bolum.requestFocus();
                }
                else
                {
                    if (dersadi.equals("")) {
                        dersadi="UyarıBos";
                    }
                    //Toast.makeText(getApplicationContext(), "dersadi"+dersadi, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.putExtra("universite", universite);
                    i.putExtra("bolum", bolum);
                    i.putExtra("dersadi", dersadi);
                    setResult(Activity.RESULT_OK,i);
                    finish();
                }
            }
        });
    }
}
