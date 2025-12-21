package com.example.a20251118_bai1_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StudentListFragment : Fragment() {

    private lateinit var viewModel: StudentViewModel
    private lateinit var adapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewList)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = StudentAdapter(
            mutableListOf(),
            onItemClick = { position ->
                val student = viewModel.studentList.value?.get(position)
                if (student != null) {
                    val bundle = Bundle().apply {
                        putSerializable("studentData", student)
                    }
                    findNavController().navigate(R.id.action_list_to_update, bundle)
                }
            },
            onDeleteClick = { position ->
                viewModel.deleteStudent(position)
            }
        )
        recyclerView.adapter = adapter

        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAdd)
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_list_to_add)
        }

        viewModel.studentList.observe(viewLifecycleOwner) { students ->
            adapter.updateData(ArrayList(students))
        }
    }
}
