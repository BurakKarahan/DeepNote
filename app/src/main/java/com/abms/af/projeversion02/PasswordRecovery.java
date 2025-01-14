package com.abms.af.projeversion02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abms.af.projeversion02.Models.PHPMailersifregonderme;
import com.abms.af.projeversion02.RestApi.ManagerAll;

import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordRecovery extends AppCompatActivity {

    SharedPreferences sharedPref;
    EditText Email;
    Button Send;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        tanımla();
        islevver();

    }

    public void tanımla() {
        Email = (EditText) findViewById(R.id.Email);
        Send = (Button) findViewById(R.id.Send);
        textView = (TextView) findViewById(R.id.textView);

    }

    public void islevver() {

        Send.setEnabled(false);
        Send.setBackgroundColor(getResources().getColor(R.color.holo_blue_dark_saydam));

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.destek)));
                startActivity(browserIntent);
            }
        });

        Email.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                if (Email.length() > 0) {
                    Send.setEnabled(true);
                    Send.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                } else {
                    Send.setEnabled(false);
                    Send.setBackgroundColor(getResources().getColor(R.color.holo_blue_dark_saydam));
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final SweetAlertDialog pDialog = new SweetAlertDialog(PasswordRecovery.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Yükleniyor");
                pDialog.setCancelable(false);
                pDialog.show();

                final String email = Email.getText().toString();
                //Toast.makeText(getApplicationContext(), email ,Toast.LENGTH_LONG).show();

                if (isValidEmailId(email.trim()))
                {
                    try {
                        final Call<PHPMailersifregonderme> request = ManagerAll.webyonet().PHPMailersifregonderme(getString(R.string.key_for_protection_create_user), email);
                        request.enqueue(new Callback<PHPMailersifregonderme>() {
                            @Override
                            public void onResponse(Call<PHPMailersifregonderme> call, Response<PHPMailersifregonderme> response) {

                                if (response.isSuccessful()) {
                                    if (response.body().getResult().equals("Basarili")) {
                                        //Toast.makeText(getApplicationContext(), "Şifre sıfırlama kodu email adresinize gönderilmiştir", Toast.LENGTH_LONG).show();
                                        //Toast.makeText(getApplicationContext(), response.body().getKod(), Toast.LENGTH_LONG).show();

                                        sharedPref = getApplicationContext().getSharedPreferences("sifre", 0);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("Kod", response.body().getKod()); //string değer ekleniyor
                                        editor.putString("Email", response.body().getEmail()); //string değer ekleniyor
                                        editor.commit(); //Kayıt

                                        pDialog.cancel();

                                        new SweetAlertDialog(PasswordRecovery.this, SweetAlertDialog.NORMAL_TYPE)
                                                .setTitleText("E-posta Gönderildi")
                                                .setContentText(email + " adresine, hesabına yeniden girebilmeni sağlayacak kod içeren bir e-posta gönderildi")
                                                .setConfirmText("Tamam")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {
                                                        //sDialog.dismissWithAnimation();

                                                        Intent intent = new Intent(getApplicationContext(), PasswordRecovery2.class);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .show();
                                    } else {
                                        pDialog.cancel();

                                        SweetAlertDialog sa = new SweetAlertDialog(PasswordRecovery.this, SweetAlertDialog.ERROR_TYPE);
                                        sa.setTitleText("Hata!");
                                        sa.setContentText("Kullanıcı bulunamadı, Lütfene-posta adresinizi kontrol ederek tekrar deneyiniz");
                                        sa.setConfirmText("Tamam");
                                        sa.show();
                                    }

                                } else {
                                    pDialog.cancel();

                                    //Toast.makeText(getApplicationContext(), "Hata ile Karşılaşıldı, Daha Sonra Tekrar Deneyiniz", Toast.LENGTH_LONG).show();
                                    final SweetAlertDialog sa = new SweetAlertDialog(PasswordRecovery.this, SweetAlertDialog.WARNING_TYPE);
                                    sa.setTitleText("Dikkat!");
                                    sa.setContentText("Bir şeyler yolunda gitmedi, internet bağlantınızı kontrol ederek tekrar deneyiniz");
                                    sa.setConfirmText("Tamam");
                                    sa.show();
                                }

                            }

                            @Override
                            public void onFailure(Call<PHPMailersifregonderme> call, Throwable t) {

                                pDialog.cancel();

                                SweetAlertDialog sa = new SweetAlertDialog(PasswordRecovery.this, SweetAlertDialog.WARNING_TYPE);
                                sa.setTitleText("Dikkat!");
                                sa.setContentText("Bir şeyler yolunda gitmedi, internet bağlantınızı kontrol ederek tekrar deneyiniz");
                                sa.setConfirmText("Tamam");
                                sa.show();

                                //Toast.makeText(getApplicationContext(), "Hata ile Karşılaşıldı, Daha Sonra Tekrar Deneyiniz", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (Exception e) {
                        pDialog.cancel();
                        Log.e("TAG", "onClick: ", e);
                    }
                }
                else
                {
                    pDialog.cancel();

                    final SweetAlertDialog sa = new SweetAlertDialog(PasswordRecovery.this, SweetAlertDialog.WARNING_TYPE);
                    sa.setTitleText("Dikkat!");
                    sa.setContentText("Lütfen E-posta bilgilerinizi kontrol ederek tekrar deneyiniz");
                    sa.setConfirmText("Tamam");
                    sa.show();
                }
            }
        });
    }
    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}
