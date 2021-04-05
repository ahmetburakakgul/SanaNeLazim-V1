package com.example.snl.Retrofit;

import com.example.snl.Model.AccountModel;
import com.example.snl.Model.AccountUpdateModel;
import com.example.snl.Model.AddImageModel;
import com.example.snl.Model.AdvertisementResultModel;
import com.example.snl.Model.FavoriIslemModel;
import com.example.snl.Model.FavoriListeSliderModel;
import com.example.snl.Model.FavoriListelemeModel;
import com.example.snl.Model.FavoriModel;
import com.example.snl.Model.IlanBilgiUpdate;
import com.example.snl.Model.IlanDetayModel;
import com.example.snl.Model.IlanDetaySliderModel;
import com.example.snl.Model.IlanResimUpdateModel;
import com.example.snl.Model.IlanSilModel;
import com.example.snl.Model.IlanlarimModel;
import com.example.snl.Model.LoginModel;
import com.example.snl.Model.MemberModel;
import com.example.snl.Model.SifremiUnuttumModel;
import com.example.snl.Model.TumIlanlarModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    //Get methodu ile genellikle server’dan bilgi talebinde bulunulduğu zaman kullanılır.
    //Post Metodu İse Web Service Form Şeklinde Veri Gönderme İşleminde Yardımcı olur.

    //@FormUrlEncoded Post veya Put gibi metotları formUrl formatına dönüştürüp gönderilmesini sağlayan yapıdır.
    //@Field parametrenizi gizleyen ve güvenliği sağlamak için kullanılır.Yani Post Ettiğimiz Parametreleri URL de Gizli Tutar.

    //Query vermiş olduğumuz parametreleri URL de key = value şeklinde tutulmasını sağlar.

    @FormUrlEncoded
    @POST("loginuser.php")
    Call<LoginModel> loginuser(@Field("email") String email,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("registeruser.php")
    Call<MemberModel> registerUser(@Field("email") String email,
                                   @Field("username") String username,
                                   @Field("phone") String phone,
                                   @Field("password") String password);

    @FormUrlEncoded
    @POST("addadvertisements.php")
    Call<AdvertisementResultModel> advertisement(@Field("memberId") String memberId,
                                                 @Field("title") String title,
                                                 @Field("description") String description,
                                                 @Field("price") String price,
                                                 @Field("category") String category,
                                                 @Field("state") String state);


    @FormUrlEncoded
    @POST("advertisementimage.php")
    Call<AddImageModel> addImage(@Field("memberId") String memberId,
                                 @Field("advertisementId") String advertisementId,
                                 @Field("image") String base64StringImage);


    @GET("ilanlarim.php")
    Call<List<IlanlarimModel>> ilanlarim(@Query("memberId") String memberId);


    @GET("ilansil.php")
    Call<IlanSilModel> ilansil(@Query("advertisementId") String advertisementId);


    @GET("ilanupdate.php")
    Call<IlanBilgiUpdate> ilanUpdate(@Query("advertisementId") String advertisementId,
                                     @Query("title") String title,
                                     @Query("description") String description,
                                     @Query("price") String price);


    @FormUrlEncoded
    @POST("ilanresimupdate.php")
    Call<IlanResimUpdateModel> ilanResimUpdate(@Field("memberId") String memberId,
                                               @Field("advertisementId") String advertisementId,
                                               @Field("image") String base64StringImage);


    @GET("tumilanlar.php")
    Call<List<TumIlanlarModel>> tumIlanlar();


    @GET("ilandetay.php")
    Call<IlanDetayModel> ilanDetay(@Query("advertisementId") String advertisementId);


    @GET("ilandetayresim.php")
    Call<List<IlanDetaySliderModel>> ilanDetaySlider(@Query("advertisementId") String advertisementId);


    @GET("favori.php")
    Call<FavoriModel> getButtonText(@Query("member_Id") String memberId, @Query("advertisement_Id") String advertisementId);


    @GET("favoriislemler.php")
    Call<FavoriIslemModel> favoriIslemler(@Query("member_Id") String memberId, @Query("advertisement_Id") String advertisementId);


    @GET("favorilisteslider.php")
    Call<List<FavoriListeSliderModel>> setListeSlider(@Query("member_Id") String memberId);


    @GET("favoriliste.php")
    Call<List<FavoriListelemeModel>> setfavoriListe(@Query("member_Id") String memberId);


    @GET("user.php")
    Call<AccountModel> account(@Query("memberId") String memberId);


    @GET("userupdate.php")
    Call<AccountUpdateModel> accountUpdate(@Query("memberId") String memberId,
                                           @Query("username") String username,
                                           @Query("password") String password,
                                           @Query("email") String email,
                                           @Query("phone") String phone);

    @GET("sifremiunuttum.php")
    Call<SifremiUnuttumModel> sifremiUnuttum(@Query("email") String email);

}
