package com.acaroom.theMoviePopular.ui

import com.acaroom.theMoviePopular.data.MovieItem
import com.acaroom.theMoviePopular.data.MovieList
import com.acaroom.theMoviePopular.data.MovieListResponse
import com.acaroom.theMoviePopular.data.TheMovieApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * TheMovieService로부터 요청을 하기위해
 */
@Singleton
class MovieManager @Inject constructor(private val api: TheMovieApi) {

    suspend fun getMovieList(param: Map<String, String>): MovieList {
        val result = api.getMovieListCo(param)
        return process(result)
    }

    private fun process(response: MovieListResponse): MovieList {
        val list = response.results.map {
            MovieItem(
                it.vote_count,
                it.vote_average,
                it.title,
                it.release_date,
                it.poster_path,
                it.overview
            )
        }
        return MovieList(response.page, list)
    }
}