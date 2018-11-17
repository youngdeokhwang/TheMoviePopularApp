package com.acaroom.theMoviePopular.di

import com.acaroom.theMoviePopular.data.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * 네트워크의 모든 요청에 대한 의존성을 제공하는 모듈
 */
@Module
class NetworkModule {

    /**
     * Retrofit 객체를 제공
     * @return Retrofit 객체 반환
     * 설명: Retrofit 빌더메서드인 baseUrl을 통해 URL을 초기화하고 컨버터를 Moshi로 지정
     * 코틀린의 코루틴을 같이 사용하기 위해 CoroutineCallAdapterFactory 지정
     * 이때는 Call 대신 Deferred 인스턴스를 반환해 사용할 수 있다.
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory()) // 코루틴을 사용하는 경우
                // .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))  // RxJava의 경우
                .build()
    }
}