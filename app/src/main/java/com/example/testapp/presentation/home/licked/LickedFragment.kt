package com.example.testapp.presentation.home.licked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.FragmentLickedBinding // Сгенерированный класс
import com.example.testapp.domain.model.Course
import com.example.testapp.presentation.home.common.adapter.CoursesAdapter
import com.example.testapp.presentation.home.common.viewModel.BaseCoursesViewModel
import com.example.testapp.presentation.model.UiInfoState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LickedFragment : Fragment() {

    private var _binding: FragmentLickedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LickedViewModel by viewModels()

    private lateinit var coursesAdapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLickedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindToViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCourses()
    }

    private fun bindToViewModel() {
        initRecyclerView()
        observeFlows()
    }

    private fun initRecyclerView() {
        coursesAdapter = CoursesAdapter(
            emptyList(),
            onItemClick = { courseId -> viewModel.onCourseClicked(courseId) },
            onLickedItemClick = { courseId -> viewModel.onLikeClicked(courseId) }
        )

        binding.rsCourses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = coursesAdapter
        }
    }

    private fun observeFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeState() }

                launch { observeAction() }
            }
        }
    }

    private suspend fun observeState() {
        viewModel.state.collect { state ->
            when (state) {
                is BaseCoursesViewModel.State.Error -> showError(state.error)
                BaseCoursesViewModel.State.Loading -> showLoading()
                is BaseCoursesViewModel.State.Success -> showCourses(state.courses)

            }
        }
    }

    private suspend fun observeAction() {
        viewModel.actions.collect { action ->
            when (action) {
                is BaseCoursesViewModel.Action.RouteToDetail -> {
                    navigateToDetailFragment(action.courseId)
                }
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            pbCourses.isVisible = true
            groupError.isVisible = false
            rsCourses.isVisible = false
            icConnection.isVisible = false
        }
    }

    private fun showError(error: UiInfoState) {
        with(binding) {
            pbCourses.isVisible = false
            rsCourses.isVisible = false
            icConnection.isVisible = false
            groupError.isVisible = true
            tvError.text = getString(error.title)
        }
    }

    private fun showCourses(courses: BaseCoursesViewModel.CoursesState) {
        with(binding) {
            pbCourses.isVisible = false
            groupError.isVisible = false
            rsCourses.isVisible = true
            icConnection.isVisible = courses.error != null
            coursesAdapter.updateCourses(courses.courses)
        }
    }

    private fun navigateToDetailFragment(courseId: Int) {
        val args = Bundle().apply {
            putInt("courseId", courseId)
        }
        binding.root.findNavController()
            .navigate(R.id.action_lickedFragment_to_detailFragment, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
