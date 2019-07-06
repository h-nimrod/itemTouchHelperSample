package com.hnimrod.itemtouchhelpersample

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.hnimrod.itemtouchhelpersample.databinding.ActivityMainBinding
import com.hnimrod.itemtouchhelpersample.helper.OnStartDragListener
import com.hnimrod.itemtouchhelpersample.helper.SimpleItemTouchHelperCallback
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val KEY_SAVE_LIST = "key_save_list"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var callback: ItemTouchHelper.Callback
    private lateinit var adapter: SimpleGridListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list: List<String> =
            if (savedInstanceState == null) (1..50).map { it.toString() }.toList()
            else savedInstanceState.getStringArrayList(KEY_SAVE_LIST) as List<String>

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        adapter = SimpleGridListAdapter(this, object : OnStartDragListener {
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
            }
        }, list)

        callback = SimpleItemTouchHelperCallback(adapter)

        itemTouchHelper = ItemTouchHelper(callback).apply {
            attachToRecyclerView(binding.recyclerView)
        }

        val spanCount = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 3
            else -> 5
        }
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, spanCount)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putStringArrayList(KEY_SAVE_LIST, ArrayList(adapter.getList()))
        super.onSaveInstanceState(outState)
    }

}
