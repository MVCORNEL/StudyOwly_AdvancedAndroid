package com.vcmanea.studyowly.ui.tutorial

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vcmanea.studyowly.domain.tutorial.Lesson
import com.vcmanea.studyowly.domain.tutorial.quiz.Quiz
import com.vcmanea.studyowly.domain.tutorial.theory.Theory
import com.vcmanea.studyowly.ui.quiz.QuizFragment
import com.vcmanea.studyowly.ui.theory.TheoryFragment

class TutorialViewPager(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    var tutorials: ArrayList<Lesson> = ArrayList()

    fun updateList(lessonList: ArrayList<Lesson>?) {
        lessonList?.let {
            tutorials = lessonList
            if ((tutorials).isNotEmpty()) {
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return tutorials.size
    }

    //dependency injection here
    override fun createFragment(position: Int): Fragment {
        //call back here
        return when (tutorials[position]) {
            is Quiz -> QuizFragment.newInstance(position)
            is Theory -> TheoryFragment.newInstance(position)
            else -> throw IllegalStateException("Unknown fragment type")
        }
    }

}