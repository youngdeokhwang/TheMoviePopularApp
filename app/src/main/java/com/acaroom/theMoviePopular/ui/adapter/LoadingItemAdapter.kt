package com.acaroom.theMoviePopular.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.acaroom.theMoviePopular.R
import com.acaroom.theMoviePopular.utils.inflate

class LoadingItemAdapter : ItemAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class LoadingViewHolder(parent: ViewGroup) :
            RecyclerView.ViewHolder(parent.inflate(R.layout.item_loading))
}