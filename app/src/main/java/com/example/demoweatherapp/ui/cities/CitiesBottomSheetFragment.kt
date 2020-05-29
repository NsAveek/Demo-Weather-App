package com.example.demoweatherapp.ui.cities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoweatherapp.R
import com.example.demoweatherapp.core.events.ListenToDismissFragmentCall
import com.example.demoweatherapp.databinding.CitiesBottomSheetFragmentBinding
import com.example.demoweatherapp.ui.cities.adapter.AllCitiesListAdapter
import com.example.demoweatherapp.ui.home.MainActivity
import com.example.demoweatherapp.viewModel.ViewModelProviderFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class CitiesBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        fun getCitiesBottomSheetFragment() = CitiesBottomSheetFragment()
    }

    lateinit var viewModel: CitiesBottomSheetViewModel

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var appContext: Context

    private lateinit var binding: CitiesBottomSheetFragmentBinding

    private lateinit var allCitiesistAdapter: AllCitiesListAdapter
    private lateinit var allCitiesRecyclerView: RecyclerView
    private lateinit var allCitiesListLayoutManager: LinearLayoutManager

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(CitiesBottomSheetViewModel::class.java)

        allCitiesListLayoutManager = LinearLayoutManager(activity).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.cities_bottom_sheet_fragment,
            container,
            false
        )
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this // To enable Live Data object to update the XML on update

        initAllCitiesListAdapter(binding.root)
        initAllCitiesRecyclerView(binding.root)

        loadDataInsideTheAdapter()
        return binding.root
    }

    private fun initAllCitiesListAdapter(v : View) {
        allCitiesistAdapter =
            AllCitiesListAdapter(v.context)
    }

    private fun initAllCitiesRecyclerView(v: View) {
        allCitiesRecyclerView =
            v.findViewById<RecyclerView>(R.id.all_cities_rcv).apply {
                this.layoutManager = this@CitiesBottomSheetFragment.allCitiesListLayoutManager
                this.adapter = this@CitiesBottomSheetFragment.allCitiesistAdapter
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun loadDataInsideTheAdapter() {
        allCitiesistAdapter.clearData()
        allCitiesistAdapter.setData(appContext.resources.getStringArray(R.array.all_cities).toList())
    }

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED)
    }

    override fun getView(): View? {
        return super.getView()
    }

    override fun onPause() {
        super.onPause()
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ListenToDismissFragmentCall) {
        if (event.getMessage()){
            dismiss()
        }
    }
}
