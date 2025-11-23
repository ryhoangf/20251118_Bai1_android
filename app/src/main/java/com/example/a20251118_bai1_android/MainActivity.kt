package com.example.a20251118_bai1_android

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // Khai báo biến
    private lateinit var etStudentId: EditText
    private lateinit var etName: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var recyclerView: RecyclerView

    private val studentList = mutableListOf<Student>()
    private lateinit var adapter: StudentAdapter

    // Biến theo dõi vị trí item đang được chọn để sửa (-1 nghĩa là chưa chọn gì)
    private var currentEditingIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Ánh xạ View
        etStudentId = findViewById(R.id.etStudentId)
        etName = findViewById(R.id.etName)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)

        // 2. Khởi tạo dữ liệu mẫu (Option)
        studentList.add(Student("20200001", "Nguyễn Văn A"))
        studentList.add(Student("20200002", "Trần Thị B"))
        studentList.add(Student("20200003", "Lê Văn C"))

        // 3. Cấu hình Adapter và RecyclerView
        adapter = StudentAdapter(
            studentList,
            onEditClick = { position ->
                // Khi click vào item: Đẩy dữ liệu lên EditText
                val selectedStudent = studentList[position]
                etStudentId.setText(selectedStudent.studentId)
                etName.setText(selectedStudent.fullName)

                // Lưu vị trí đang sửa
                currentEditingIndex = position

                // (Tùy chọn) Khóa nút Add hoặc đổi màu để báo hiệu đang sửa
                // btnAdd.isEnabled = false
            },
            onDeleteClick = { position ->
                deleteStudent(position)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 4. Sự kiện nút ADD
        btnAdd.setOnClickListener {
            val id = etStudentId.text.toString().trim()
            val name = etName.text.toString().trim()

            if (id.isNotEmpty() && name.isNotEmpty()) {
                // Thêm mới
                studentList.add(Student(id, name))
                adapter.notifyItemInserted(studentList.size - 1)
                recyclerView.scrollToPosition(studentList.size - 1) // Cuộn xuống cuối
                clearInput()
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }

        // 5. Sự kiện nút UPDATE
        btnUpdate.setOnClickListener {
            val id = etStudentId.text.toString().trim()
            val name = etName.text.toString().trim()

            if (currentEditingIndex != -1) {
                if (id.isNotEmpty() && name.isNotEmpty()) {
                    // Cập nhật dữ liệu tại vị trí đang chọn
                    studentList[currentEditingIndex].studentId = id
                    studentList[currentEditingIndex].fullName = name

                    // Báo adapter cập nhật lại giao diện dòng đó
                    adapter.notifyItemChanged(currentEditingIndex)

                    Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
                    clearInput()
                } else {
                    Toast.makeText(this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng chọn sinh viên trong danh sách để sửa", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hàm xóa sinh viên
    private fun deleteStudent(position: Int) {
        studentList.removeAt(position)
        adapter.notifyItemRemoved(position)
        // Cập nhật lại index cho toàn bộ list để tránh lỗi khi xóa tiếp
        adapter.notifyItemRangeChanged(position, studentList.size)

        // Nếu xóa đúng cái đang sửa thì reset form
        if (currentEditingIndex == position) {
            clearInput()
        }
    }

    // Hàm xóa trắng form nhập liệu
    private fun clearInput() {
        etStudentId.text.clear()
        etName.text.clear()
        etStudentId.requestFocus()
        currentEditingIndex = -1 // Reset trạng thái về thêm mới
    }
}