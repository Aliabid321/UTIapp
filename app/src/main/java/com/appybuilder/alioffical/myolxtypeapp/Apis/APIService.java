package com.appybuilder.alioffical.myolxtypeapp.Apis;

import com.appybuilder.alioffical.myolxtypeapp.FavCat;
import com.appybuilder.alioffical.myolxtypeapp.Models.AdsCategoryModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.DummyModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.SampleModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.ShowAdsModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.UpdateAdsModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.UserModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.UtiHomeModel;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @FormUrlEncoded
    @POST("register")
    Call<UserModel> SignUp(@Field("email") String username,
                           @Field("password") String password,
                           @Field("c_password") String c_password);


    @FormUrlEncoded
    @POST("login")
    Call<ShowAdsModel> Login(@Field("email") String email,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("reset-password-api")
    Call<UserModel> resetPass(@Field("email") String email);

    @FormUrlEncoded
    @POST("/order")
    Call<FavCat> PizzaPlaceOrder(@Field("cart_ids") String cart_ids,
                                 @Field("order_no") String order_no,
                                 @Field("tip") String tip,
                                 @Field("discount") String discount,
                                 @Field("delivery_fee") String delivery_fee,
                                 @Field("total_price") String total_price,
                                 @Field("shipping_addres ") String shipping_addres);

    @GET("/photos")
    Call<DummyModel> showAllAdsfun();

    @GET("view-all-ads-api")
    Call<UtiHomeModel.NewHomeModel> showAllAdsfunData(@Header("Authorization") String key);

    @GET("view-favorite-ads-api")
    Call<UtiHomeModel.NewHomeModel> showAllfav_Ads(@Header("Authorization") String key);

    @GET("view-ads-api/{id}")
    Call<UtiHomeModel.NewHomeModel> showSingleAd(@Header("Authorization") String key, @Path("id") String id);

    @GET("view-all-ads-categories-api")
    Call<AdsCategoryModel.AdsCatModel> showAllAdsCategoryfunData(@Header("Authorization") String key);

    @Multipart
    @POST("https://mithho.com/public/api/create-ads-api")
    Call<AdsCategoryModel.AdsCatModel> create_Ads(@Header("Authorization") String key, @Body HashMap<String, String> hashMap);

    @POST("https://mithho.com/public/api/create-ads-api")
    Call<AdsCategoryModel.AdsCatModel> uploadPhoto(@Header("Authorization") String key, @Body HashMap<String, String> hashMap);

    @Multipart
    @POST("https://mithho.com/public/api/create-ads-api")
    Call<AdsCategoryModel.AdsCatModel> create_Ads_2(@Header("Authorization") String key,
                                                    @Part("item_name") RequestBody item_name,
                                                    @Part("ads_category") RequestBody ads_category,
                                                    @Part("item_description") RequestBody item_description,
                                                    @Part("location") RequestBody location,
                                                    @Part MultipartBody.Part ads_images);

    @POST("https://mithho.com/public/api/add-favorite-ads-api")
    Call<UpdateAdsModel.UpdateModel> update_fav_item(@Header("Authorization") String key,
                                                     @Query("ads_id") String ads_id,
                                                     @Query("purpose") String purpose
    );
    @POST("https://mithho.com/public/api/logout-user")
    Call<AdsCategoryModel.AdsCatModel>logout(@Header("Authorization") String key);


//
//    @FormUrlEncoded
//    @POST("/order")
//    Call<SignInModel> PizzaPlaceOrder(@Field("cart_ids") String cart_ids,
//                                      @Field("order_no") String order_no,
//                                      @Field("tip") String tip,
//                                      @Field("discount") String discount,
//                                      @Field("delivery_fee") String delivery_fee,
//                                      @Field("total_price") String total_price,
//                                      @Field("shipping_addres ") String shipping_addres);
//    @GET("logout")
//    Call<SignInModel> Pizzalogout(@Query("token") String token);
//
//    @GET("category")
//    Call<CategoryModel> PizzaCategory(@Query("token") String token);
//
//    @GET("drinks")
//    Call<ColdDrinkModel> PizzaColdDrink(@Query("token") String token);
//
//    @GET("refresh")
//    Call<SignInModel> PizzaRefreshToken(@Query("token") String token);
//
//    @GET("product-by-category/2")
//    Call<PizaModel> PizzaProductBy_id(@Query("token") String token);
//
//    @FormUrlEncoded
//    @POST("add-to-cart")
//    Call<SignInModel> PizzaAddtoCart(@Field("product_id") String product_id,
//                                     @Field("quantity") String quantity);
}
