package com.example.recyclerview

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.ContactCardBinding

class RecyclerContactAdapter(private val contactList : ArrayList<ContactModel>) : RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder>() {

    private lateinit var binding : ContactCardBinding


    // to add clicklistener on this adapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_card, parent, false)
//        return ViewHolder(view)

        // Binding
        binding = ContactCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(contactList[position]){
                binding.imgContact.setImageResource(image)
                binding.name.text = name
                binding.number.text = contactNumber
            }
        }
        binding.cardView.setOnClickListener {
            val obj = ContactShowFragment()

            val bundle = Bundle()
            bundle.putInt("image", contactList[position].image)
            bundle.putString("name", contactList[position].name)
            bundle.putString("number", contactList[position].contactNumber)

            obj.arguments = bundle


            val activity = it.context as MainActivity
            val frag = activity.supportFragmentManager.beginTransaction()
            frag.replace(R.id.frameLayout, obj)
            frag.addToBackStack("Adding backstack")
            frag.commit()
        }

        // When we need to set listener by passing view to viewHolder
        holder.itemView.setOnClickListener {

        }

    }

    override fun getItemCount(): Int {
        return  contactList.size
    }


    // When we pass view as a parameter in view holder class
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        val image = itemView.findViewById<ImageView>(R.id.imgContact)
//    }

    // Use binding instead of view in ViewHolder class
    class ViewHolder(binding: ContactCardBinding) : RecyclerView.ViewHolder(binding.root)
}