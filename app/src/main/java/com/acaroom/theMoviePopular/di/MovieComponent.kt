package com.acaroom.theMoviePopular.di

import com.acaroom.theMoviePopular.ui.MovieFragment
import dagger.Component
import javax.inject.Singleton

/**
 * 무비 컴포넌트
 * 지정된 각각의 모듈은 Dagger에 의해 의존성을 가지는 필요한 곳에 제공됨.
 * Component는 필요할 때마다 객체를 생성하고 저장하는 책임을 갖는다.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, MovieModule::class, NetworkModule::class))
interface MovieComponent {

    /**
     * MovieFragment에서 의존성이 필요한 곳에 제공/주입을 하게된다.
     */
    fun inject(movieFragment: MovieFragment)
}