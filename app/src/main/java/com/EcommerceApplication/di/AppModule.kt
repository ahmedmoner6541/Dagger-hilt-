package com.EcommerceApplication.di

import android.app.Application
import androidx.room.Room
import com.EcommerceApplication.data.local.db.ProductDao
import com.EcommerceApplication.data.local.db.ProductDatabase
import com.EcommerceApplication.data.remote.network.*
import com.EcommerceApplication.util.Constants
import com.EcommerceApplication.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides // 1- creater retrofit builder
    @Singleton
    fun providesRetrofit():Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


  @Singleton
    @Provides
    fun provideOkHttpClient(interceptor:AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()

    }


    @Singleton
    @Provides
    fun providesTokenRefreshApi(retrofitBuilder: Retrofit.Builder): TokenRefreshApi {
        return retrofitBuilder.build().create(TokenRefreshApi::class.java)
    }


    @Singleton
    @Provides
    fun providesProductApi(retrofitBuilder: Retrofit.Builder): ProductApi {
        return retrofitBuilder.build().create(ProductApi::class.java)
    }
 

    @Provides  // 1- create datebase builder 
    @Singleton
    fun provideDatabase(app: Application): ProductDatabase =
        Room.databaseBuilder(app, ProductDatabase::class.java, Constants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides // 2- create dap interface by database builder
    @Singleton
    fun provideProductDao(db: ProductDatabase): ProductDao {
        return db.getProductDao()
    }


}