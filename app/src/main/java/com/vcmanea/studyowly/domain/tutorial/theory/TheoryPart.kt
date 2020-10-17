package com.vcmanea.studyowly.domain.tutorial.theory

const val THEORY_TITLE="Title"
const val THEORY_SUBTITLE="Subtitle"
const val THEORY_BODY="Body"
const val THEORY_CODE="Code"
const val THEORY_IMAGE="Image"
const val THEORY_IMPORTANT="Part"


class TheoryPart(val theory_id:Long=0,val type:String="", val content:String="",val order: Int=0) {


}