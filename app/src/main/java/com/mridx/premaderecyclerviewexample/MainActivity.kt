package com.mridx.premaderecyclerviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mridx.premaderecyclerviewexample.databinding.ActivityMainBinding
import com.mridx.premaderecyclerviewexample.databinding.ItemviewType1Binding
import com.mridx.premaderecyclerviewexample.databinding.ItemviewType2Binding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val items by lazy {
        val list = arrayListOf<String>()
        for (i in 0..10) {
            list.add("hello $i")
        }
        list
    }

    data class Test(var name: String, var selected: Boolean = false)

    private val list = arrayListOf(
        Test("Mridul"),
        Test("Rupam"),
        Test("Sumit"),
        Test("Milan"),
        Test("Jitu"),
        Test("Moni"),
        Test("Goutam"),
        Test("Bhrigu"),
        Test("Jyoti"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.premadeRecyclerView.apply {
            setItemCount(list.size)
            onLastItemScrolled {
                for (i in 0 until 10) {
                    list.add(Test("Dynamic "))
                }
                addMoreItems(10)
            }
            itemBuilder = { parent, index ->
                ItemviewType1Binding.inflate(layoutInflater, parent, false).root
            }
            itemBinding { holder, index ->
                ItemviewType1Binding.bind(holder.itemView).apply {
                    textView.text = "${list[index].name} $index"
                    root.setOnClickListener {
                        list[index].selected = !list[index].selected
                        itemSetChanged()
                    }
                    if (list[index].selected) {
                        root.setBackgroundColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.purple_500
                            )
                        )
                    } else {
                        root.setBackgroundColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.white
                            )
                        )
                    }
                }
            }
        }.render()

        /*binding.premadeRecyclerView.apply {
            this.setItemCount(15) //initially pass items count
            this.onLastItemScrolled {
                // last item is scrolled, load more data or show there's no more new data :D
                this.addMoreItems(count = 10) // it will increase item count by the passed value
                //this.setItemCount(count = 20) //it will override older items count by the passed value
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
        }*/


        /**
         * if you want to pass item list instead of items count
         *
         * note: no need to maintain list locally
         */
        //region pass item list to PremadeRecyclerView

        /*binding.premadeRecyclerView.apply {
            setItems(items) //pass item list initially
            onLastItemScrolled {
                addMoreItems(items) //add new items list, it will auto-reflect
                // addMoreItems(items, notifyItemRangeChanged = true) //if want itemRangeChanged to be work on newly added items
            }
            itemBuilderWithItem = {parent, item -> //return parent and the item to build the view
                ItemviewType1Binding.inflate(layoutInflater, parent, false).apply {
                    this.textView.text = item as String
                    this.root.setOnClickListener {
                        Toast.makeText(
                            this@MainActivity,
                            "Clicked on view type 1 View",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.root
            }
            //rest is same
        }.also { it.render() }*/

        //endregion


    }
}