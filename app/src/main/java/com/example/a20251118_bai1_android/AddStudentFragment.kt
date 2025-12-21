package com.example.a20251118_bai1_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a20251118_bai1_android.databinding.FragmentAddStudentBinding

class AddStudentFragment : Fragment() {

    private lateinit var binding: FragmentAddStudentBinding
    private lateinit var viewModel: StudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        binding.student = Student()

        binding.btnSave.setOnClickListener {
            val s = binding.student
            if (s != null && s.studentId.isNotEmpty() && s.fullName.isNotEmpty()) {
                viewModel.addStudent(s)
                Toast.makeText(context, "Đã thêm sinh viên", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Vui lòng nhập MSSV và Tên", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
