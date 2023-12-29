package com.example.base_app.di.component

import android.app.Application
import com.example.base_app.AppApplication
import com.example.base_app.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ViewModelModule::class,
    AppModule::class,
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    FragmentModule::class,
//    DatabaseModule::class,
    PopupModule::class,
    ServiceModule::class])
interface AppComponent : AndroidInjector<AppApplication> {

    override fun inject(application: AppApplication)

    @Component.Builder
    interface Builder {


        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}