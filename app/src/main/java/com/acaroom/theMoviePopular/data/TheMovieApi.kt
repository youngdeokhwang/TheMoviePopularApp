package com.acaroom.theMoviePopular.data

import io.reactivex.Observable
import retrofit2.Call

/**
 * 웹서비스의 결과를 가져오기위한 메서드를 제공하는 인터페이스
 */
interface TheMovieApi {

    fun getMovieListRx(param: Map<String, String>): Observable<MovieListResponse>

    fun getMovieListNormal(param: Map<String, String>): Call<MovieListResponse>

    suspend fun getMovieListCo(param: Map<String, String>): MovieListResponse
}
