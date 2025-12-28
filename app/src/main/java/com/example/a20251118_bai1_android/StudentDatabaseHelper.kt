package com.example.a20251118_bai1_android

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "StudentManager.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "students"

        // Tên các cột
        private const val COLUMN_ID = "studentId" // Khóa chính là MSSV
        private const val COLUMN_NAME = "fullName"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_ADDRESS = "address"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID TEXT PRIMARY KEY,
                $COLUMN_NAME TEXT,
                $COLUMN_PHONE TEXT,
                $COLUMN_ADDRESS TEXT
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // 1. Thêm sinh viên
    fun addStudent(student: Student): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, student.studentId)
            put(COLUMN_NAME, student.fullName)
            put(COLUMN_PHONE, student.phone)
            put(COLUMN_ADDRESS, student.address)
        }
        val result = db.insert(TABLE_NAME, null, values)
        //db.close()
        return result
    }

    // 2. Lấy danh sách sinh viên
    fun getAllStudents(): MutableList<Student> {
        val list = mutableListOf<Student>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                val address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS))
                list.add(Student(id, name, phone, address))
            } while (cursor.moveToNext())
        }
        cursor.close()
        //db.close()
        return list
    }

    // 3. Cập nhật sinh viên
    fun updateStudent(originalId: String, student: Student): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            // Nếu muốn cho phép sửa MSSV thì cần logic phức tạp hơn,
            // ở đây ta cập nhật các trường khác dựa trên MSSV cũ (originalId)
            put(COLUMN_ID, student.studentId)
            put(COLUMN_NAME, student.fullName)
            put(COLUMN_PHONE, student.phone)
            put(COLUMN_ADDRESS, student.address)
        }
        // Where clause: update where studentId = originalId
        val result = db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(originalId))
        //db.close()
        return result
    }

    // 4. Xóa sinh viên
    fun deleteStudent(studentId: String): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(studentId))
        //db.close()
        return result
    }
}
