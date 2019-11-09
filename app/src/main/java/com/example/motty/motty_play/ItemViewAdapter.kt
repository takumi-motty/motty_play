package com.example.motty.motty_play

import android.content.Context
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ItemViewAdapter (private val context: Context, private val itemList: List<Item>) :
    RecyclerView.Adapter<ItemViewAdapter.ItemViewHolder>(){

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val rankNumberView: TextView = view.findViewById(R.id.rankNumberText)
        val itemIconImageView: ImageView = view.findViewById(R.id.itemIcon)
        val itemNameTextView: TextView = view.findViewById(R.id.itemNameText)
        val shopNameTextView: TextView = view.findViewById(R.id.shopNameText)
        val contentTextView: TextView = view.findViewById(R.id.content)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(from(context).inflate(R.layout.list_item, parent, false))

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.rankNumberView.text = (position+1).toString()
        Picasso.get()
            .load(itemList[position].mediumImageUrls[0])
            .resize(128,128)
            .centerCrop()
            .into(holder.itemIconImageView)

        holder.itemNameTextView.text = itemList[position].itemName
        holder.shopNameTextView.text = itemList[position].shopName
        holder.contentTextView.text = itemList[position].catchcopy
    }
}