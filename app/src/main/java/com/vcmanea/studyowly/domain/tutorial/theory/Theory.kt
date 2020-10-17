

package com.vcmanea.studyowly.domain.tutorial.theory

import com.vcmanea.studyowly.domain.tutorial.Lesson

class Theory(id:Long=0,chapter_id:Long=0) : Lesson(id,chapter_id){

    val theoryTheoryParts: ArrayList<TheoryPart> = ArrayList()
    fun addPartList(theoryPartList: List<TheoryPart>) {
        theoryTheoryParts.addAll(theoryPartList)
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