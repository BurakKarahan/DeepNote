package com.abms.af.projeversion02.RestApi;

import com.abms.af.projeversion02.Models.GonderiSil;
import com.abms.af.projeversion02.Models.Homesayfasitumpaylasimveritabani;
import com.abms.af.projeversion02.Models.Kullanicigirissonuc;
import com.abms.af.projeversion02.Models.Kullanicikayitsonuc;
import com.abms.af.projeversion02.Models.PHPMailersifregonderme;
import com.abms.af.projeversion02.Models.Pdfyuklemesonuc;
import com.abms.af.projeversion02.Models.Profilbilgilerigetir;
import com.abms.af.projeversion02.Models.Profilfotosilmesonuc;
import com.abms.af.projeversion02.Models.Profilfotoyuklemesonuc;
import com.abms.af.projeversion02.Models.Profilsayfasikullanicipaylasimlari;
import com.abms.af.projeversion02.Models.Resimyuklemesonuc;
import com.abms.af.projeversion02.Models.Sikayetetmesonuc;
import com.abms.af.projeversion02.Models.Yenisifrebelirleme;
import com.abms.af.projeversion02.Models.Yorumlarigetirsonuc;
import com.abms.af.projeversion02.Models.Yorumsilmesonuc;
import com.abms.af.projeversion02.Models.Yorumyapmasonuc;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ManagerAll extends BaseManager {

    private static ManagerAll webyonet = new ManagerAll();

    public static synchronized ManagerAll webyonet() {
        return webyonet;
    }

    public Call<Kullanicikayitsonuc> kullaniciekle(String key ,String ad_soyad, String dogum_tarihi, String universite, String bolum, String e_posta, String sifre) {
        Call<Kullanicikayitsonuc> kullaniciekleme = getRestApiClient().kullaniciekle(key,ad_soyad, dogum_tarihi, universite, bolum, e_posta, sifre);
        return kullaniciekleme;
    }

    public Call<Kullanicigirissonuc> kontrolet(String giris_mail, String giris_sifre) {
        Call<Kullanicigirissonuc> kontrol = getRestApiClient().kontrol(giris_mail, giris_sifre);
        return kontrol;
    }

    public Call<Resimyuklemesonuc> resim_yukle(String email,Integer id_kullanici, String ders, String aciklama, String bolum, Map<String, RequestBody> file) {
        Call<Resimyuklemesonuc> resimekle = getRestApiClient().resimekle(email,id_kullanici, ders, aciklama, bolum,  file);
        return resimekle;
    }

    public Call<Profilbilgilerigetir> profilgetir(String email,Integer id_kullanici) {
        Call<Profilbilgilerigetir> profilbilgi = getRestApiClient().profilgetir(email,id_kullanici);
        return profilbilgi;
    }

    public Call<List<Homesayfasitumpaylasimveritabani>> paylasimlartumugetir(String email,String jsonguvenlık,int page) {
        Call<List<Homesayfasitumpaylasimveritabani>> paylasımgelenler = getRestApiClient().paylasımlarintumunugetir(email,jsonguvenlık,page);
        return paylasımgelenler;
    }

    public Call<List<Profilsayfasikullanicipaylasimlari>> kullancigönderigetir(String email,Integer id_kullanici) {
        Call<List<Profilsayfasikullanicipaylasimlari>> kullanicipaylasim = getRestApiClient().kullanicigönderigetir(email,id_kullanici);
        return kullanicipaylasim;
    }

    public Call<Pdfyuklemesonuc> pdf_yukle(String email,Integer id_kullanici, String ders, String aciklama, String bolum, Map<String, RequestBody> file) {
        Call<Pdfyuklemesonuc> pdfekle = getRestApiClient().pdfekle(email,id_kullanici, ders, aciklama, bolum, file);
        return pdfekle;
    }
    public Call<Profilfotoyuklemesonuc> ppyukle(String email,Integer id_kullanici,Map<String, RequestBody> file)
    {
        Call<Profilfotoyuklemesonuc> ppyukleme=getRestApiClient().profilfotoyukle(email,id_kullanici,file);
        return  ppyukleme;
    }
    public  Call<Sikayetetmesonuc> sikayetet(String email,Integer paylasımid)
    {
        Call<Sikayetetmesonuc> sikayet=getRestApiClient().sikayetet(email,paylasımid);
        return  sikayet;
    }

    public Call<List<Homesayfasitumpaylasimveritabani>> aramagonderigetir(String email,String universite,String bolum, String dersadi,int page) {
        Call<List<Homesayfasitumpaylasimveritabani>> aramagonderi = getRestApiClient().aramagonderigetir(email,universite,bolum,dersadi,page);
        return aramagonderi;
    }
    public  Call<Yorumyapmasonuc> yorumyap(String email,int id_kullanici,int paylasim_id,String yorum)
    {
        Call<Yorumyapmasonuc> yorumgonder=getRestApiClient().yorumyap(email,id_kullanici,paylasim_id,yorum);
        return yorumgonder;
    }

    public Call<List<Yorumlarigetirsonuc>> yorumgetir(String email,int paylasim_id)
    {
        Call<List<Yorumlarigetirsonuc>> yorumgel=getRestApiClient().yorumgetir(email,paylasim_id);
        return yorumgel;
    }

    public Call<Yorumsilmesonuc> yorumsil(String email,int id_kullanici,int paylasim_id,int id_yorum)
    {
        Call<Yorumsilmesonuc> yorumsill=getRestApiClient().yorumsil(email,id_kullanici,paylasim_id,id_yorum);
        return yorumsill;
    }

    public Call<Profilfotosilmesonuc> fotosil(String email,Integer id_kullanici)
    {
        Call<Profilfotosilmesonuc> fotosil=getRestApiClient().profilfotosil(email,id_kullanici);
        return fotosil;
    }

    public Call<ResponseBody> indirr(String url)
    {
        Call<ResponseBody> download=getRestApiClient().indir(url);
        return download;
    }

    public Call<PHPMailersifregonderme> PHPMailersifregonderme(String key ,String email)
    {
        Call<PHPMailersifregonderme> x = getRestApiClient().RestPHPMailer(key,email);
        return x;
    }

    public Call<Yenisifrebelirleme> YeniSfireBelirleme(String key ,String email, String sifre)
    {
        Call<Yenisifrebelirleme> x = getRestApiClient().RestYeniSifreBekirleme(key,email,sifre);
        return x;
    }

    public Call<GonderiSil> GonderiSil(String email,Integer id)
    {
        Call<GonderiSil> x = getRestApiClient().RestGonderiSil(email,id);
        return x;
    }

}
