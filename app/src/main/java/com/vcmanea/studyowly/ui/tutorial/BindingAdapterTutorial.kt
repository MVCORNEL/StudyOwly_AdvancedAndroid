package com.vcmanea.studyowly.ui.tutorial

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.vcmanea.studyowly.domain.tutorial.Lesson
import timber.log.Timber

//----------------------------------------------------------    TUTORIAL   --------------------------------------------------------------
//PAGER
@BindingAdapter("getLessonsList","getUnlockedElements")
fun bindSectionList(viewPager2: ViewPager2, lessonList: ArrayList<Lesson>?, completedLessons: Int) {
        Timber.d("PAGER data changed list size is : ${lessonList?.size}, unlocked number of elements is :${completedLessons}")
        val pager = viewPager2.adapter as TutorialViewPager
        pager.updateList(lessonList,completedLessons)
    }

//PROGRESS BAR
//Problem here as the all progress abr is redraw
@BindingAdapter("setMax")
fun bindMax(progressBar: MyProgressBar, lessonListSize: Int) {
            Timber.d("progressBar list size as MAX is : $lessonListSize")
            progressBar.setListSize(lessonListSize)

}

@BindingAdapter("setProgress")
fun bindProgress(progressBar: MyProgressBar, progress: Int) {

    progressBar.setProgressFromHere(progress)
     Timber.d("progressBar list progress is : $progress")
}
