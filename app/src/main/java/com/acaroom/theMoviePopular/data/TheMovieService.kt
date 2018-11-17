package com.acaroom.theMoviePopular.data

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

// The Movie API 서비스르 위한 키
const val API_KEY = "f2ddab39501d4e5bb900d1b4514e9cb1"
const val BASE_URL = "https://api.themoviedb.org/3/"

/**
 * Retrofit의 @GET 어노테이션으로 HTTP의 GET요청으로 JSON을 읽어온다
 */
interface  TheMovieService {
    @GET("discover/movie")
    /**
     * REST 요청을 처리하기 위한 메서드
     * @param par QueryMap을 통해 질의한 쿼리문을 Map으로 부터 받는다.
     * @return Call<T> 콜백 인터페이스 반환, T는 주고 받을 데이터 구조
     * @QueryMap 어노테이션은 위치가 바뀌어도 동적으로 값을 받아올 수 있게 한다.
     */
    fun getTop(@QueryMap par: Map<String, String>): Call<MovieListResponse>

    /**
     * 코틀린을 사용하는 버전의 요청 메서드
     * @return Deferred<T> 코루틴의 지연된 콜백 인터페이스 반환, T는 주고 받을 데이터 구조
     */
    @GET("discover/movie")
    fun getDeferredTop(@QueryMap par: Map<String, String>): Deferred<MovieListResponse>
}
