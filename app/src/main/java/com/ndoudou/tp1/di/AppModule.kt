package com.ndoudou.tp1.di

import android.content.Context
import androidx.room.Room
import com.ndoudou.tp1.application.TpApplication
import com.ndoudou.tp1.data.local.dao.UserDao
import com.ndoudou.tp1.data.local.database.DataBase
import com.ndoudou.tp1.utils.NAME_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Singleton
//    @Provides
//    fun provideApplication(@ApplicationContext application: Context): TpApplication {
//        return application as TpApplication
//    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext application: Context): DataBase {
        return Room.databaseBuilder(
            application,
            DataBase::class.java,
            NAME_DATABASE,
        ).build()
    }


    @Provides
    @Singleton
    fun provideUserDao(db: DataBase) : UserDao{
        return db.userDao()
    }
}