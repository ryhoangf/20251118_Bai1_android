package com.example.a20251118_bai1_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val studentList = mutableListOf<Student>()
    private lateinit var adapter: StudentAdapter

    // Launcher cho việc THÊM sinh viên
    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newStudent = result.data?.getSerializableExtra("NEW_STUDENT") as? Student
            if (newStudent != null) {
                studentList.add(newStudent)
                adapter.notifyItemInserted(studentList.size - 1)
            }
        }
    }

    // Launcher cho việc SỬA sinh viên (từ màn hình chi tiết)
    private val updateStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedStudent = result.data?.getSerializableExtra("UPDATED_STUDENT") as? Student
            val position = result.data?.getIntExtra("POSITION", -1) ?: -1

            if (updatedStudent != null && position != -1) {
                studentList[position] = updatedStudent
                adapter.notifyItemChanged(position)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        // Dữ liệu mẫu
        studentList.add(Student("20200001", "Nguyễn Văn A", "0901234567", "Hà Nội"))
        studentList.add(Student("20200002", "Trần Thị B", "0909876543", "HCM"))

        adapter = StudentAdapter(
            studentList,
            onItemClick = { position -> // Đã đổi tên ở đây
                // Code mở Activity chi tiết (như hướng dẫn trước)
                val intent = Intent(this, StudentDetailActivity::class.java)
                intent.putExtra("STUDENT_DATA", studentList[position])
                intent.putExtra("POSITION", position)
                updateStudentLauncher.launch(intent)
            },
            onDeleteClick = { position ->
                // Code xóa
                studentList.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, studentList.size)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    // Tạo Option Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Xử lý sự kiện click Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                // Mở Activity Thêm
                val intent = Intent(this, AddStudentActivity::class.java)
                addStudentLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
