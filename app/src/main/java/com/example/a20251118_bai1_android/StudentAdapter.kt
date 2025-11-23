package com.example.a20251118_bai1_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val studentList: MutableList<Student>,
    private val onEditClick: (Int) -> Unit,  // Hàm callback khi nhấn vào item (để sửa)
    private val onDeleteClick: (Int) -> Unit // Hàm callback khi nhấn nút xóa
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvId: TextView = itemView.findViewById(R.id.tvId)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(student: Student, position: Int) {
            tvName.text = student.fullName
            tvId.text = student.studentId

            // Xử lý nút xóa
            btnDelete.setOnClickListener {
                onDeleteClick(position)
            }

            // Xử lý click vào dòng (để đưa dữ liệu lên form sửa)
            itemView.setOnClickListener {
                onEditClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position], position)
    }

    override fun getItemCount(): Int = studentList.size
}