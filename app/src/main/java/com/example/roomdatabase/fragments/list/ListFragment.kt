package com.example.roomdatabase.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.R
import com.example.roomdatabase.viewmodel.UserViewModel
import com.example.roomdatabase.databinding.FragmentListBinding
import java.util.*
import kotlin.concurrent.schedule


class ListFragment : Fragment() {
    private lateinit var mUserViewModel: UserViewModel

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val adapter = ListAdapter(emptyList())
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mUserViewModel.readAllData.observe(viewLifecycleOwner, { user ->
            adapter.setData(user)
        })
        // Inflate the layout for this fragment
        binding.floatingActionButton.setOnClickListener {

                findNavController().navigate(R.id.action_listFragment_to_addFragment)

        }

        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_menu) {
            deleteAllUser()
        }

        return super.onOptionsItemSelected(item)
    }
    private fun deleteAllUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteAllUser()
            Toast.makeText(
                requireContext(),
                "Successfuly removed : ",
                Toast.LENGTH_LONG
            ).show()

        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete  All?")
        builder.setMessage("Are you sure?")
        builder.create().show()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}