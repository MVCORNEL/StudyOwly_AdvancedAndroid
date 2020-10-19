package com.vcmanea.studyowly.ui.chapter

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vcmanea.studyowly.R
import com.vcmanea.studyowly.domain.chapter.Chapter


//Chapter RV
@BindingAdapter("getChapterList")
fun bindLearnRecyclerView(recyclerView: RecyclerView, chaptersList: List<Chapter>?) {
    chaptersList?.let {
        Log.d("BindingAdapter", "${chaptersList.size}")
        val adapter = recyclerView.adapter as ChapterGridAdapter
        adapter.submitList(chaptersList)
    }
}

@BindingAdapter("imgUrl")
//The first parameter is the view parameter and is specified as an ImageView, wich means that only ImageView and any derived classes can use this adapter
fun bindImage(imageView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Picasso.get().load(imgUrl).placeholder(R.drawable.google_loading_animation).error(R.drawable.google_ic_broken_image).into(imageView)
    }
}