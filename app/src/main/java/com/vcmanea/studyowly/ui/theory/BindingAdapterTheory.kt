package com.vcmanea.studyowly.ui.theory

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import com.vcmanea.studyowly.R
import com.vcmanea.studyowly.domain.tutorial.theory.CodeHighlighter
import com.vcmanea.studyowly.domain.tutorial.theory.*
import com.vcmanea.studyowly.domain.tutorial.theory.TheoryPart


//----------------------------------------------------------    THEORY    --------------------------------------------------------------
@BindingAdapter("setTheoryParts")
fun getTheoryParts(linearLayout: LinearLayout, partsList: ArrayList<TheoryPart>?) {
    val inflater = LayoutInflater.from(linearLayout.context)
    partsList?.forEach {

        when (it.type) {
            THEORY_TITLE  -> {
                val title = inflater.inflate(R.layout.theory_title, linearLayout, false) as MaterialTextView
                title.text = it.content
                linearLayout.addView(title)
            }
            THEORY_SUBTITLE -> {
                val subTitle = inflater.inflate(R.layout.theory_subtitle, linearLayout, false) as MaterialTextView
                subTitle.text = it.content
                linearLayout.addView(subTitle)
            }

            THEORY_BODY -> {
                val txtBody = inflater.inflate(R.layout.theory_body, linearLayout, false)
                val txt = txtBody as MaterialTextView
                txt.text = it.content
                linearLayout.addView(txtBody)
            }

            THEORY_CODE -> {
                val layout = inflater.inflate(R.layout.theory_code, linearLayout, false) as ConstraintLayout
                val codeTxt = layout.findViewById<MaterialTextView>(R.id.theory_codeTxt)

                if (codeTxt.parent != null) {
                    ((codeTxt.parent) as ViewGroup).removeView(codeTxt)
                }

                val spannableText = CodeHighlighter.parseCode(it.content)
                codeTxt.text = spannableText
                linearLayout.addView(codeTxt)
            }

            THEORY_IMPORTANT -> {
                val layout = inflater.inflate(R.layout.theory_important, linearLayout, false) as ConstraintLayout
                val importantTxt = layout.findViewById<MaterialTextView>(R.id.theory_importantTxt)

                if (importantTxt.parent != null) {
                    ((importantTxt.parent) as ViewGroup).removeView(importantTxt)
                }

                importantTxt.text = it.content
                linearLayout.addView(importantTxt)
            }

            THEORY_IMAGE -> {
                //TODO The logic behind adding the picture
                val imgView = inflater.inflate(R.layout.theory_image, linearLayout, false) as ImageView
                imgView.setImageResource(R.drawable.ic_baseline_radio_button_checked_24)
                linearLayout.addView(imgView)
            }
            else->{

            }
        }
    }
}