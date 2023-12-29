package com.example.base_app.di.module

import android.app.Application
import androidx.room.Room
import com.example.base_app.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDataBaseModule(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "Gallery.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

//    @Provides
//    internal fun provideAlbumDao(appDatabase: AppDatabase): AlbumPrivateDao = appDatabase.albumPrivateDao()

//    @Provides
//    internal fun provideFavouriteDao(appDatabase: AppDatabase): FavouriteDao = appDatabase.albumFavouriteDao()

}
