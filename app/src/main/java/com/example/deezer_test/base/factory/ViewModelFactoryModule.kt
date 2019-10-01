package com.example.deezer_test.base.factory

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}
