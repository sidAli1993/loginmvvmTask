package com.sid_ali_tech.loginapptaskmvvm.presentation.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.sid_ali_tech.loginapptaskmvvm.R
import com.sid_ali_tech.loginapptaskmvvm.common.userClick
import com.sid_ali_tech.loginapptaskmvvm.data.model.User
import com.sid_ali_tech.loginapptaskmvvm.databinding.ListItemUserBinding

class UsersAdapter(private val categories: List<User>):RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewTaype: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ListItemUserBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: User = categories[position]

        val firstAlphabet = model.first_name.firstOrNull { it.isLetter() }
        val capitalizedFirstAlphabet = firstAlphabet?.uppercase()
        holder.binding.tvLetter.text=capitalizedFirstAlphabet
        holder.binding.tvName.text="${model.first_name} ${model.last_name}"
        holder.binding.tvAddress.text=model.address
        holder.binding.tvEmail.text=model.email
        holder.binding.tvPhone.text=model.phone

        holder.binding.imvEdit.setOnClickListener {
            val navController= findNavController(it)
            Bundle().apply {
                putString("usrObj",Gson().toJson(model))
                navController.navigate(R.id.navEditUser,this)
            }

        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}