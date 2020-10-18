package com.vcmanea.studyowly.domain.chapter

data class Chapter(val title: String="empty", val isComplete: Int = NOT_COMPLETED
                   , val imgUrl:String="", var id: Long = 0L) {

    companion object {
        const val NOT_COMPLETED = 0
        const val COMPLETED = 1
    }
}