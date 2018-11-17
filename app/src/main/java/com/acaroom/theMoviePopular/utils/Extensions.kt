@file:JvmName("ExtensionsUtils")

package com.acaroom.theMoviePopular.utils

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.acaroom.theMoviePopular.R
import com.squareup.picasso.Picasso

/**
 * ViewGroup에 확장함수 inflate를 정의 -> ui/MovieFragment 에서 사용
 * attachToRoot는 기본값 false를 가지기 때문에 생략 할 수 있다
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

/**
 * ImageView의 확장함수로 loadImg를 정의
 * Picasso 라이브러리를 이용해 이미지를 가져다 this(ImageView)에 로드한다
 */
fun ImageView.loadImg(imageUrl: String) {
    if (TextUtils.isEmpty(imageUrl)) {
        Picasso.get().load(R.mipmap.ic_launcher).into(this)
    } else {
        Picasso.get().load(imageUrl)
                .placeholder(R.drawable.img_default) // 로드되지 않은 경우 기본 이미지
                //.resize(300,300)  // 300x300 픽셀
                .centerCrop() // 중앙을 기준으로 잘라내기 (전체 이미지가 약간 잘릴수 있다)
                .fit() // 이미지 늘림 없이 ImageView에 맞춤
                .into(this) // this인 ImageView에 로드
    }
}

fun <T> androidLazy(initializer: () -> T) : Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)