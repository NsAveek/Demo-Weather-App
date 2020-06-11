package com.example.demoweatherapp.ui.home.cities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.demoweatherapp.R
import com.example.demoweatherapp.databinding.CitiesLayoutBinding
import com.example.demoweatherapp.databinding.LoaderFooterTransparentGreyBinding
import com.example.demoweatherapp.db.WeatherModel
import com.example.demoweatherapp.ui.home.MainActivity
import com.example.demoweatherapp.ui.home.cities.CitiesViewModel
import com.example.demoweatherapp.ui.home.cities.WeatherForecastViewModel
import com.example.demoweatherapp.viewModel.ViewModelProviderFactory

/** Represents types of views to load inside recycler view
 * @author Aveek
 * @version 1
 * @since Version - 1.0
 */
enum class EnumTransactionType(val type: String) {
    REGULAR("regular"),
    LOADING("loading")
}


const val REGULAR_TYPE = 1
const val LOADING_TYPE = 2
const val DOWNLOAD_MORE_DATA = 3

class CitiesListAdapter(val context: MainActivity, viewModelProviderFactory: ViewModelProviderFactory) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: ArrayList<WeatherModel> = ArrayList()

    private val viewModel: CitiesViewModel = ViewModelProviders.of(context, viewModelProviderFactory)
        .get(CitiesViewModel::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LOADING_TYPE -> {
                LoadingViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(context),
                        R.layout.loader_footer_transparent_grey, parent, false
                    )
                )
            }
            else -> {
                RegularTransactionViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(context),
                        R.layout.cities_layout, parent, false
                    ), context
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is RegularTransactionViewHolder -> viewHolder.bind(items[position])
            is LoadingViewHolder -> viewHolder.bind(DOWNLOAD_MORE_DATA)
        }
    }

    /**
     * set data to the adapter.
     * @param dataset to add to the adapter as dataset
     * @return nothing
     */
    fun setData(data: List<WeatherModel>) {
        items.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * add data to the existing dataset of the adapter. Mostly used for pagination
     * @param dataset to add to the existing dataset
     * @return nothing
     */
    fun addData(data: List<WeatherModel>) {
        val currentPos = itemCount
        items.addAll(data)
        notifyItemRangeInserted(currentPos, items.size)
    }

    /**
     * clear data from the adapter
     * @return nothing
     */
    fun clearData() {
        items.clear()
        notifyDataSetChanged()
    }

    /**
     * get adapter data
     * @param none
     * @return adapter data set
     */
    fun getData(): ArrayList<WeatherModel> {
        return items
    }

    /**
     * remove adapter data
     * @param position of the dataset to remove
     * @return none
     */
    fun removeData(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * add data to adapter data set
     * @param data to add
     * @return none
     */
    fun addDataAtPos(data: WeatherModel) {
        val currentPos = itemCount
        items.add(data)
        notifyItemRangeInserted(currentPos, items.size)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            EnumTransactionType.LOADING.type -> LOADING_TYPE
            else -> REGULAR_TYPE
        }
    }

    /** Represents viewholder of the main type view for recycler view
     * @author Aveek
     * @version 1
     * @since Version 1.0
     */

    class RegularTransactionViewHolder(
        val binding: CitiesLayoutBinding,
        val context: MainActivity
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * set data to xml
         * @param data to set
         * @return none
         */
        fun bind(data: WeatherModel) {
            with(binding) {
                viewModel = CitiesViewModel().apply {
                    data.cityName?.let {
                        citiesName.value = it
                        when(it){
                            context.getString(R.string.kuala_lumpur)->{
                                backgroundDrawable.set(ContextCompat.getDrawable(context,R.drawable.kl_city))
                            }
                            context.getString(R.string.george_town)->{
                                backgroundDrawable.set(ContextCompat.getDrawable(context,R.drawable.george_town))
                            }
                            context.getString(R.string.johor_bahru)->{
                                backgroundDrawable.set(ContextCompat.getDrawable(context,R.drawable.johor_bahru))
                            }
                            else ->{
                                backgroundDrawable.set(ContextCompat.getDrawable(context,R.drawable.malaysia))
                            }
                        }
                    }
                }
                cityName = data.cityName
            }
        }
    }


    /** Represents viewholder of the Loading type view for recycler view
     * @author Aveek
     * @version 1
     * @since Version 1.0
     */
    class LoadingViewHolder(val binding: LoaderFooterTransparentGreyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * set data to xml
         * @param visibility to toggle visibility
         * @return none
         */
        fun bind(visibility: Int) {
            binding.progressBar.visibility =
                if (visibility == DOWNLOAD_MORE_DATA) View.VISIBLE else View.GONE
        }
    }
}