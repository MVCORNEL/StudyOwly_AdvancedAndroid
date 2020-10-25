

package com.vcmanea.studyowly.domain.tutorial.theory

import com.vcmanea.studyowly.domain.tutorial.Lesson

class Theory(id:Long=0,chapter_id:Long=0) : Lesson(id,chapter_id){

    override var isCompleted: Boolean=true


    val theoryParts: ArrayList<TheoryPart> = ArrayList()
    fun addPartList(theoryPartList: List<TheoryPart>) {
        theoryParts.clear()
        theoryParts.addAll(theoryPartList)
    }


    override fun compareTo(other: Lesson): Int {
        if(this.id<other.id){
            return -1
        }
        else if(this.id==other.id){
                return 0
            }
        else return 1
    }
}