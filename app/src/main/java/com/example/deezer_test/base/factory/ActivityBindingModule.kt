package com.example.deezer_test.base.factory

import com.example.deezer_test.ui.activity.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun bindMainActivity(): MainActivity

}