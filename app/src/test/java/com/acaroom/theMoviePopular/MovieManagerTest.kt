// this is to allow method names to be with natural language
@file:Suppress("IllegalIdentifier")

package com.acaroom.theMoviePopular

import com.acaroom.theMoviePopular.data.*
import com.acaroom.theMoviePopular.ui.MovieManager
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

/**
 * Unit Tests for MovieManager
 *
 */
class MovieManagerTest {

    private var apiMock = mock<TheMovieApi>()
    val param = mapOf(
            "page" to "1",
            "api_key" to API_KEY,
            "sort_by" to "popularity.desc",
            "language" to "ko"
    )

    @Test // (1) 응답이 null이 아닌지 검사
    fun `Success - check response is not null`() = testBlocking {
        // 준비(prepare)
        val movieListResponse = MovieListResponse(1, listOf())
        // (4) 코루틴을 위한 목 객체
        apiMock = mock {
            onBlocking { getMovieListCo(param) } doReturn movieListResponse
        }

        // 호출(call)
        val movieManager = MovieManager(apiMock)
        val movieList = movieManager.getMovieList(param)

        // 평가(assert)
        assertNotNull(movieList)
    }

    @Test  // 뉴스를 받을 수 있는지 검사
    fun `Success - checks received one movie info`() = testBlocking {
        // prepare
        val movieDetail = MovieDetailResponse( // (5) 목 데이터의 구성
                123,
                3.5f,
                "Title",
                "release_date",
                "poster_path",
                "overview"
        )
        val movie = MovieListResponse(1, listOf(movieDetail))
        apiMock = mock {
            onBlocking { getMovieListCo(param) } doReturn movie
        }

        // 호출(call)
        val movieManager = MovieManager(apiMock)
        val movieList = movieManager.getMovieList(param)

        // 평가(assert)
        assertNotNull(movieList)
        // (6) 목데이터의 일치여부
        assertEquals(movieDetail.release_date, movieList.results[0].release_date)
        assertEquals(movieDetail.title, movieList.results[0].title)
    }

    @Test  // 에러 상황에 대한 테스트
    fun `Error - Exception received from service call`() {
        // prepare - (7) 에러의 발생
        apiMock = mock {
            onBlocking { getMovieListCo(param) } doAnswer { throw Throwable() }
        }

        // call
        val movieManager = MovieManager(apiMock)
        assertFailsWith<Throwable> { // (8)
            runBlocking {
                movieManager.getMovieList(param)
            }
        }
    }

    // 코루틴 코드를 위해
    private fun testBlocking(block: suspend CoroutineScope.() -> Unit) {
        runBlocking(Dispatchers.Default, block)
    }
}