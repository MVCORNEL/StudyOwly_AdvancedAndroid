package com.vcmanea.studyowly.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vcmanea.studyowly.databinding.RowChapterCompletedBinding
import com.vcmanea.studyowly.databinding.RowChapterNotCompletedBinding
import com.vcmanea.studyowly.domain.chapter.Chapter


private const val CHAPTER_COMPLETED_TYPE = 0
private const val CHAPTER_DUE_TYPE = 1

class ChapterGridAdapter(private val chpaterListener: ChapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var chapterList: List<Chapter> = ArrayList()

    fun submitList(list: List<Chapter>) {
        chapterList = list
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return chapterList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (chapterList.get(position).isComplete) {
            Chapter.COMPLETED -> CHAPTER_COMPLETED_TYPE
            Chapter.NOT_COMPLETED -> CHAPTER_DUE_TYPE
            else -> throw ClassCastException("Unknown viewType")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CHAPTER_COMPLETED_TYPE -> ChapterCompleteHolder.from(parent)
            CHAPTER_DUE_TYPE -> ChapterDueHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val learnChapter = chapterList.get(position)
        when (holder) {

            is ChapterDueHolder ->
                holder.bindTo(learnChapter, chpaterListener)


            is ChapterCompleteHolder ->
                holder.bindTo(learnChapter, chpaterListener)


            else ->{
                Log.d("Unknown viewType", "error")

            }
        }
    }

    //HOLDER NOT_COMPLETED
    class ChapterDueHolder(private var binding: RowChapterNotCompletedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ChapterDueHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowChapterNotCompletedBinding.inflate(layoutInflater)
                return ChapterDueHolder(binding)
            }
        }

        fun bindTo(chapter: Chapter, chapterListener: ChapterListener) {
            binding.chapter = chapter
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.clickListener = chapterListener
            binding.executePendingBindings()

        }
    }

    //HOLDER COMPLETED
    class ChapterCompleteHolder(private var binding: RowChapterCompletedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ChapterCompleteHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowChapterCompletedBinding.inflate(layoutInflater)
                return ChapterCompleteHolder(binding)
            }
        }

        fun bindTo(chapter: Chapter, chapterListener: ChapterListener) {
            binding.chapter = chapter
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.clickListener = chapterListener
            binding.executePendingBindings()
        }
    }




    class ChapterListener(val clickListener: (chapterId: Long) -> Unit) {
        fun onClick(chapterDetails: Chapter) = clickListener(chapterDetails.id)
    }
}