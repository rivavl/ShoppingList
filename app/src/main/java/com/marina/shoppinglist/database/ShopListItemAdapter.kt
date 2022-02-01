package com.marina.shoppinglist.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marina.shoppinglist.R
import com.marina.shoppinglist.databinding.ListNameItemBinding
import com.marina.shoppinglist.entities.ShopListNameItem
import com.marina.shoppinglist.entities.ShopListItem

class ShopListItemAdapter(private val listener: Listener) :
    ListAdapter<ShopListItem, ShopListItemAdapter.ItemHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return if (viewType == 0)
            ItemHolder.createShopItem(parent)
        else
            ItemHolder.createLibraryItem(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (getItem(position).itemType == 0) {
            holder.setItemData(getItem(position), listener)
        } else {
            holder.setLibraryData(getItem(position), listener)
        }
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListNameItemBinding.bind(view)

        fun setItemData(shopListItem: ShopListItem, listener: Listener) = with(binding) {

        }

        fun setLibraryData(shopListNameItem: ShopListItem, listener: Listener) = with(binding) {

        }

        companion object {
            fun createShopItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_list_item, parent, false)
                )
            }

            fun createLibraryItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_library_list_item, parent, false)
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    class ItemComparator() : DiffUtil.ItemCallback<ShopListItem>() {

        override fun areItemsTheSame(
            oldItem: ShopListItem,
            newItem: ShopListItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShopListItem,
            newItem: ShopListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun deleteItem(id: Int)
        fun onClickItem(shopListNameItem: ShopListNameItem)
        fun editItem(shopListNameItem: ShopListNameItem)
    }
}