package com.vcmanea.studyowly.ui.tutorial

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vcmanea.studyowly.domain.tutorial.Lesson
import com.vcmanea.studyowly.domain.tutorial.Tutorial

class TutorialViewPager(fragment: Fragment, var tutorial: Tutorial?) :
    FragmentStateAdapter(fragment) {

    var tutorials: ArrayList<Lesson> = ArrayList()
    private var unlockedElements: Int = 0


    fun updateList(lessonList: ArrayList<Lesson>?, unlockedElements: Int) {
        lessonList?.let {
            tutorials = lessonList
            this.unlockedElements = unlockedElements
            if ((tutorials).isNotEmpty()) {
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        //UNLOCKED ELEMENTS
       return unlockedElements
//        return tutorials.size
    }

    //dependency injection here
    override fun createFragment(position: Int): Fragment {
        //call back here
        return when (tutorials[position]) {
//            is Quiz -> QuizFragment.newInstance(position)
//            is Theory -> TheoryFragment.newInstance(position)
            else -> throw IllegalStateException("Unknown fragment type")
        }
    }
}