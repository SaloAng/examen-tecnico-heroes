package com.test.superhero.io

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.superhero.databinding.ItemHeroBinding

class HeroViewHolder(view:View):RecyclerView.ViewHolder(view) {

    private val binding = ItemHeroBinding.bind(view)

    fun bind(image:String){
        Picasso.get().load(image).into(binding.idHero)
    }
}