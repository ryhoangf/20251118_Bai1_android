package com.example.a20251118_bai1_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a20251118_bai1_android.databinding.FragmentUpdateStudentBinding

class UpdateStudentFragment : Fragment() {

    private lateinit var binding: FragmentUpdateStudentBinding
    private lateinit var viewModel: StudentViewModel
    private var originalId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        val receivedStudent = arguments?.getSerializable("studentData") as? Student

        if (receivedStudent != null) {
            originalId = receivedStudent.studentId

            val displayStudent = receivedStudent.copy()
            binding.student = displayStudent
        }

        binding.btnUpdate.setOnClickListener {
            val s = binding.student
            if (s != null) {
                viewModel.updateStudent(originalId, s)
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }
}
