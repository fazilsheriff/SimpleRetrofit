package com.example.sampleretrofitparse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
1 Push the code
2 Find the working mechanism
3 How will you log the response
4 Integrate with recycler
5 Save it room DB and than list in recyler view
6 use view model ,live data and data binding
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //For logging the response
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
        httpClient.addInterceptor(httpLoggingInterceptor);
        //Retrofit instance
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(iMarvelApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        // creating APi instance
        iMarvelApi iMarvelApi=retrofit.create(com.example.sampleretrofitparse.iMarvelApi.class);
        //Call the interfac method
        Call<List<HeroModel>> call=iMarvelApi.getHeroes();

        call.enqueue(new Callback<List<HeroModel>>() {
            @Override
            public void onResponse(Call<List<HeroModel>> call, Response<List<HeroModel>> response) {
                Log.i("Respnse",response.raw().toString());
                Log.i("Respnse",response.body().toString());
                Log.i("Respnse",response.message().toString());
//                Log.i("Respnse",response.errorBody().toString());
                Log.i("Response",new GsonBuilder().setPrettyPrinting().create().toJson(response));

            }

            @Override
            public void onFailure(Call<List<HeroModel>> call, Throwable t) {
//                Log.i("Respnse","Failure");
            }
        });

    }
}