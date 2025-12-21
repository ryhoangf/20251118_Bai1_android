package com.example.a20251118_bai1_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {
    private val _studentList = MutableLiveData<MutableList<Student>>()
    val studentList: LiveData<MutableList<Student>> get() = _studentList

    init {
        val initialData = mutableListOf(
            Student("20200001", "Nguyễn Văn A", "0901234567", "Hà Nội"),
            Student("20200002", "Trần Thị B", "0909876543", "HCM")
        )
        _studentList.value = initialData
    }

    fun addStudent(student: Student) {
        val currentList = _studentList.value ?: mutableListOf()
        currentList.add(student)
        _studentList.value = currentList
    }

    fun updateStudent(originalId: String, updatedStudent: Student) {
        val currentList = _studentList.value ?: return
        val index = currentList.indexOfFirst { it.studentId == originalId }
        if (index != -1) {
            currentList[index] = updatedStudent
            _studentList.value = currentList
        }
    }

    fun deleteStudent(position: Int) {
        val currentList = _studentList.value ?: return
        if (position in currentList.indices) {
            currentList.removeAt(position)
            _studentList.value = currentList
        }
    }
}
