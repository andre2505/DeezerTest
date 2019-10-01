package com.example.deezer_test.base.factory

import com.example.deezer_test.ui.base.BaseFragment
import com.example.deezer_test.ui.fragment.detail.BottomSheetFragment
import com.example.deezer_test.ui.fragment.detail.DetailFragment
import com.example.deezer_test.ui.fragment.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * The class description here.
 *
 * @author thomas
 */
@Module
abstract class FragmentBindingModule {


    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun bindBaseFragment(): BaseFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun bindDetailFragment(): DetailFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun bindBottomSheetFragment(): BottomSheetFragment



}