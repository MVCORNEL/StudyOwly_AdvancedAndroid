package com.vcmanea.studyowly.ui.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.vcmanea.studyowly.databinding.FragmentQuizBinding
import com.vcmanea.studyowly.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val QUIZ_ARG = "QUIZ_ARG"

@AndroidEntryPoint
class QuizFragment : Fragment() {
    //LISTENER
    interface OnNextPageQuiz {
        fun onNextPageQuiz()
    }
    lateinit var pageListener: OnNextPageQuiz
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is OnNextPageQuiz) {
            pageListener = parentFragment as OnNextPageQuiz
        }
        else {
            throw RuntimeException("The parent fragment must implement OnNextPageListener")
        }
    }
    //VIEW-MODEL
    private lateinit var quizViewModelFactory: QuizViewModel.QuizViewModelFactory
    private val viewModel: QuizViewModel by viewModels { quizViewModelFactory }
    //DATA-BINDING
    private lateinit var binding: FragmentQuizBinding
    //REPO
    @Inject lateinit var repository: Repository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //ARGUMENT
        val numberParameter = arguments?.getInt(QUIZ_ARG)
        //VIEW-MODEL-FACTORY
        numberParameter?.let {
            quizViewModelFactory = QuizViewModel.QuizViewModelFactory(numberParameter,repository)
        }
        //BINDING
        binding = FragmentQuizBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        //HANDLER
        binding.handler = SelectHandler {
            val tagKey = it.tag as String
            viewModel.onChoiceSelected(tagKey)
        }

        //OBSERVE NEXT PAGE
        viewModel.nextPagerElement.observe(viewLifecycleOwner, {
            it?.let {
                if (it) {
                    pageListener.onNextPageQuiz()
                    viewModel.doneNextPage()
                }
            }
        })
        return binding.root
    }


    companion object {
        fun newInstance(position: Int) = QuizFragment().apply {
            arguments = Bundle().apply { putInt(QUIZ_ARG, position) }
        }
    }
}
// Select choice
class SelectHandler(val clickListener: (view: View) -> Unit) {
    fun onSelected(view: View) = clickListener(view)
}