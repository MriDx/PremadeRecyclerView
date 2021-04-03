package com.mridx.premaderecyclerviewexample

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mridx.premaderecyclerviewexample.databinding.ActivityMainBinding
import com.mridx.premaderecyclerviewexample.databinding.ItemviewType2Binding

class Selectable : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    data class Item(var isSelected: Boolean)

    private val items by lazy {
        arrayListOf(
            Item(isSelected = true),
            Item(isSelected = false),
            Item(isSelected = true),
            Item(isSelected = false),
            Item(isSelected = false),
            Item(isSelected = false),
            Item(isSelected = false),
            Item(isSelected = false),
            Item(isSelected = true)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.premadeRecyclerView.apply {
            setItemCount(items.size)
            itemBuilder = { parent, index ->
                ItemviewType2Binding.inflate(layoutInflater, parent, false).also {
                    it.root.setBackgroundColor(if (items[index].isSelected) Color.GREEN else Color.RED)
                    it.root.setOnClickListener {
                        items[index].isSelected = !items[index].isSelected
                        this.itemChanged(index)
                    }
                }.root
            }
        }.also { it.render() }

    }

}