package com.vcmanea.studyowly.ui.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs

import androidx.viewpager2.widget.ViewPager2
import com.vcmanea.studyowly.databinding.FragmentTutorialBinding
import com.vcmanea.studyowly.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


const val TUTORIAL_ARG = "TUTORIAL_ARG"

@AndroidEntryPoint
class TutorialFragment : Fragment(){


    //PAGER
    private lateinit var fragmentPager: ViewPager2
    private lateinit var tutorialPagerAdapter: TutorialViewPager

    //VIEWMODEL
    private lateinit var factory: TutorialViewModel.Factory
    private val viewModel: TutorialViewModel by viewModels{factory}
    private lateinit var binding: com.vcmanea.studyowly.databinding.FragmentTutorialBinding
    //REPO
    @Inject  lateinit var repository:Repository
    //ARGUMENTS
    private val args: TutorialFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //BINDING
        binding = FragmentTutorialBinding.inflate(inflater)
        binding.lifecycleOwner = this

        //VIEW MODEL
        val application= requireNotNull(this.activity).application
        factory=TutorialViewModel.Factory(args.tutorialID,repository)
        binding.viewmodel = viewModel

        // PAGER
        fragmentPager = binding.sectionPager
        fragmentPager.setPageTransformer(ZoomOutPageTransformer())
        tutorialPagerAdapter = TutorialViewPager(this, null)
        fragmentPager.adapter = tutorialPagerAdapter

        // PAGER
        fragmentPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                //update the position of the progressbar
//                viewModel.pagerPositionChanged(position)
            }
        })

        return binding.root

    }

    companion object {
        fun newInstance(tutorial: String) = TutorialFragment().apply {
            arguments = Bundle().apply { putString(TUTORIAL_ARG, tutorial) }
        }
    }
}