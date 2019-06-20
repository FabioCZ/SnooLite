package com.gottlicher.snoolite.post


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gottlicher.snoolite.R
import com.gottlicher.snoolite.api.EMPTY_POST
import com.gottlicher.snoolite.api.RedditApiService
import com.gottlicher.snoolite.databinding.FragmentPostBinding
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.toolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import org.koin.android.ext.android.inject


class PostFragment : Fragment() {

    val redditApiService:RedditApiService by inject()
    val args: PostFragmentArgs by navArgs()

    var isLoading:Boolean = false
        set(value) { loading_container.visibility = if (value) View.VISIBLE else View.GONE }

    lateinit var binding:FragmentPostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(LayoutInflater.from(context), container, false)
        binding.post = EMPTY_POST
        GlobalScope.launch (Dispatchers.Main) { loadPost(args.permalink) }
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = resources.getString(R.string.comments)
        toolbar.navigationIconResource = R.drawable.ic_arrow_back_white_24dp
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }
    suspend fun loadPost(permalink:String) {
        isLoading = true
        try {
            val response = redditApiService.getPermalink(permalink)
            if (response.isSuccessful) {
                binding.post = response.body()!!.post

                val lm = LinearLayoutManager(context)
                comments_rv.layoutManager = lm
                val adapter = CommentsAdapter()
                adapter.submitList(response.body()!!.comments)

                val divider = DividerItemDecoration(context, lm.orientation)
                comments_rv.addItemDecoration(divider)
                comments_rv.adapter = adapter

            }
        } finally {
            isLoading = false
        }
    }
}
