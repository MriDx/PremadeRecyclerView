package com.mridx.premaderecyclerviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mridx.premaderecyclerviewexample.databinding.ActivityMainBinding
import com.mridx.premaderecyclerviewexample.databinding.ItemviewType1Binding
import com.mridx.premaderecyclerviewexample.databinding.ItemviewType2Binding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.premadeRecyclerView.apply {
            this.setItemCount(15) //pass items count
            this.onLastItemScrolled {
                // last item is scrolled, load more data or show there's no more new data :D
                this.setItemCount(10) //for example I'm adding another 10 items to the recyclerview
            }
            this.itemBuilder = { parent, index ->
                var view: View? =
                    ItemviewType1Binding.inflate(layoutInflater, parent, false).apply {
                        this.textView.text = "this is a view type 1"
                        this.root.setOnClickListener {
                            Toast.makeText(
                                this@MainActivity,
                                "Clicked on view type 1 View",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }.root
                if (index in 6..9) {
                    view = ItemviewType2Binding.inflate(layoutInflater, parent, false).apply {
                        this.root.setOnClickListener {
                            Toast.makeText(
                                this@MainActivity,
                                "Clicked on view type 2 View",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }.root
                }

                view
            }
            //uncomment below line to render the recyclerview with StaggeredGridLayoutManager
            //this.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

            //uncomment below line to render the recyclerview with GridLayoutManager
            //this.layoutManager = GridLayoutManager(this@MainActivity, 2)

            // by default it renders with LinearLayoutManager with vertical orientation
        }.also {
            //render must call after assigning itemBuilder
            it.render()
        }

    }
}