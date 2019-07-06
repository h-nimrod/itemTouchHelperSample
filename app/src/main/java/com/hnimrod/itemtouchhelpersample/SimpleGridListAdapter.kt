package com.hnimrod.itemtouchhelpersample

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hnimrod.itemtouchhelpersample.databinding.CellBinding
import com.hnimrod.itemtouchhelpersample.helper.ItemTouchHelperAdapter
import com.hnimrod.itemtouchhelpersample.helper.ItemTouchHelperViewHolder
import com.hnimrod.itemtouchhelpersample.helper.OnStartDragListener
import java.util.*

class SimpleGridListAdapter(
    private val context: Context,
    private val listener: OnStartDragListener,
    initialValues: List<String> = listOf("a", "b", "c")
) : RecyclerView.Adapter<SimpleGridListAdapter.ItemViewHolder>(), ItemTouchHelperAdapter {

    private val items: MutableList<String> = initialValues.toMutableList()

    fun getList(): List<String> = items.toList()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DataBindingUtil.inflate<CellBinding>(LayoutInflater.from(context), R.layout.cell, parent, false)
        return ItemViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.text.text = items[position]
        holder.binding.handle.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                listener.onStartDrag(holder)
            }
            false
        }
    }

    override fun onItemDismiss(pos: Int) {
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onItemMove(fromPos: Int, toPos: Int): Boolean {
        Collections.swap(items, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
        return true
    }

    inner class ItemViewHolder(val binding: CellBinding) : RecyclerView.ViewHolder(binding.root),
        ItemTouchHelperViewHolder {

        override fun onItemSelected() {
            binding.item.setBackgroundResource(R.color.cellGrey)
        }

        override fun onItemClear() {
            binding.item.setBackgroundResource(R.color.cellLightGrey)
        }
    }
}
