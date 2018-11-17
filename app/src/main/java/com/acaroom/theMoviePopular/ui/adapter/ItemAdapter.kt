package com.acaroom.theMoviePopular.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

interface ItemAdapter {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}