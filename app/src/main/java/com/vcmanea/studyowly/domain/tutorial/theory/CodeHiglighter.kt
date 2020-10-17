package com.vcmanea.studyowly.domain.tutorial.theory

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import java.util.regex.Pattern

object CodeHighlighter  {

        //keywords
        private const val BLUE = "#5b87e7"
        private const val regexBlue = "(?<=<blue>).*?(?=</blue>)"

        //quotes
        private const val GREEN = "#39915b"
        private const val regexGreen = "(?<=<green>).*?(?=</green>)"

        //classes with first letter in upper case
        private const val PURPLE = "#a86fce"
        private const val regexPurple = "(?<=<purple>).*?(?=</purple>)"

        //comments
        private const val RED = "#d82466"
        private const val regexRED = "(?<=<red>).*?(?=</red>)"

        fun parseCode(text: String): SpannableStringBuilder {

            val spannableString = SpannableStringBuilder(text)
            var pattern = Pattern.compile(regexBlue)
            var matcher = pattern.matcher(spannableString)
            //1 KEYWORDS
            while (matcher.find()) {
                spannableString.setSpan(ForegroundColorSpan(Color.parseColor(BLUE)), matcher.start(), matcher.end(), 0)
                spannableString.delete(matcher.start() - 6, matcher.start())
                spannableString.delete(matcher.end() - 6, matcher.end() + 1)
            }
            //2 QUOTES
            pattern = Pattern.compile(regexGreen)
            matcher.reset()
            matcher = pattern.matcher(spannableString)
            while (matcher.find()) {
                spannableString.setSpan(ForegroundColorSpan(Color.parseColor(GREEN)), matcher.start(), matcher.end(), 0)
                spannableString.delete(matcher.start() - 7, matcher.start())
                spannableString.delete(matcher.end() - 7, matcher.end() + 1)
            }

            //3 CLASSES
            pattern = Pattern.compile(regexPurple)
            matcher.reset()
            matcher = pattern.matcher(spannableString)
            while (matcher.find()) {
                spannableString.setSpan(ForegroundColorSpan(Color.parseColor(PURPLE)), matcher.start(), matcher.end(), 0)
                spannableString.delete(matcher.start() - 8, matcher.start())
                spannableString.delete(matcher.end() - 8, matcher.end() + 1)
            }

            //4 COMMENTS
            pattern = Pattern.compile(regexRED)
            matcher.reset()
            matcher = pattern.matcher(spannableString)
            while (matcher.find()) {
                spannableString.setSpan(ForegroundColorSpan(Color.parseColor(RED)), matcher.start(), matcher.end(), 0)
                spannableString.delete(matcher.start() - 5, matcher.start())
                spannableString.delete(matcher.end() - 5, matcher.end() + 1)
            }

            return spannableString
        }
    }
