package com.example.deezer_test.base.application

import android.app.Application
import com.example.deezer_test.base.factory.ActivityBindingModule
import com.example.deezer_test.base.factory.FragmentBindingModule
import com.example.deezer_test.base.factory.ViewModelModule
import com.example.deezer_test.base.factory.ViewModelFactoryModule
import com.example.deezer_test.network.configuration.NetworkModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton
import dagger.BindsInstance


/**
 * The class description here.
 *
 * @author Thomas Drège
 *
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class
    ]
)

interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: BaseApplication)
}