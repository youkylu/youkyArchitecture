package com.example.commonlib.helper

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit

/**
 *
 * Class: com.example.base.helper.RetrofitServiceManager
 *
 * Description:
 * <pre>
 *
</pre> *
 *
 * @author lujunjie
 * @date 2019/2/1/15:36
 */

class RetrofitServiceManager private constructor(url: String) {
    private val mRetrofit: Retrofit

    companion object {
//        private val DEFAULT_TIME_OUT = 5//超时时间 5s
//        private val DEFAULT_READ_TIME_OUT = 10

        /**
         * 获取RetrofitServiceManager
         *
         * @return
         */
        @Volatile
        var instance: RetrofitServiceManager? = null

        private fun getInstance(c: String): RetrofitServiceManager {
            if (instance == null) {
                synchronized(RetrofitServiceManager::class) {
                    if (instance == null) {
                        instance = RetrofitServiceManager(c)
                    }
                }
            }
            return instance!!
        }

        /**
         * 创建对应的api class
         */
        fun <T> createApi(clazz: Class<T>, url: String): T {
            return getInstance(url)?.mRetrofit?.create(clazz)
        }
    }

    init {
        val level = HttpLoggingInterceptor.Level.BODY
        //新建log拦截器
        val loggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("ApiUrl", "--->$message") })
        loggingInterceptor.level = level

        // init cookie manager
        val cookieHandler = CookieManager()

        // 创建 OKHttpClient
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .cookieJar(JavaNetCookieJar(cookieHandler))
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        // 创建Retrofit
        mRetrofit = Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .baseUrl(url)
            .build()
    }

}

