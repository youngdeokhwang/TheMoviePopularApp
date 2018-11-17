package com.acaroom.theMoviePopular.ui

import android.support.v4.app.Fragment
import kotlinx.coroutines.Job

open class RxBaseFragment : Fragment() {

    protected var job: Job? = null

    override fun onResume() {
        super.onResume()
        job = null
    }

    override fun onPause() {
        super.onPause()
        job?.cancel()
        job = null
    }
}