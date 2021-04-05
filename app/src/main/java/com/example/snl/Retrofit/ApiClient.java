package com.example.snl.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL="http://192.168.1.2:80/snldb_files/";  //Bizim Localdeki Web Servicelerimize Bağlanacğını Belirttiğimiz Kısımdır BASE_URL
    public static Retrofit retrofit = null; //Retrofit açık kaynak kodlu REST İstemcisidir.REST, servis yönelimli mimari üzerine oluşturulan yazılımlarda kullanılan bir veri transfer yöntemidir.HTTP üzerinde çalışır.
                                            //Diğer Alternatiflere Göre Daha Hızlı Çalışır.İstemci ve sunucu arasında XML veya JSON verilerini taşıyarak uygulamaların haberleşmesini sağlar.
    public static Retrofit getApiClient(){
        if (retrofit==null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)) //Gson Kütüphanesi Json Formatına Objelerin Yazılıp Okunmasını Sağlar.
                    .build();
        }
        return retrofit;
    }
}
