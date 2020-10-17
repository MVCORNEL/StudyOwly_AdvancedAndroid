package com.vcmanea.studyowly.domain.chapter

data class Chapter(val title: String="empty", val state: Int = COMPLETED, val imgUrl:String="", var id: Long = 0L) {

    companion object {
        const val COMPLETED = 0
        const val DUE = 1
        const val LOCKED = 2
    }
}