package com.example.carsomeweatherapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import aveek.com.management.ui.db.AppDatabase
import com.example.carsomeweatherapp.R
import com.example.carsomeweatherapp.databinding.ActivityMainBinding
import com.example.carsomeweatherapp.network.NetworkActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivity : NetworkActivity(), LifecycleOwner, HasSupportFragmentInjector {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    private lateinit var binding : ActivityMainBinding

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    // TODO : Inject Database
    private lateinit var database : AppDatabase

    private lateinit var compositeDisposable : CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        compositeDisposable = CompositeDisposable()

        initDatabase()

        initBinding()

        mLifecycleRegistry = LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }
    }
    private fun initDatabase() {
        database = AppDatabase.getAppDataBase(this)!!
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = viewModel
        binding.lifecycleOwner=this // To enable Live Data object to update the XML on update
    }
    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED)
        compositeDisposable.dispose()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }
}
