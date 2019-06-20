package com.gottlicher.snoolite.about

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.gottlicher.snoolite.R
import kotlinx.android.synthetic.main.fragment_post.*
import org.jetbrains.anko.appcompat.v7.navigationIconResource


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = resources.getString(R.string.about)
        toolbar.navigationIconResource = R.drawable.ic_arrow_back_white_24dp
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }
}
