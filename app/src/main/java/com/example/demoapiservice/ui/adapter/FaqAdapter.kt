package com.example.demoapiservice.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapiservice.data.model.FaqItem
import com.example.demoapiservice.databinding.ItemFaqBinding

class FaqAdapter (private var list: List<FaqItem>):
    RecyclerView.Adapter<FaqAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemFaqBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFaqBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(list[position]){
            holder.binding.tvNo.text="${position + 1}."
            holder.binding.tvTitle.text=question
            holder.binding.tvContent.text=answer
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}