package com.example.tryingfirebase.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tryingfirebase.databinding.UsersItemBinding
import com.example.tryingfirebase.listener.UserListener
import com.example.tryingfirebase.models.UserModel
import com.example.tryingfirebase.views.EditActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var listUsers: List<UserModel> = arrayListOf()
    private lateinit var listener: UserListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val binding = UsersItemBinding.
        inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return listUsers.count()
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bindData(listUsers[position])
    }

    fun updateUsers(list: List<UserModel>){
        listUsers = list
        notifyDataSetChanged()
    }

    fun attachListener(userListener: UserListener){
        listener = userListener
    }

    inner class UserViewHolder(private val itemBinding: UsersItemBinding,
                               val userListener: UserListener) :
        RecyclerView.ViewHolder(itemBinding.root){

            fun bindData(user: UserModel){
                itemBinding.name.text = user.name
                itemBinding.age.text = "${user.age} anos"
                if(user.disability)
                    itemBinding.disability.text = "Possui deficiência"
                else
                    itemBinding.disability.text = "Não possui deficiência"

                itemBinding.created.text = "Inserção em 10/02/32 às 18h30"
                itemBinding.edit.setOnClickListener{
                    val intent = Intent(itemView.context, EditActivity::class.java)
                    intent.putExtra("name_user", user.name)
                    intent.putExtra("age_user", user.age)
                    intent.putExtra("disability_user", user.disability)
                    itemView.context.startActivity(intent)
                }
            }

    }


}

