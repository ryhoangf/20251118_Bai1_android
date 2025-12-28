package com.example.a20251118_bai1_android

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// Chuyển sang AndroidViewModel để lấy Application Context
class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val _studentList = MutableLiveData<MutableList<Student>>()
    val studentList: LiveData<MutableList<Student>> get() = _studentList

    private val dbHelper: StudentDatabaseHelper = StudentDatabaseHelper(application)

    init {
        loadData()
    }

    // Hàm load lại dữ liệu từ DB lên LiveData
    private fun loadData() {
        _studentList.value = dbHelper.getAllStudents()
    }

    fun addStudent(student: Student) {
        dbHelper.addStudent(student)
        loadData() // Refresh lại list sau khi thêm
    }

    fun updateStudent(originalId: String, updatedStudent: Student) {
        dbHelper.updateStudent(originalId, updatedStudent)
        loadData() // Refresh lại list sau khi sửa
    }

    fun deleteStudent(position: Int) {
        val currentList = _studentList.value ?: return
        if (position in currentList.indices) {
            val studentToDelete = currentList[position]
            // Gọi DB xóa theo MSSV (Primary Key)
            dbHelper.deleteStudent(studentToDelete.studentId)
            loadData() // Refresh lại list sau khi xóa
        }
    }
}