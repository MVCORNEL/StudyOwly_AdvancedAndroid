package com.vcmanea.studyowly.ui.theory

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.vcmanea.studyowly.databinding.FragmentTheoryBinding
import com.vcmanea.studyowly.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val THEORY_ARG = "THEORY_ARG"

@AndroidEntryPoint
class TheoryFragment : Fragment() {
    //LISTENER
    interface OnNextPageTheory {
        fun onNextPageTheory()
    }
    lateinit var pageListener: OnNextPageTheory
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is OnNextPageTheory) {
            pageListener = parentFragment as OnNextPageTheory
        }
        else {
            throw RuntimeException("The parent fragment must implement OnNextPageListener")
        }
    }
    //REPO
    @Inject lateinit var repository: Repository
    //VIEW-MODEL
    private lateinit var theoryViewModelFactory: TheoryViewModel.TheoryViewModelFactory
    private val viewModel: TheoryViewModel by viewModels { theoryViewModelFactory }
    //DATA-BINDING
    private lateinit var binding: FragmentTheoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //ARGUMENT
        val numberParameter = arguments?.getInt(THEORY_ARG)
        //VIEW-MODEL-FACTORY
        numberParameter?.let {
            theoryViewModelFactory = TheoryViewModel.TheoryViewModelFactory(numberParameter,repository)
        }
        //BINDING
        binding = FragmentTheoryBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        //OBSERVE NEXT PAGE
        viewModel.nextPagerElement.observe(viewLifecycleOwner, {
            it?.let {
                if (it) {
                    pageListener.onNextPageTheory()
                    viewModel.doneNextPage()
                }
            }
        })
        return binding.root
    }

    companion object {
        fun newInstance(position: Int) = TheoryFragment().apply {
            arguments = Bundle().apply { putInt(THEORY_ARG, position) }
        }
    }

}