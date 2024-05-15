package br.com.johabfreitas.apiviacep.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object{

        val BASE_URL = "https://viacep.com.br/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}