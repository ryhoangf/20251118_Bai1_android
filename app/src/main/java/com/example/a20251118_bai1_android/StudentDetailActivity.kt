package com.example.a20251118_bai1_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class StudentDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        val etId = findViewById<EditText>(R.id.etId)
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)

        // 1. Nhận dữ liệu từ Intent
        val student = intent.getSerializableExtra("STUDENT_DATA") as? Student
        val position = intent.getIntExtra("POSITION", -1)

        // 2. Hiển thị lên giao diện
        if (student != null) {
            etId.setText(student.studentId)
            etName.setText(student.fullName)
            etPhone.setText(student.phone)
            etAddress.setText(student.address)
        }

        // 3. Xử lý nút cập nhật
        btnUpdate.setOnClickListener {
            val updatedStudent = Student(
                etId.text.toString(),
                etName.text.toString(),
                etPhone.text.toString(),
                etAddress.text.toString()
            )

            val resultIntent = Intent()
            resultIntent.putExtra("UPDATED_STUDENT", updatedStudent)
            resultIntent.putExtra("POSITION", position) // Trả lại vị trí để list biết dòng nào cần sửa
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
