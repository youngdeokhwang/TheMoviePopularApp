package com.acaroom.theMoviePopular.data

import io.reactivex.Observable
import retrofit2.Call
import javax.inject.Inject

/**
 * REST API를 사용하기 위해 구현된 클래스
 * @param theMovieService TheMovieService의 인스턴스 프로퍼티
 */
class TheMovieRestApi @Inject constructor(private val theMovieService: TheMovieService) : TheMovieApi {
    override fun getMovieListRx(param: Map<String, String>): Observable<MovieListResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getMovieListCo(param: Map<String, String>): MovieListResponse {
        return theMovieService.getDeferredTop(param).await()
    }

    override fun getMovieListNormal(param: Map<String, String>): Call<MovieListResponse> {
        return theMovieService.getTop(param)
    }
}