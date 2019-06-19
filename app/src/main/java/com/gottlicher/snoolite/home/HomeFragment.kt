package com.gottlicher.snoolite.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.gottlicher.snoolite.R
import com.gottlicher.snoolite.api.DataState
import com.gottlicher.snoolite.api.RedditPost
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    val vm:HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        posts_rv.layoutManager = LinearLayoutManager(this.context)
        val adapter = PostsAdapater()
        vm.postsLiveData.observe(this, Observer<PagedList<RedditPost>> { t -> adapter.submitList(t) })
        vm.stateLiveData.observe(this, Observer<DataState> { t -> adapter.setDataState(t)})
        posts_rv.adapter = adapter
    }
}
