# PremadeRecyclerView [2.0-beta01](https://github.com/MriDx/PremadeRecyclerView/releases/tag/2.0-beta01)

**PemadeRecyclerView** is a ready-made recyclerview. It simplifies the list rendering process and speed-up development.

**PremadeRecyclerView** is inspired from Flutter's *ListView.Builder()*





# Features

 - No need to maintain RecyclerView.Adapter<*> and ViewHolder classes any more.
 - Out-of-the-box load more listener.
 - Render multiple view easily.
 - All other native recyclerview functions and attributes are compatible.

## Get Started

### Installing...

#### Step 1 - 
Add jitpack.io to project's `build.gradle` file
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

#### Step 2 -
Add *PremadeRecyclerView* dependency to app's `build.gradle` file
```gradle
dependencies {
  ...
  implementation 'com.github.mridx:PremadeRecyclerView:2.0-beta01'
}
```

#### Step 3 -
***Build*** and enjoy :D 


### Add to XML layout

#### Step 1 -
In XML Layout, replace ***RecyclerView*** with **PremadeRecyclerView** 
```XML
<com.mridx.premaderecyclerview.PremadeRecyclerView
        android:id="@+id/premadeRecyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

#### Step 2 -
In Activity or Fragment -
```kotlin
binding.premadeRecyclerView.apply {
            this.setItemCount(15) //pass items count
            this.onLastItemScrolled {
                // last item is scrolled, load more data or show there's no more new data :D
                this.addMoreItems(count =  10) // it will increase item count by the passed value
            }
            this.itemBuilder = { parent, index ->
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
            }
            // by default it renders with LinearLayoutManager in vertical orientation
        }.also {
            //render must call after assigning itemBuilder
            it.render()
        }
   ```
   **That's all**
   * *It will render 15 items at first, and when it scrolls to bottom it will add another 10 items to the list.*

### Customizing
#### Pass the list of items instead
```kotlin
binding.premadeRecyclerView.apply {
	setItems(items) //pass item list initially instead of setItemCount
	onLastItemScrolled { // last item is scrolled, load more data or show there's no more new data :D
           addMoreItems(items) //add new items list, it will auto-reflect
		// addMoreItems(items, notifyItemRangeChanged = true) //if want itemRangeChanged to be work on newly added items
		}
	//itemBuilderWithItem instead of itemBuilder
	itemBuilderWithItem = {parent, item -> 
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
	}
	...
}.also { it.render() }
```

#### Change layout manager
```kotlin
binding.premadeRecyclerView.apply {
	...
	// by default, it will apply LinearLayoutManager with vertical orientation
	
	//uncomment below line to render the recyclerview with StaggeredGridLayoutManager
    //this.layoutManager = StaggeredGridLayoutManager(columnCount, RecyclerView.VERTICAL)

    //uncomment below line to render the recyclerview with GridLayoutManager
    //this.layoutManager = GridLayoutManager(this@MainActivity, columnCount)
}
```

#### more to add...


#### Functions
```kotlin
setItemCount(count:  Int) //sets item count to render in the view
// Note:- shouldn't be called more that once
```
```kotlin
addMoreItems(count:  Int) //suitable for load more items 
```
```kotlin
setItems(itemList:  List<Any>) // pass items list to the view, no need to maintain list locally anymore
// Note :- it will override already if any list existing to the view so call only once.
```
```kotlin
addMoreItems(itemList:  List<Any>) //suitable for add more items when exsiting are scrolled already
// Note :- it will add passed items into the existing list and no need to call notifyDataSetChanged manually
```
```kotlin 
addMoreItems(itemList:  List<Any>, notifyItemRangeChanged:  Boolean) //passing true as notifyItemRangeChanged will show itemRangeChanged animation automatically, no need to notify manually
```
```kotlin
itemSetChanged() //notifyDataSetChanged()

itemChanged(position:  Int) // notifyItemChanged(position)

itemChanged(startPosition:  Int, count:  Int) //notifyItemRangeChanged(startPosition, count)

itemAdded(position:  Int) //notifyItemInserted(position)

itemsAdded(startPosition:  Int, count:  Int) //notifyItemRangeInserted(startPosition, count)

itemRemoved(position:  Int) //notifyItemRemoved(position)

itemRemoved(startPosition:  Int, count:  Int) //notifyItemRangeRemoved(startPosition, count)
```


### Contributor

 - MriDx [ [GitHub](https://github.com/mridx), [Twitter](https://twitter.com/mridulbaishya2) , [Facebook](https://facebook.com/mridx), [medium](https://mridx.medium.com) ]
 - Here it can be your name, you're welcome to contribute


## Licence

```
Copyright 2021 MriDx

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
