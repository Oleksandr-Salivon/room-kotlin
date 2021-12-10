package com.example.roomdatabase.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.model.User
import com.example.roomdatabase.databinding.RowCustomBinding

class ListAdapter(private var userList: List<User>) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.MyViewHolder {
        val binding = RowCustomBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = userList.size


    override fun onBindViewHolder(holder: ListAdapter.MyViewHolder, position: Int) {
        val currentItem = userList[position]
        with(holder) {
            with(currentItem) {
                binding.textViewId.text = id.toString()
                binding.textViewFirstName.text = firstName
                binding.textViewLastName.text = lastName
                binding.textViewAge.text = age.toString()

                binding.rowLayout.setOnClickListener { view ->
                    val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                    Navigation.findNavController(view).navigate(action)
                }
            }

        }
    }

    fun setData(user: List<User>) {
        this.userList = user
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: RowCustomBinding) : RecyclerView.ViewHolder(binding.root)
}

