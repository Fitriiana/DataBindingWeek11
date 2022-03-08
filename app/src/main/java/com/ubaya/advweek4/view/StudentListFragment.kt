package com.ubaya.advweek4.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.advweek4.R
import com.ubaya.advweek4.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_student_list.*

class StudentListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val stdentListAdapter = StudentListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = stdentListAdapter

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.studentsLiveData.observe(viewLifecycleOwner) {
            stdentListAdapter.updateStudentList(it)
        }
        viewModel.studentLoadErrorLiveData.observe(viewLifecycleOwner){
            textError.visibility = if(it) View.VISIBLE else View.GONE
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner){
            if (it){
                recView.visibility = View.GONE
                progressLoad.visibility = View.GONE
            }else{
                recView.visibility = View.VISIBLE
                progressLoad.visibility = View.GONE
            }
        }
    }
}