package com.example.testeapicatsj.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testeapicatsj.databinding.CardImageBinding
import com.example.testeapicatsj.model.data.CatData
import com.squareup.picasso.Picasso

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolderA>() {

    private var listaImg = emptyList<String>()
     fun addImg(lista: List<String>){
         listaImg = lista
        notifyDataSetChanged()
    }
    inner class ViewHolderA(val binding: CardImageBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(url:String){
            Picasso.get()
                .load(url)
                .into(binding.imageViewCat)

        }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderA {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = CardImageBinding.inflate(layoutInflater,parent,false)
        return ViewHolderA(itemView)
    }

    override fun getItemCount(): Int {
        return listaImg.size
    }

    override fun onBindViewHolder(holder: ViewHolderA, position: Int) {
        val url = listaImg[position]
        holder.bind(url)
    }
}


