package com.vcmanea.studyowly.domain.tutorial

open class Lesson(val id: Long, val chapterId: Long) : Comparable<Lesson> {

    var isCompleted = false
    var listener: OnLessonCompleted? = null

    fun setOnCompleteListener(onLessonCompleted: OnLessonCompleted) {
        listener = onLessonCompleted
    }

    interface OnLessonCompleted {
        fun onLessonCompleted()
    }

    override fun compareTo(other: Lesson): Int {
        if (this.id < other.id) {
            return -1
        }
        else if (this.id == other.id) {
            return 0
        }
        else return 1
    }

    //Returns true if the lesson is completed for the first time
    fun completeLesson() {
            isCompleted = true
        if (listener==null) {
          throw NotImplementedError("OnLessonCompleted must be implement by each lesson")
        }
        listener?.onLessonCompleted()
        }

    }

