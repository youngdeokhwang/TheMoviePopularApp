package com.acaroom.theMoviePopular.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.acaroom.theMoviePopular.R
import com.acaroom.theMoviePopular.data.MovieItem
import com.acaroom.theMoviePopular.utils.inflate
import com.acaroom.theMoviePopular.utils.loadImg
import kotlinx.android.synthetic.main.item_movie.view.*
import org.jetbrains.anko.design.snackbar

class MovieItemAdapter(val viewActions: ViewSelectedListener) : ItemAdapter {

    interface ViewSelectedListener {
        fun onItemSelected(url: String?)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return MovieViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as MovieViewHolder
        holder.bind(item as MovieItem)
    }

    // 이너 클래스에서는 바깥 클래스의 프로퍼티 등을 접근 할 수 있다.
    inner class MovieViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_movie)) {

        private val imgPoster = itemView.img_poster
        private val overview = itemView.tv_overview
        private val releaseDate = itemView.tv_release_date
        private val voteCount = itemView.tv_vote_count
        private val tvTitle = itemView.tv_title
        private val tvAverage = itemView.rate_vote_avg
        private val btnReserve = itemView.btn_reserve

        fun bind(item: MovieItem) {
            imgPoster.loadImg("https://image.tmdb.org/t/p/w500/${item.poster_path}")
            overview.text = item.overview
            releaseDate.text = item.release_date
            voteCount.text = "${item.vote_count} 투표"
            tvTitle.text = item.title
            tvAverage.rating = item.vote_average / 2

            // 이너클래스 이므로 viewActions은 바깥 클래스의 멤버이지만 접근할 수 있다.
            super.itemView.setOnClickListener {
                viewActions.onItemSelected("https://image.tmdb.org/t/p/w500/${item.poster_path}")
            }

            // 영화 예약에 관련된 클릭 이벤트 처리
            btnReserve.setOnClickListener {
                it.snackbar("스낵바입니다.")
            }
        }
    }

}