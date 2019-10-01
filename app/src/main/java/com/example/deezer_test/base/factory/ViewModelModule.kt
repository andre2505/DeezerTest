package com.example.deezer_test.base.factory

import androidx.lifecycle.ViewModel
import com.example.deezer_test.ui.fragment.detail.DetailViewModel
import com.example.deezer_test.ui.fragment.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
@Module(includes = [ViewModelFactoryModule::class])
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel

}