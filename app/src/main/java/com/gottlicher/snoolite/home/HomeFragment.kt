package com.gottlicher.snoolite.home


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.gottlicher.snoolite.R
import com.gottlicher.snoolite.api.RedditPost
import com.gottlicher.snoolite.databinding.FragmentHomeBinding
import com.gottlicher.snoolite.dialogs.AsyncInputDialog
import com.gottlicher.snoolite.dialogs.DialogResult
import com.gottlicher.snoolite.post.PostFragment
import com.gottlicher.snoolite.post.PostFragmentArgs
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    val vm:HomeViewModel by viewModel()
    lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(this.context),container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!vm.initialized) {
            vm.initLiveData()
        }
        initContent()
        main_fab.onClick {
            val newSub = AsyncInputDialog(R.string.go_to_sub, R.string.r_slash_hint, R.string.ok, R.string.cancel).show(this@HomeFragment.context!!)
            if (newSub.dialogResult == DialogResult.POSITIVE) {
                vm.currentSub.set(newSub.text)
                vm.initLiveData()
                initContent()
            }
        }

        nav_bar_about.onClick { findNavController().navigate(R.id.aboutFragment) }
        nav_bar_refresh.onClick {
            vm.initLiveData()
            initContent()
        }
    }

    private fun initContent(){
        binding.loading = vm.stateLiveData
        posts_rv.layoutManager = LinearLayoutManager(this.context)
        val adapter = PostsAdapater()
        vm.postsLiveData.observe(this, Observer<PagedList<RedditPost>> { t -> adapter.submitList(t) })

        adapter.onClick = { clickedItem ->
            val action = HomeFragmentDirections.actionHomeFragmentToPostFragment(clickedItem.permalink)
            findNavController().navigate(action)
        }
        posts_rv.adapter = adapter
        toolbar.title = resources.getString(R.string.browsing_r_pl,vm.currentSub.get())
    }
}
