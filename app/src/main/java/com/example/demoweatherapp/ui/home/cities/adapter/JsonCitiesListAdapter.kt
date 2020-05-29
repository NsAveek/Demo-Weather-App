package com.example.demoweatherapp.ui.home.cities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.demoweatherapp.R
import com.example.demoweatherapp.databinding.CitiesForecastLayoutBinding
import com.example.demoweatherapp.databinding.JsonCitiesLayoutBinding
import com.example.demoweatherapp.databinding.LoaderFooterTransparentGreyBinding
import com.example.demoweatherapp.ui.home.MainActivity
import com.example.demoweatherapp.ui.home.cities.JSONCitiesViewModel
import com.example.demoweatherapp.ui.home.cities.WeatherForecastViewModel
import com.example.demoweatherapp.viewModel.ViewModelProviderFactory
import javax.inject.Inject


class JsonCitiesListAdapter @Inject constructor(val context : MainActivity, viewModelProviderFactory: ViewModelProviderFactory) : RecyclerView.Adapter<JsonCitiesListAdapter.BottomSheetVH>() {

    private val items : ArrayList<String> = ArrayList()

    private val viewModel : JSONCitiesViewModel = ViewModelProviders.of(context, viewModelProviderFactory)
        .get(JSONCitiesViewModel::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetVH{
        return BottomSheetVH(DataBindingUtil.inflate(LayoutInflater.from(parent?.context),
            R.layout.json_cities_layout, parent, false), viewModel)
    }


    fun getDataAtPosition(position: Int) : String{
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class BottomSheetVH (val binding: JsonCitiesLayoutBinding, val viewModel: JSONCitiesViewModel): RecyclerView.ViewHolder(binding.root) {

        fun bind(data : String, itemPosition: Int) {
            binding.jsonCitiesViewModel = viewModel
            binding.data = data
            binding.itemPosition = itemPosition
        }
    }

    /**
     * set data to the adapter.
     * @param dataset to add to the adapter as dataset
     * @return nothing
     */
    fun setData(data : List<String>){
        items.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * add data to the existing dataset of the adapter. Mostly used for pagination
     * @param dataset to add to the existing dataset
     * @return nothing
     */
    fun addData(data : List<String>){
        val currentPos = itemCount
        items.addAll(data)
        notifyItemRangeInserted(currentPos, items.size)
    }
    /**
     * clear data from the adapter
     * @return nothing
     */
    fun clearData(){
        items.clear()
        notifyDataSetChanged()
    }

    /**
     * get adapter data
     * @param none
     * @return adapter data set
     */
    fun getData() : ArrayList<String>{
        return items
    }

    /**
     * remove adapter data
     * @param position of the dataset to remove
     * @return none
     */
    fun removeData(position : Int){
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * add data to adapter data set
     * @param data to add
     * @return none
     */
    fun addDataAtPos(data : String){
        val currentPos = itemCount
        items.add(data)
        notifyItemRangeInserted(currentPos,items.size)
    }

    override fun onBindViewHolder(holder: BottomSheetVH, position: Int) {
        holder.bind(items[position], position)
    }

}