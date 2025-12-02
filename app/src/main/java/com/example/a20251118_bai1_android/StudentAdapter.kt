package com.example.a20251118_bai1_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val studentList: MutableList<Student>,
    private val onItemClick: (Int) -> Unit,  // Callback khi nhấn vào item (để mở chi tiết)
    private val onDeleteClick: (Int) -> Unit // Callback khi nhấn nút xóa
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Ánh xạ View
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvId: TextView = itemView.findViewById(R.id.tvId)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(student: Student) {
            tvName.text = student.fullName
            tvId.text = student.studentId

            // Xử lý nút XÓA
            btnDelete.setOnClickListener {
                // Sử dụng bindingAdapterPosition để lấy vị trí thực tế an toàn hơn
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteClick(position)
                }
            }

            // Xử lý click vào ITEM (để mở màn hình chi tiết)
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        // Chỉ truyền object Student, không truyền position vào hàm bind để tránh nhầm lẫn
        holder.bind(studentList[position])
    }

    override fun getItemCount(): Int = studentList.size
}
