package com.example.testapp.presentation.home.detail

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.FragmentDetailBinding
import com.example.testapp.domain.model.Course
import com.example.testapp.presentation.home.common.adapter.CoursesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindToViewModel()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun bindToViewModel() {
        setupListeners()
        observeFlows()
    }

    private fun observeFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeState() }
            }
        }
    }

    private suspend fun observeState() {
        viewModel.state.collect { state ->
            when (state) {
                DetailViewModel.State.Loading -> showLoading()
                is DetailViewModel.State.Success -> showCourse(state.course)
            }
        }
    }

    private fun showCourse(course: Course) {
        with(binding) {
            pbCourses.isVisible = false
            nesView.isVisible = true
            btnBookmark.isVisible = true

            btnBookmark.setImageResource(
                if (course.hasLike) R.drawable.ic_bookmark_fill_big else R.drawable.ic_bookmark_black
            )
            tvName.text = course.title
            tvDate.text = formatDate(course.publishDate)
            tvRate.text = course.rate
            tvText.text = course.text
        }
    }

    private fun showLoading() {
        with(binding) {
            pbCourses.isVisible = true
            nesView.isVisible = false
            btnBookmark.isVisible = false
        }
    }

    private fun setupListeners() {
        setupButtonBack()
        setupButtonBookmark()
    }

    private fun setupButtonBookmark() {
        binding.btnBookmark.setOnClickListener {
            viewModel.onLikeClicked()
        }
    }

    private fun setupButtonBack() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun formatDate(dateString: String): String {
        val inputFormatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        } else {
            return dateString
        }

        val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
        val date = LocalDate.parse(dateString, inputFormatter)
        val formatted = date.format(outputFormatter)

        return formatted.replace(Regex("(?<=\\s)[а-я]")) {
            it.value.uppercase(Locale("ru"))
        }
    }
}