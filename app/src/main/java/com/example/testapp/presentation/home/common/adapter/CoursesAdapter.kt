package com.example.testapp.presentation.home.common.adapter

import android.os.Build
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.databinding.ItemCourseBinding
import com.example.testapp.domain.model.Course
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class CoursesAdapter(
    private var courses: List<Course>,
    private val onItemClick: (Int) -> Unit,
    private val onLickedItemClick: (Int) -> Unit
): RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoursesViewHolder {
        val inflater = android.view.LayoutInflater.from(parent.context)
        val binding = ItemCourseBinding.inflate(inflater, parent, false)
        return CoursesViewHolder(binding, onItemClick, onLickedItemClick)
    }

    override fun onBindViewHolder(
        holder: CoursesViewHolder,
        position: Int
    ) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun updateCourses(newCourses: List<Course>) {
        courses = newCourses
        notifyDataSetChanged()
    }

    class CoursesViewHolder(
        private val binding: ItemCourseBinding,
        private val onItemClick: (Int) -> Unit,
        private val onLickedItemClick: (Int) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            with(binding) {
                tvCourseTitle.text = course.title
                tvCourseDescription.text = course.text
                tvCoursePrice.text = course.price + "₽"
                tvRate.text = course.rate
                tvDate.text = formatDate(course.publishDate)
                root.setOnClickListener { onItemClick(course.id) }
                btnBookmark.setOnClickListener { onLickedItemClick(course.id) }
                btnBookmark.setImageResource(
                    if (course.hasLike) R.drawable.ic_bookmark_fill else R.drawable.ic_bookmark
                )
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

}