package com.example.a20251118_bai1_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val etId = findViewById<EditText>(R.id.etId)
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val id = etId.text.toString()
            val name = etName.text.toString()
            val phone = etPhone.text.toString()
            val address = etAddress.text.toString()

            if (id.isNotEmpty() && name.isNotEmpty()) {
                val newStudent = Student(id, name, phone, address)

                // Trả dữ liệu về MainActivity
                val resultIntent = Intent()
                resultIntent.putExtra("NEW_STUDENT", newStudent)
                setResult(Activity.RESULT_OK, resultIntent)
                finish() // Đóng Activity
            } else {
                Toast.makeText(this, "Vui lòng nhập MSSV và Tên", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
