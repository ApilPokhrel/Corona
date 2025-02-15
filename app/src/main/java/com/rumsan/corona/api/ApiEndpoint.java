package com.rumsan.corona.api;

import com.rumsan.corona.entity.FaqsP;
import com.rumsan.corona.entity.HospitalP;
import com.rumsan.corona.entity.MythsP;
import com.rumsan.corona.entity.NepWorldP;
import com.rumsan.corona.entity.NepalDataModel;
import com.rumsan.corona.entity.NewsP;
import com.rumsan.corona.entity.PodcastModel;
import com.rumsan.corona.entity.PodcastP;
import com.rumsan.corona.entity.WorldDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiEndpoint {

    @GET("cms/myths")
    Call<MythsP> myths(@Query("start") int start, @Query("limit") int limit, @Query("page") int page);

    @GET("cms/faqs")
    Call<FaqsP> faqs(@Query("start") int start, @Query("limit") int limit, @Query("page") int page);

    @GET("news/")
    Call<NewsP> news(@Query("start") int start, @Query("limit") int limit, @Query("page") int page);

    @GET("cms/hospitals")
    Call<HospitalP> hospitals(@Query("start") int start, @Query("limit") int limit, @Query("page") int page, @Query("name") String name, @Query("state") String state);

    @GET("data/world")
    Call<List<WorldDataModel>> worldData();

    @GET("podcasts/")
    Call<PodcastP> podcast();

    @GET("data/nepal")
    Call<NepalDataModel> nepalData();

    @GET("data/allnepali")
    Call<NepWorldP> nepWorld(@Query("start") int start, @Query("limit") int limit, @Query("page") int page);


}
