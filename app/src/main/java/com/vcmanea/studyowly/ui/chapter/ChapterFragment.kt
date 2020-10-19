package com.vcmanea.studyowly.ui.chapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.vcmanea.studyowly.databinding.FragmentChapterBinding
import dagger.hilt.android.AndroidEntryPoint

const val CHAPTER_ARG = "CHAPTER_ARG"
@AndroidEntryPoint
class ChapterFragment : Fragment() {

    private lateinit var binding: FragmentChapterBinding
     private val viewModel: ChapterViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChapterBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        //RECYCLER VIEW
        val recyclerView = binding.chaptersRV
        val gridLayoutManager = GridLayoutManager(context, 2)
        //Spanning algorithm
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position % 5 == 0) {
                    return 2
                }
                return 1
            }
        }


        //ADAPTER with onClickListener Listener
        val adapter = ChapterGridAdapter(ChapterGridAdapter.ChapterListener {
            viewModel.displayTutorial(it)
            viewModel.updateChapter(it)
        })

        //NAVIGATION
        //observe navigate
        viewModel.navigateToTutorial.observe(viewLifecycleOwner, Observer<Long> {
            if(null!=it){
                val action=ChapterFragmentDirections.actionNavigationFragmentToTutorialFragment(it)
                findNavController().navigate(action)
                //this is a must if not you won't be able to return back, by pressing back button because the value will not be null
                viewModel.displayTutorialComplete()
            }

        })


        recyclerView.layoutManager=gridLayoutManager
        recyclerView.adapter = adapter
        return binding.root

    }



    companion object {
        fun newInstance(position: Int) = ChapterFragment().apply {

            arguments = Bundle().apply { putInt(CHAPTER_ARG, position) }
        }
    }
}