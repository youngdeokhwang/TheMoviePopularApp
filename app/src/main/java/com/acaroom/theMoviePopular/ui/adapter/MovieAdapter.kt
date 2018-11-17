package com.acaroom.theMoviePopular.ui.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.acaroom.theMoviePopular.data.MovieItem
import java.util.*

class MovieAdapter(listener: MovieItemAdapter.ViewSelectedListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // MOVIE 혹은 LOADING 아이템의 종류를 파악하기 위해
    private var items: ArrayList<ViewType>

    // 두 종류의 어댑터를 위한 배열 컬렉션
    private var delegateAdapters = SparseArrayCompat<ItemAdapter>()

    private val loadingItem = object : ViewType {
        override fun getViewType() = AdapterType.LOADING
    }

    // 생성시 초기화 되는 블록
    init {
        delegateAdapters.put(AdapterType.LOADING, LoadingItemAdapter())
        delegateAdapters.put(AdapterType.MOVIE, MovieItemAdapter(listener))
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            delegateAdapters.get(viewType).onCreateViewHolder(parent)


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun addMovieList(movieList: List<MovieItem>) {
        // 초기 위치 제거 및 알리기
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        // 모든 목록을 추가하고 마지막은 로딩용 아이템 추가
        items.addAll(movieList)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition, items.size + 1 /* 로딩용으로 하나 추가 */)
    }
    // 삭제하고 추가하기
    fun clearAndAddMovieList(movieList: List<MovieItem>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        items.addAll(movieList)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
    }
    // 뉴스 가져오기
    fun getMovieList(): List<MovieItem> = items
                .filter { it.getViewType() == AdapterType.MOVIE }
                .map { it as MovieItem }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}