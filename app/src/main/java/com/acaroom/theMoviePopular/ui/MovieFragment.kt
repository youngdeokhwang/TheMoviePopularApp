package com.acaroom.theMoviePopular.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acaroom.theMoviePopular.TheMoviePopularApp
import com.acaroom.theMoviePopular.R
import com.acaroom.theMoviePopular.data.API_KEY
import com.acaroom.theMoviePopular.data.MovieList
import com.acaroom.theMoviePopular.ui.adapter.MovieAdapter
import com.acaroom.theMoviePopular.ui.adapter.MovieItemAdapter
import com.acaroom.theMoviePopular.utils.androidLazy
import com.acaroom.theMoviePopular.utils.inflate
import kotlinx.android.synthetic.main.frag_recycler.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject

class MovieFragment : RxBaseFragment(), MovieItemAdapter.ViewSelectedListener {


    companion object {
        private val KEY_THE_MOVIE = "theMoviePopular"
    }

    // lateinit이 없다면 DI를 통해 외부에서 주입받기 때문에 별도로 해당 프로퍼티에
    // 명시적으로 null을 대입하지 않는 이상 Non-null이라고 확신할 수 있다.
    // 하지만 코드를 Kotlin에서 그대로 사용하면 Kotlin에서는 컴파일 에러가 발생할
    // 것이기 때문에 lateinit으로 초기화를 미룬다.
    @Inject lateinit var movieManager: MovieManager

    private var theMovieList: MovieList? = null
    private val movieAdapter by androidLazy { MovieAdapter(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //컴포넌트에 의해 의존성 모듈이 주입된다.
        TheMoviePopularApp.movieComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // 프레그먼트 띄우기
        // 기존 코드 inflater.inflate(R.layout.frag_recycler, container, false) 를 교체
        // (1) ViewGroup의 확장 함수 inflate() 사용 -> commons/Extensions.kt
        return container?.inflate(R.layout.frag_recycler)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // RecyclerView의 리소스 id
        rv_movie_list.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({ requestMovie() }, linearLayout))
        }

        rv_movie_list.adapter = movieAdapter

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_THE_MOVIE)) {
            theMovieList = savedInstanceState.get(KEY_THE_MOVIE) as MovieList
            movieAdapter.clearAndAddMovieList(theMovieList!!.results)
        } else {
            requestMovie()
        }
    }

    override fun onItemSelected(url: String?) {
        if (url.isNullOrEmpty()) {
            Snackbar.make(rv_movie_list, "No URL assigned to this results", Snackbar.LENGTH_LONG).show()
        } else {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val movie = movieAdapter.getMovieList()
        if (theMovieList != null && movie.isNotEmpty()) {
            outState.putParcelable(KEY_THE_MOVIE, theMovieList?.copy(results = movie))
        }
    }

    /**
     * 목록 요청을 통해 영화 목록을 가져온다
     */
    private fun requestMovie() {

        job = GlobalScope.launch(Dispatchers.Main) {
            try {
                val param = mapOf(
                        "page" to (theMovieList?.page).toString(),
                        "api_key" to API_KEY,
                        "sort_by" to "popularity.desc",
                        "language" to "ko"
                        )
                val retrievedMovie = movieManager.getMovieList(param)
                retrievedMovie.page = retrievedMovie.page?.plus(1)
                theMovieList = retrievedMovie

                movieAdapter.addMovieList(retrievedMovie.results)
            } catch (e: Throwable) {
                if (isVisible) {
//                    Snackbar.make(rv_movie_list, e.message.orEmpty(), Snackbar.LENGTH_INDEFINITE)
//                            .setAction("RETRY") { requestMovie() }
//                            .show()
                    rv_movie_list.snackbar(e.message.orEmpty(), "RETRY") {
                        requestMovie()
                    }
                }
            }
        }
    }
}

