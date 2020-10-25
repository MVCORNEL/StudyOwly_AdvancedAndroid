package com.vcmanea.studyowly.ui.tutorial

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.vcmanea.studyowly.domain.tutorial.Lesson
import timber.log.Timber

//----------------------------------------------------------    TUTORIAL   --------------------------------------------------------------
//PAGER
@BindingAdapter("getLessonsList")
fun bindSectionList(viewPager2: ViewPager2, lessonList: ArrayList<Lesson>?) {
    lessonList?.let {
        Timber.d("PAGER data changed list size is : ${lessonList.size}}")
        val pager = viewPager2.adapter as TutorialViewPager
        //ALL THE LESSON WILL BE INITIALIZED FIRST IN ORDER AS THEIR DATA TO REMAIN THE SAME, WHEN WE SCROLL BACK
        viewPager2.offscreenPageLimit = lessonList.size
        pager.updateList(lessonList)
    }
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
