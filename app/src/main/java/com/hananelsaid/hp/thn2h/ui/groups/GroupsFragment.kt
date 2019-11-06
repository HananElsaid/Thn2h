package com.hananelsaid.hp.thn2h.ui.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hananelsaid.hp.thn2h.R

class GroupsFragment : Fragment() {

    private lateinit var groupsViewModel: GroupsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        groupsViewModel =
            ViewModelProviders.of(this).get(GroupsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        groupsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}