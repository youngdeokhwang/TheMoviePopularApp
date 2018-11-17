package com.acaroom.theMoviePopular

import android.app.Application
import com.acaroom.theMoviePopular.di.DaggerMovieComponent
import com.acaroom.theMoviePopular.di.MovieComponent

/**
 * 애플리케이션 사이의 공동 멤버 접근
 * 이후 context를 통해 접근할 수 있다.
 */
class TheMoviePopularApp : Application() {

    // 이 멤버는 어디서든 접근이 가능해 진다.
    companion object {
        // 의존성 컴포넌트 초기화를 위해
        lateinit var movieComponent: MovieComponent
    }

    override fun onCreate() {
        super.onCreate()
        // Dagger에 의해 자동 생성된다.
        movieComponent = DaggerMovieComponent.builder().build()
    }
}