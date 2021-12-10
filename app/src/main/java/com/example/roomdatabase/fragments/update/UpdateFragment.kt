package com.example.roomdatabase.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentListBinding
import com.example.roomdatabase.databinding.FragmentUpdateBinding
import com.example.roomdatabase.model.User
import com.example.roomdatabase.viewmodel.UserViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
//
        binding.updateAge.setText(args.curentUser.age.toString())
        binding.updateFirstName.setText(args.curentUser.firstName)
        binding.updateLastName.setText(args.curentUser.lastName)
        binding.UpdateButton.setOnClickListener {
            updateData()
        }
        setHasOptionsMenu(true)
        return binding.root
    }


    private fun updateData() {
        val firstName = binding.updateFirstName.text.toString()
        val lastName = binding.updateLastName.text.toString()
        val age = binding.updateAge.text

        if (inputCheck(firstName, lastName, age)) {
            // Create User Object
            val updateUser =
                User(args.curentUser.id, firstName, lastName, Integer.parseInt(age.toString()))
            // Add Data to Database
            mUserViewModel.updateUser(updateUser)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            // Navigate Back

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }


    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_menu) {
            deleteUser()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.curentUser)
            Toast.makeText(
                requireContext(),
                "Successfuly removed : ${args.curentUser.firstName}",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete  ${args.curentUser.id}?")
        builder.setMessage("Are you sure?")
        builder.create().show()

    }

}