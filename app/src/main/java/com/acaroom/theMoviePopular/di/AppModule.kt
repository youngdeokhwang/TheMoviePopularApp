package com.acaroom.theMoviePopular.di

import android.app.Application
import android.content.Context
import com.acaroom.theMoviePopular.TheMoviePopularApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Application 클래스는 보통 context객체를 통해 안드로이드의 여러 요소 간 사용할 공통의 내용을 접근
 */
@Module
class AppModule(val app: TheMoviePopularApp) {

    /**
     * provideContext()는 Application의 의존성 객체인 컨텍스트를 제공
     * @Singleton은 Dagger API는 아니고 javax.annotation 패키지에 포함된 어노테이션으로
     * 인스턴스가 오로지 하나 이어야만 한다는 것
     */
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication() : Application = app
}
